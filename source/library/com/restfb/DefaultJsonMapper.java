/*
 * Copyright (c) 2010-2013 Mark Allen.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.restfb;

import static com.restfb.json.JsonObject.NULL;
import static com.restfb.util.ReflectionUtils.findFieldsWithAnnotation;
import static com.restfb.util.ReflectionUtils.findMethodsWithAnnotation;
import static com.restfb.util.ReflectionUtils.getFirstParameterizedTypeArgument;
import static com.restfb.util.ReflectionUtils.isPrimitive;
import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.trimToEmpty;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.FINEST;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.types.Post.Comments;
import com.restfb.util.ReflectionUtils.FieldWithAnnotation;

/**
 * Default implementation of a JSON-to-Java mapper.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultJsonMapper implements JsonMapper {
  /**
   * We call this instance's {@link JsonMappingErrorHandler#handleMappingError(String)} method on mapping failure so
   * client code can decide how to handle the problem.
   */
  protected JsonMappingErrorHandler jsonMappingErrorHandler;

  /**
   * Logger.
   */
  private static final Logger logger = Logger.getLogger(DefaultJsonMapper.class.getName());

  /**
   * Creates a JSON mapper which will throw {@link com.restfb.exception.FacebookJsonMappingException} whenever an error
   * occurs when mapping JSON data to Java objects.
   */
  public DefaultJsonMapper() {
    this(new JsonMappingErrorHandler() {
      /**
       * @see com.restfb.DefaultJsonMapper.JsonMappingErrorHandler#handleMappingError(java.lang.String, java.lang.Class,
       *      java.lang.Exception)
       */
      @Override
      public boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception e) {
        return false;
      }
    });
  }

  /**
   * Creates a JSON mapper which delegates to the provided {@code jsonMappingErrorHandler} for handling mapping errors.
   * 
   * @param jsonMappingErrorHandler
   *          The JSON mapping error handler to use.
   * @throws IllegalArgumentException
   *           If {@code jsonMappingErrorHandler} is {@code null}.
   * @since 1.6.2
   */
  public DefaultJsonMapper(JsonMappingErrorHandler jsonMappingErrorHandler) {
    if (jsonMappingErrorHandler == null)
      throw new IllegalArgumentException("The jsonMappingErrorHandler parameter cannot be null.");

    this.jsonMappingErrorHandler = jsonMappingErrorHandler;
  }

  /**
   * @see com.restfb.JsonMapper#toJavaList(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> List<T> toJavaList(String json, Class<T> type) {
    if (type == null)
      throw new FacebookJsonMappingException("You must specify the Java type to map to.");

    json = trimToEmpty(json);

    if (isBlank(json)) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, null))
        return null;
      throw new FacebookJsonMappingException("JSON is an empty string - can't map it.");
    }

    if (json.startsWith("{")) {
      // Sometimes Facebook returns the empty object {} when it really should be
      // returning an empty list [] (example: do an FQL query for a user's
      // affiliations - it's a list except when there are none, then it turns
      // into an object). Check for that special case here.
      if (isEmptyObject(json)) {
        if (logger.isLoggable(FINER))
          logger.finer("Encountered {} when we should've seen []. "
              + "Mapping the {} as an empty list and moving on...");

        return new ArrayList<T>();
      }

      // Special case: if the only element of this object is an array called
      // "data", then treat it as a list. The Graph API uses this convention for
      // connections and in a few other places, e.g. comments on the Post
      // object.
      // Doing this simplifies mapping, so we don't have to worry about having a
      // little placeholder object that only has a "data" value.
      try {
        JsonObject jsonObject = new JsonObject(json);
        String[] fieldNames = JsonObject.getNames(jsonObject);

        if (fieldNames != null) {
          boolean hasSingleDataProperty = fieldNames.length == 1 && "data".equals(fieldNames[0]);
          Object jsonDataObject = jsonObject.get("data");

          if (!hasSingleDataProperty && !(jsonDataObject instanceof JsonArray))
            if (jsonMappingErrorHandler.handleMappingError(json, type, null))
              return null;
            else
              throw new FacebookJsonMappingException("JSON is an object but is being mapped as a list "
                  + "instead. Offending JSON is '" + json + "'.");

          json = jsonDataObject.toString();
        }
      } catch (JsonException e) {
        // Should never get here, but just in case...
        if (jsonMappingErrorHandler.handleMappingError(json, type, e))
          return null;
        else
          throw new FacebookJsonMappingException("Unable to convert Facebook response " + "JSON to a list of "
              + type.getName() + " instances.  Offending JSON is " + json, e);
      }
    }

    try {
      List<T> list = new ArrayList<T>();

      JsonArray jsonArray = new JsonArray(json);
      for (int i = 0; i < jsonArray.length(); i++)
        list.add(toJavaObject(jsonArray.get(i).toString(), type));

      return unmodifiableList(list);
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, e))
        return null;
      else
        throw new FacebookJsonMappingException("Unable to convert Facebook response " + "JSON to a list of "
            + type.getName() + " instances", e);
    }
  }

  /**
   * @see com.restfb.JsonMapper#toJavaObject(java.lang.String, java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T toJavaObject(String json, Class<T> type) {
    if (isBlank(json))
      if (jsonMappingErrorHandler.handleMappingError(json, type, null))
        return null;
      else
        throw new FacebookJsonMappingException("JSON is an empty string - can't map it.");

    if (json.startsWith("["))
      if (jsonMappingErrorHandler.handleMappingError(json, type, null))
        return null;
      else
        throw new FacebookJsonMappingException("JSON is an array but is being mapped as an object "
            + "- you should map it as a List instead. Offending JSON is '" + json + "'.");

    try {
      // Are we asked to map to JsonObject? If so, short-circuit right away.
      if (type.equals(JsonObject.class))
        return (T) new JsonObject(json);

      List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation = findFieldsWithAnnotation(type, Facebook.class);
      Set<String> facebookFieldNamesWithMultipleMappings = facebookFieldNamesWithMultipleMappings(fieldsWithAnnotation);

      // If there are no annotated fields, assume we're mapping to a built-in
      // type. If this is actually the empty object, just return a new instance
      // of the corresponding Java type.
      if (fieldsWithAnnotation.size() == 0)
        if (isEmptyObject(json)) {
          T instance = createInstance(type);

          // If there are any methods annotated with @JsonMappingCompleted,
          // invoke them.
          invokeJsonMappingCompletedMethods(instance);

          return instance;
        } else {
          return toPrimitiveJavaType(json, type);
        }

      // Facebook will sometimes return the string "null".
      // Check for that and bail early if we find it.
      if ("null".equals(json))
        return null;

      // Facebook will sometimes return the string "false" to mean null.
      // Check for that and bail early if we find it.
      if ("false".equals(json)) {
        if (logger.isLoggable(FINE))
          logger.fine("Encountered 'false' from Facebook when trying to map to " + type.getSimpleName()
              + " - mapping null instead.");
        return null;
      }

      JsonObject jsonObject = new JsonObject(json);
      T instance = createInstance(type);

      if (instance instanceof JsonObject)
        return (T) jsonObject;

      // For each Facebook-annotated field on the current Java object, pull data
      // out of the JSON object and put it in the Java object
      for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
        String facebookFieldName = getFacebookFieldName(fieldWithAnnotation);

        if (!jsonObject.has(facebookFieldName)) {
          if (logger.isLoggable(FINER))
            logger.finer("No JSON value present for '" + facebookFieldName + "', skipping. JSON is '" + json + "'.");

          continue;
        }

        fieldWithAnnotation.getField().setAccessible(true);

        // Set the Java field's value.
        //
        // If we notice that this Facebook field name is mapped more than once,
        // go into a special mode where we swallow any exceptions that occur
        // when mapping to the Java field. This is because Facebook will
        // sometimes return data in different formats for the same field name.
        // See issues 56 and 90 for examples of this behavior and discussion.
        if (facebookFieldNamesWithMultipleMappings.contains(facebookFieldName)) {
          try {
            fieldWithAnnotation.getField()
              .set(instance, toJavaType(fieldWithAnnotation, jsonObject, facebookFieldName));
          } catch (FacebookJsonMappingException e) {
            logMultipleMappingFailedForField(facebookFieldName, fieldWithAnnotation, json);
          } catch (JsonException e) {
            logMultipleMappingFailedForField(facebookFieldName, fieldWithAnnotation, json);
          }
        } else {
          try {
            fieldWithAnnotation.getField()
              .set(instance, toJavaType(fieldWithAnnotation, jsonObject, facebookFieldName));
          } catch (Exception e) {
            if (!jsonMappingErrorHandler.handleMappingError(json, type, e))
              throw e;
          }
        }
      }

      // If there are any methods annotated with @JsonMappingCompleted,
      // invoke them.
      invokeJsonMappingCompletedMethods(instance);

      return instance;
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, e))
        return null;
      else
        throw new FacebookJsonMappingException("Unable to map JSON to Java. Offending JSON is '" + json + "'.", e);
    }
  }

  /**
   * Finds and invokes methods on {@code object} that are annotated with the {@code @JsonMappingCompleted} annotation.
   * <p>
   * This will even work on {@code private} methods.
   * 
   * @param object
   *          The object on which to invoke the method.
   * @throws IllegalArgumentException
   *           If unable to invoke the method.
   * @throws IllegalAccessException
   *           If unable to invoke the method.
   * @throws InvocationTargetException
   *           If unable to invoke the method.
   */
  protected void invokeJsonMappingCompletedMethods(Object object) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    for (Method method : findMethodsWithAnnotation(object.getClass(), JsonMappingCompleted.class)) {
      method.setAccessible(true);

      if (method.getParameterTypes().length == 0)
        method.invoke(object);
      else if (method.getParameterTypes().length == 1 && JsonMapper.class.equals(method.getParameterTypes()[0]))
        method.invoke(object, this);
      else
        throw new FacebookJsonMappingException(format(
          "Methods annotated with @%s must take 0 parameters or a single %s parameter. Your method was %s",
          JsonMappingCompleted.class.getSimpleName(), JsonMapper.class.getSimpleName(), method));
    }
  }

  /**
   * Dumps out a log message when one of a multiple-mapped Facebook field name JSON-to-Java mapping operation fails.
   * 
   * @param facebookFieldName
   *          The Facebook field name.
   * @param fieldWithAnnotation
   *          The Java field to map to and its annotation.
   * @param json
   *          The JSON that failed to map to the Java field.
   */
  protected void logMultipleMappingFailedForField(String facebookFieldName,
      FieldWithAnnotation<Facebook> fieldWithAnnotation, String json) {
    if (!logger.isLoggable(FINER))
      return;

    Field field = fieldWithAnnotation.getField();

    if (logger.isLoggable(FINER))
      logger.finer("Could not map '" + facebookFieldName + "' to " + field.getDeclaringClass().getSimpleName() + "."
          + field.getName() + ", but continuing on because '" + facebookFieldName
          + "' is mapped to multiple fields in " + field.getDeclaringClass().getSimpleName() + ". JSON is " + json);
  }

  /**
   * For a Java field annotated with the {@code Facebook} annotation, figure out what the corresponding Facebook JSON
   * field name to map to it is.
   * 
   * @param fieldWithAnnotation
   *          A Java field annotated with the {@code Facebook} annotation.
   * @return The Facebook JSON field name that should be mapped to this Java field.
   */
  protected String getFacebookFieldName(FieldWithAnnotation<Facebook> fieldWithAnnotation) {
    String facebookFieldName = fieldWithAnnotation.getAnnotation().value();
    Field field = fieldWithAnnotation.getField();

    // If no Facebook field name was specified in the annotation, assume
    // it's the same name as the Java field
    if (isBlank(facebookFieldName)) {
      if (logger.isLoggable(FINEST))
        logger.finest("No explicit Facebook field name found for " + field
            + ", so defaulting to the field name itself (" + field.getName() + ")");

      facebookFieldName = field.getName();
    }

    return facebookFieldName;
  }

  /**
   * Finds any Facebook JSON fields that are mapped to more than 1 Java field.
   * 
   * @param fieldsWithAnnotation
   *          Java fields annotated with the {@code Facebook} annotation.
   * @return Any Facebook JSON fields that are mapped to more than 1 Java field.
   */
  protected Set<String> facebookFieldNamesWithMultipleMappings(List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation) {
    Map<String, Integer> facebookFieldsNamesWithOccurrenceCount = new HashMap<String, Integer>();
    Set<String> facebookFieldNamesWithMultipleMappings = new HashSet<String>();

    // Get a count of Facebook field name occurrences for each
    // @Facebook-annotated field
    for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
      String fieldName = getFacebookFieldName(fieldWithAnnotation);
      int occurrenceCount =
          facebookFieldsNamesWithOccurrenceCount.containsKey(fieldName) ? facebookFieldsNamesWithOccurrenceCount
            .get(fieldName) : 0;
      facebookFieldsNamesWithOccurrenceCount.put(fieldName, occurrenceCount + 1);
    }

    // Pull out only those field names with multiple mappings
    for (Entry<String, Integer> entry : facebookFieldsNamesWithOccurrenceCount.entrySet())
      if (entry.getValue() > 1)
        facebookFieldNamesWithMultipleMappings.add(entry.getKey());

    return unmodifiableSet(facebookFieldNamesWithMultipleMappings);
  }

  /**
   * @see com.restfb.JsonMapper#toJson(java.lang.Object)
   */
  @Override
  public String toJson(Object object) {
    // Delegate to recursive method
    return toJsonInternal(object, false).toString();
  }

  /**
   * @see com.restfb.JsonMapper#toJson(java.lang.Object, boolean)
   */
  @Override
  public String toJson(Object object, boolean ignoreNullValuedProperties) {
    return toJsonInternal(object, ignoreNullValuedProperties).toString();
  }

  /**
   * Recursively marshal the given {@code object} to JSON.
   * <p>
   * Used by {@link #toJson(Object)}.
   * 
   * @param object
   *          The object to marshal.
   * @param ignoreNullValuedProperties
   *          If this is {@code true}, no Javabean properties with {@code null} values will be included in the generated
   *          JSON.
   * @return JSON representation of the given {@code object}.
   * @throws FacebookJsonMappingException
   *           If an error occurs while marshaling to JSON.
   */
  protected Object toJsonInternal(Object object, boolean ignoreNullValuedProperties) {
    if (object == null)
      return NULL;

    if (object instanceof List<?>) {
      JsonArray jsonArray = new JsonArray();
      for (Object o : (List<?>) object)
        jsonArray.put(toJsonInternal(o, ignoreNullValuedProperties));

      return jsonArray;
    }

    if (object instanceof Map<?, ?>) {
      JsonObject jsonObject = new JsonObject();
      for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
        if (!(entry.getKey() instanceof String))
          throw new FacebookJsonMappingException("Your Map keys must be of type " + String.class
              + " in order to be converted to JSON.  Offending map is " + object);

        try {
          jsonObject.put((String) entry.getKey(), toJsonInternal(entry.getValue(), ignoreNullValuedProperties));
        } catch (JsonException e) {
          throw new FacebookJsonMappingException("Unable to process value '" + entry.getValue() + "' for key '"
              + entry.getKey() + "' in Map " + object, e);
        }
      }

      return jsonObject;
    }

    if (isPrimitive(object))
      return object;

    if (object instanceof BigInteger)
      return ((BigInteger) object).longValue();

    if (object instanceof BigDecimal)
      return ((BigDecimal) object).doubleValue();

    // We've passed the special-case bits, so let's try to marshal this as a
    // plain old Javabean...

    List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation =
        findFieldsWithAnnotation(object.getClass(), Facebook.class);

    JsonObject jsonObject = new JsonObject();

    // No longer throw an exception in this case. If there are multiple fields
    // with the same @Facebook value, it's luck of the draw which is picked for
    // JSON marshaling.
    // TODO: A better implementation would query each duplicate-mapped field. If
    // it has is a non-null value and the other duplicate values are null, use
    // the non-null field.
    Set<String> facebookFieldNamesWithMultipleMappings = facebookFieldNamesWithMultipleMappings(fieldsWithAnnotation);
    if (facebookFieldNamesWithMultipleMappings.size() > 0 && logger.isLoggable(FINE))
      logger.fine(format("Unable to convert to JSON because multiple @" + Facebook.class.getSimpleName()
          + " annotations for the same name are present: " + facebookFieldNamesWithMultipleMappings));

    for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
      String facebookFieldName = getFacebookFieldName(fieldWithAnnotation);
      fieldWithAnnotation.getField().setAccessible(true);

      try {
        Object fieldValue = fieldWithAnnotation.getField().get(object);

        if (!(ignoreNullValuedProperties && fieldValue == null))
          jsonObject.put(facebookFieldName, toJsonInternal(fieldValue, ignoreNullValuedProperties));
      } catch (Exception e) {
        throw new FacebookJsonMappingException("Unable to process field '" + facebookFieldName + "' for "
            + object.getClass(), e);
      }
    }

    return jsonObject;
  }

  /**
   * Given a {@code json} value of something like {@code MyValue} or {@code 123} , return a representation of that value
   * of type {@code type}.
   * <p>
   * This is to support non-legal JSON served up by Facebook for API calls like {@code Friends.get} (example result:
   * {@code [222333,1240079]}).
   * 
   * @param <T>
   *          The Java type to map to.
   * @param json
   *          The non-legal JSON to map to the Java type.
   * @param type
   *          Type token.
   * @return Java representation of {@code json}.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  @SuppressWarnings("unchecked")
  protected <T> T toPrimitiveJavaType(String json, Class<T> type) {

    if (String.class.equals(type)) {
      // If the string starts and ends with quotes, remove them, since Facebook
      // can serve up strings surrounded by quotes.
      if (json.length() > 1 && json.startsWith("\"") && json.endsWith("\"")) {
        json = json.replaceFirst("\"", "");
        json = json.substring(0, json.length() - 1);
      }

      return (T) json;
    }

    if (Integer.class.equals(type) || Integer.TYPE.equals(type))
      return (T) new Integer(json);
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type))
      return (T) new Boolean(json);
    if (Long.class.equals(type) || Long.TYPE.equals(type))
      return (T) new Long(json);
    if (Double.class.equals(type) || Double.TYPE.equals(type))
      return (T) new Double(json);
    if (Float.class.equals(type) || Float.TYPE.equals(type))
      return (T) new Float(json);
    if (BigInteger.class.equals(type))
      return (T) new BigInteger(json);
    if (BigDecimal.class.equals(type))
      return (T) new BigDecimal(json);

    if (jsonMappingErrorHandler.handleMappingError(json, type, null))
      return null;

    throw new FacebookJsonMappingException("Don't know how to map JSON to " + type
        + ". Are you sure you're mapping to the right class? " + "Offending JSON is '" + json + "'.");
  }

  /**
   * Extracts JSON data for a field according to its {@code Facebook} annotation and returns it converted to the proper
   * Java type.
   * 
   * @param fieldWithAnnotation
   *          The field/annotation pair which specifies what Java type to convert to.
   * @param jsonObject
   *          "Raw" JSON object to pull data from.
   * @param facebookFieldName
   *          Specifies what JSON field to pull "raw" data from.
   * @return A
   * @throws JsonException
   *           If an error occurs while mapping JSON to Java.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  protected Object toJavaType(FieldWithAnnotation<Facebook> fieldWithAnnotation, JsonObject jsonObject,
      String facebookFieldName) {
    Class<?> type = fieldWithAnnotation.getField().getType();
    Object rawValue = jsonObject.get(facebookFieldName);

    // Short-circuit right off the bat if we've got a null value.
    if (NULL.equals(rawValue))
      return null;

    if (String.class.equals(type)) {
      // Special handling here for better error checking.
      // Since JsonObject.getString() will return literal JSON text even if it's
      // _not_ a JSON string, we check the marshaled type and bail if needed.
      // For example, calling JsonObject.getString("results") on the below
      // JSON...
      // {"results":[{"name":"Mark Allen"}]}
      // ... would return the string "[{"name":"Mark Allen"}]" instead of
      // throwing an error. So we throw the error ourselves.

      // Per Antonello Naccarato, sometimes FB will return an empty JSON array
      // instead of an empty string. Look for that here.
      if (rawValue instanceof JsonArray)
        if (((JsonArray) rawValue).length() == 0) {
          if (logger.isLoggable(FINER))
            logger.finer("Coercing an empty JSON array " + "to an empty string for " + fieldWithAnnotation);

          return "";
        }

      // If the user wants a string, _always_ give her a string.
      // This is useful if, for example, you've got a @Facebook-annotated string
      // field that you'd like to have a numeric type shoved into.
      // User beware: this will turn *anything* into a string, which might lead
      // to results you don't expect.
      return rawValue.toString();
    }

    if (Integer.class.equals(type) || Integer.TYPE.equals(type))
      return new Integer(jsonObject.getInt(facebookFieldName));
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type))
      return new Boolean(jsonObject.getBoolean(facebookFieldName));
    if (Long.class.equals(type) || Long.TYPE.equals(type))
      return new Long(jsonObject.getLong(facebookFieldName));
    if (Double.class.equals(type) || Double.TYPE.equals(type))
      return new Double(jsonObject.getDouble(facebookFieldName));
    if (Float.class.equals(type) || Float.TYPE.equals(type))
      return new BigDecimal(jsonObject.getString(facebookFieldName)).floatValue();
    if (BigInteger.class.equals(type))
      return new BigInteger(jsonObject.getString(facebookFieldName));
    if (BigDecimal.class.equals(type))
      return new BigDecimal(jsonObject.getString(facebookFieldName));
    if (List.class.equals(type))
      return toJavaList(rawValue.toString(), getFirstParameterizedTypeArgument(fieldWithAnnotation.getField()));

    String rawValueAsString = rawValue.toString();

    // Hack for issue 76 where FB will sometimes return a Post's Comments as
    // "[]" instead of an object type (wtf)
    if (Comments.class.isAssignableFrom(type) && rawValue instanceof JsonArray) {
      if (logger.isLoggable(FINE))
        logger.fine("Encountered comment array '" + rawValueAsString + "' but expected a "
            + Comments.class.getSimpleName() + " object instead.  Working around that " + "by coercing into an empty "
            + Comments.class.getSimpleName() + " instance...");

      JsonObject workaroundJsonObject = new JsonObject();
      workaroundJsonObject.put("count", 0);
      workaroundJsonObject.put("data", new JsonArray());
      rawValueAsString = workaroundJsonObject.toString();
    }

    // Some other type - recurse into it
    return toJavaObject(rawValueAsString, type);
  }

  /**
   * Creates a new instance of the given {@code type}.
   * <p>
   * 
   * 
   * @param <T>
   *          Java type to map to.
   * @param type
   *          Type token.
   * @return A new instance of {@code type}.
   * @throws FacebookJsonMappingException
   *           If an error occurs when creating a new instance ({@code type} is inaccessible, doesn't have a no-arg
   *           constructor, etc.)
   */
  protected <T> T createInstance(Class<T> type) {
    String errorMessage =
        "Unable to create an instance of " + type
            + ". Please make sure that if it's a nested class, is marked 'static'. "
            + "It should have a no-argument constructor.";

    try {
      Constructor<T> defaultConstructor = type.getDeclaredConstructor();

      if (defaultConstructor == null)
        throw new FacebookJsonMappingException("Unable to find a default constructor for " + type);

      // Allows protected, private, and package-private constructors to be
      // invoked
      defaultConstructor.setAccessible(true);
      return defaultConstructor.newInstance();
    } catch (Exception e) {
      throw new FacebookJsonMappingException(errorMessage, e);
    }
  }

  /**
   * Is the given JSON equivalent to the empty object (<code>{}</code>)?
   * 
   * @param json
   *          The JSON to check.
   * @return {@code true} if the JSON is equivalent to the empty object, {@code false} otherwise.
   */
  protected boolean isEmptyObject(String json) {
    return "{}".equals(json);
  }

  /**
   * Callback interface which allows client code to specify how JSON mapping errors should be handled.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.2
   */
  public static interface JsonMappingErrorHandler {
    /**
     * This method will be called by {@code DefaultJsonMapper} if it encounters an error while attempting to map JSON to
     * a Java object.
     * <p>
     * You may perform any behavior you'd like here in response to an error, e.g. logging it.
     * <p>
     * If the mapper should continue processing, return {@code true} and {@code null} will be mapped to the target type.
     * If you would like the mapper to stop processing and throw
     * {@link com.restfb.exception.FacebookJsonMappingException}, return {@code false}.
     * 
     * @param unmappableJson
     *          The JSON that couldn't be mapped to a Java type.
     * @param targetType
     *          The Java type we were attempting to map to.
     * @param e
     *          The exception that occurred while performing the mapping operation, or {@code null} if there was no
     *          exception.
     * @return {@code true} to continue processing, {@code false} to throw a
     *         {@link com.restfb.exception.FacebookJsonMappingException}.
     */
    boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception e);
  }
}
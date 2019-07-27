/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static com.restfb.logging.RestFBLogger.MAPPER_LOGGER;
import static com.restfb.util.ObjectUtil.isEmptyCollectionOrMap;
import static com.restfb.util.ReflectionUtils.*;
import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.trimToEmpty;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.*;
import com.restfb.types.Comments;
import com.restfb.util.DateUtils;
import com.restfb.util.StringJsonUtils;
import com.restfb.util.ReflectionUtils.*;

/**
 * Default implementation of a JSON-to-Java mapper.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultJsonMapper implements JsonMapper {

  /**
   * Helper to convert {@link JsonValue} into a given type
   */
  private final JsonHelper jsonHelper;

  /**
   * Creates a JSON mapper which will throw {@link com.restfb.exception.FacebookJsonMappingException} whenever an error
   * occurs when mapping JSON data to Java objects.
   */
  public DefaultJsonMapper() {
    jsonHelper = new JsonHelper();
  }

  @Override
  public <T> List<T> toJavaList(String json, Class<T> type) {
    Optional.ofNullable(type)
      .orElseThrow(() -> new FacebookJsonMappingException("You must specify the Java type to map to."));

    json = trimToEmpty(json);

    if (isBlank(json)) {
      throw new FacebookJsonMappingException("JSON is an empty string - can't map it.");
    }

    if (StringJsonUtils.isObject(json)) {
      // Sometimes Facebook returns the empty object {} when it really should be
      // returning an empty list [] (example: do an FQL query for a user's
      // affiliations - it's a list except when there are none, then it turns
      // into an object). Check for that special case here.
      if (StringJsonUtils.isEmptyObject(json)) {
        MAPPER_LOGGER
          .trace("Encountered \\{} when we should've seen []. Mapping the \\{} as an empty list and moving on...");

        return new ArrayList<>();
      }

      // Special case: if the only element of this object is an array called
      // "data", then treat it as a list. The Graph API uses this convention for
      // connections and in a few other places, e.g. comments on the Post
      // object.
      // Doing this simplifies mapping, so we don't have to worry about having a
      // little placeholder object that only has a "data" value.
      try {
        JsonObject jsonObject = Json.parse(json).asObject();
        List<String> fieldNames = jsonObject.names();

        if (!fieldNames.isEmpty()) {
          boolean hasSingleDataProperty = fieldNames.size() == 1;
          Object jsonDataObject = jsonObject.get(fieldNames.get(0));

          if (!hasSingleDataProperty && !(jsonDataObject instanceof JsonArray)) {
            throw new FacebookJsonMappingException(
              "JSON is an object but is being mapped as a list instead. Offending JSON is '" + json + "'.");
          }

          json = jsonDataObject.toString();
        }
      } catch (ParseException e) {
        // Should never get here, but just in case...
        throw new FacebookJsonMappingException("Unable to convert Facebook response JSON to a list of " + type.getName()
            + " instances.  Offending JSON is '" + json + "'.",
          e);
      }
    }

    try {
      JsonArray jsonArray = Json.parse(json).asArray();
      List<T> list = new ArrayList<>(jsonArray.size());
      for (JsonValue jsonValue : jsonArray) {
        String innerJson = jsonHelper.getStringFrom(jsonValue);
        // the inner JSON starts with square brackets but the parser don't think this is a JSON array
        // so we think the parser is right and add quotes around the string
        // solves Issue #719
        if (jsonValue.isString() && innerJson.startsWith("[")) {
          innerJson = '"' + innerJson + '"';
        }
        list.add(toJavaObject(innerJson, type));
      }
      return unmodifiableList(list);
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      throw new FacebookJsonMappingException(
        "Unable to convert Facebook response JSON to a list of " + type.getName() + " instances", e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T toJavaObject(String json, Class<T> type) {
    if (StringJsonUtils.isEmptyList(json)) {
      return toJavaObject(StringJsonUtils.EMPTY_OBJECT, type);
    }

    basicJsonStringChecks(json);

    try {
      // Are we asked to map to JsonObject? If so, short-circuit right away.
      if (type.equals(JsonObject.class)) {
        return (T) Json.parse(json).asObject();
      }

      List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation = findFieldsWithAnnotation(type, Facebook.class);
      Set<String> facebookFieldNamesWithMultipleMappings = facebookFieldNamesWithMultipleMappings(fieldsWithAnnotation);

      // If there are no annotated fields, assume we're mapping to a built-in
      // type. If this is actually the empty object, just return a new instance
      // of the corresponding Java type.
      if (fieldsWithAnnotation.isEmpty()) {
        if (StringJsonUtils.isEmptyObject(json)) {
          T instance = createInstance(type);

          // If there are any methods annotated with @JsonMappingCompleted,
          // invoke them.
          invokeJsonMappingCompletedMethods(instance);

          return instance;
        } else {
          return toPrimitiveJavaType(json, type);
        }
      }

      // Facebook will sometimes return the string "null".
      // Check for that and bail early if we find it.
      if (StringJsonUtils.isNull(json)) {
        return null;
      }

      // Facebook will sometimes return the string "false" to mean null.
      // Check for that and bail early if we find it.
      if (StringJsonUtils.isFalse(json)) {
        MAPPER_LOGGER.debug("Encountered 'false' from Facebook when trying to map to {} - mapping null instead.",
          type.getSimpleName());
        return null;
      }

      JsonValue jsonValue = Json.parse(json);
      T instance = createInstance(type);

      if (instance instanceof JsonObject) {
        return (T) jsonValue.asObject();
      }

      if (!jsonValue.isObject()) {
        return null;
      }

      JsonObject jsonObject = jsonValue.asObject();

      // For each Facebook-annotated field on the current Java object, pull data
      // out of the JSON object and put it in the Java object
      for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
        String facebookFieldName = getFacebookFieldName(fieldWithAnnotation);

        if (jsonObject.get(facebookFieldName) == null) {
          MAPPER_LOGGER.trace("No JSON value present for '{}', skipping. JSON is '{}'.", facebookFieldName, json);
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
            fieldWithAnnotation.getField().set(instance,
              toJavaType(fieldWithAnnotation, jsonObject, facebookFieldName));
          } catch (FacebookJsonMappingException | ParseException | UnsupportedOperationException e) {
            logMultipleMappingFailedForField(facebookFieldName, fieldWithAnnotation, json);
          }
        } else {
          fieldWithAnnotation.getField().set(instance, toJavaType(fieldWithAnnotation, jsonObject, facebookFieldName));
        }
      }

      // If there are any methods annotated with @JsonMappingCompleted,
      // invoke them.
      invokeJsonMappingCompletedMethods(instance);

      return instance;
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      throw new FacebookJsonMappingException("Unable to map JSON to Java. Offending JSON is '" + json + "'.", e);
    }
  }

  private void basicJsonStringChecks(String json) {
    if (isBlank(json)) {
      throw new FacebookJsonMappingException("JSON is an empty string - can't map it.");
    }

    if (StringJsonUtils.isList(json)) {
      throw new FacebookJsonMappingException("JSON is an array but is being mapped as an object "
          + "- you should map it as a List instead. Offending JSON is '" + json + "'.");
    }
  }

  /**
   * Finds and invokes methods on {@code object} that are annotated with the {@code @JsonMappingCompleted} annotation.
   * <p>
   * This will even work on {@code private} methods.
   * 
   * @param object
   *          The object on which to invoke the method.
   * @throws IllegalAccessException
   *           If unable to invoke the method.
   * @throws InvocationTargetException
   *           If unable to invoke the method.
   */
  protected void invokeJsonMappingCompletedMethods(Object object)
      throws IllegalAccessException, InvocationTargetException {
    for (Method method : findMethodsWithAnnotation(object.getClass(), JsonMappingCompleted.class)) {
      method.setAccessible(true);

      if (method.getParameterTypes().length == 0)
        method.invoke(object);
      else if (method.getParameterTypes().length == 1 && JsonMapper.class.equals(method.getParameterTypes()[0]))
        method.invoke(object, this);
      else
        throw new FacebookJsonMappingException(
          format("Methods annotated with @%s must take 0 parameters or a single %s parameter. Your method was %s",
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
    if (!MAPPER_LOGGER.isTraceEnabled()) {
      return;
    }

    Field field = fieldWithAnnotation.getField();

    MAPPER_LOGGER.trace(
      "Could not map '{}' to {}. {}, but continuing on because '{}"
          + "' is mapped to multiple fields in {}. JSON is {}",
      facebookFieldName, field.getDeclaringClass().getSimpleName(), field.getName(), facebookFieldName,
      field.getDeclaringClass().getSimpleName(), json);
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
      MAPPER_LOGGER.trace("No explicit Facebook field name found for {}, so defaulting to the field name itself ({})",
        field, field.getName());

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
  protected Set<String> facebookFieldNamesWithMultipleMappings(
      List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation) {
    Map<String, Integer> facebookFieldsNamesWithOccurrenceCount = new HashMap<>();
    Set<String> facebookFieldNamesWithMultipleMappings = new HashSet<>();

    // Get a count of Facebook field name occurrences for each
    // @Facebook-annotated field
    for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
      String fieldName = getFacebookFieldName(fieldWithAnnotation);
      int occurrenceCount = facebookFieldsNamesWithOccurrenceCount.getOrDefault(fieldName, 0);
      facebookFieldsNamesWithOccurrenceCount.put(fieldName, occurrenceCount + 1);
    }

    // Pull out only those field names with multiple mappings
    facebookFieldNamesWithMultipleMappings.addAll(facebookFieldsNamesWithOccurrenceCount.entrySet().stream()
      .filter(entry -> entry.getValue() > 1).map(Entry::getKey).collect(Collectors.toList()));

    return unmodifiableSet(facebookFieldNamesWithMultipleMappings);
  }

  @Override
  public String toJson(Object object) {
    return toJson(object, false);
  }

  @Override
  public String toJson(Object object, boolean ignoreNullValuedProperties) {
    JsonValue jsonObj = toJsonInternal(object, ignoreNullValuedProperties);
    return jsonHelper.getStringFrom(jsonObj);
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
  protected JsonValue toJsonInternal(Object object, boolean ignoreNullValuedProperties) {
    if (object == null) {
      return Json.NULL;
    }

    if (object instanceof JsonValue) {
      return (JsonValue) object;
    }

    if (object instanceof List<?>) {
      JsonArray jsonArray = new JsonArray();
      for (Object o : (List<?>) object)
        jsonArray.add(toJsonInternal(o, ignoreNullValuedProperties));

      return jsonArray;
    }

    if (object instanceof Map<?, ?>) {
      JsonObject jsonObject = new JsonObject();
      for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
        if (!(entry.getKey() instanceof String)) {
          throw new FacebookJsonMappingException("Your Map keys must be of type " + String.class
              + " in order to be converted to JSON.  Offending map is " + object);
        }

        try {
          jsonObject.add((String) entry.getKey(), toJsonInternal(entry.getValue(), ignoreNullValuedProperties));
        } catch (ParseException | IllegalArgumentException e) {
          throw new FacebookJsonMappingException(
            "Unable to process value '" + entry.getValue() + "' for key '" + entry.getKey() + "' in Map " + object, e);
        }
      }

      return jsonObject;
    }

    if (isPrimitive(object)) {
      return primitiveToJsonValue(object);
    }

    if (object instanceof BigInteger) {
      return Json.value(((BigInteger) object).longValue());
    }

    if (object instanceof BigDecimal) {
      return Json.value(((BigDecimal) object).doubleValue());
    }

    if (object instanceof Enum) {
      return Json.value(((Enum) object).name());
    }

    if (object instanceof Date) {
      return Json.value(DateUtils.toLongFormatFromDate((Date) object));
    }

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
    if (!facebookFieldNamesWithMultipleMappings.isEmpty() && MAPPER_LOGGER.isDebugEnabled()) {
      MAPPER_LOGGER.debug(
        "Unable to convert to JSON because multiple @{} annotations for the same name are present: {}",
        Facebook.class.getSimpleName(), facebookFieldNamesWithMultipleMappings);
    }

    for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
      String facebookFieldName = getFacebookFieldName(fieldWithAnnotation);
      fieldWithAnnotation.getField().setAccessible(true);

      try {
        Object fieldValue = fieldWithAnnotation.getField().get(object);

        if (!(ignoreNullValuedProperties && (fieldValue == null || isEmptyCollectionOrMap(fieldValue)))) {
          jsonObject.add(facebookFieldName, toJsonInternal(fieldValue, ignoreNullValuedProperties));
        }
      } catch (Exception e) {
        throw new FacebookJsonMappingException(
          "Unable to process field '" + facebookFieldName + "' for " + object.getClass(), e);
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

    // cleanup the json string
    if (json.length() > 1 && json.startsWith("\"") && json.endsWith("\"")) {
      json = json.replaceFirst("\"", "");
      json = json.substring(0, json.length() - 1);
    }

    if (String.class.equals(type)) {
      return (T) json;
    }
    if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
      return (T) Integer.valueOf(json);
    }
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) {
      return (T) Boolean.valueOf(json);
    }
    if (Long.class.equals(type) || Long.TYPE.equals(type)) {
      return (T) Long.valueOf(json);
    }
    if (Double.class.equals(type) || Double.TYPE.equals(type)) {
      return (T) Double.valueOf(json);
    }
    if (Float.class.equals(type) || Float.TYPE.equals(type)) {
      return (T) Float.valueOf(json);
    }
    if (BigInteger.class.equals(type)) {
      return (T) new BigInteger(json);
    }
    if (BigDecimal.class.equals(type)) {
      return (T) new BigDecimal(json);
    }

    throw new FacebookJsonMappingException("Don't know how to map JSON to " + type
        + ". Are you sure you're mapping to the right class?\nOffending JSON is '" + json + "'.");
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
   * @throws ParseException
   *           If an error occurs while mapping JSON to Java.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  protected Object toJavaType(FieldWithAnnotation<Facebook> fieldWithAnnotation, JsonObject jsonObject,
      String facebookFieldName) {
    Class<?> type = fieldWithAnnotation.getField().getType();
    JsonValue rawValue = jsonObject.get(facebookFieldName);

    // Short-circuit right off the bat if we've got a null value.
    if (rawValue.isNull()) {
      return null;
    }

    if (String.class.equals(type)) {
      /*
       * Special handling here for better error checking.
       *
       * Since {@code JsonObject.getString()} will return literal JSON text even if it's _not_ a JSON string, we check
       * the marshaled type and bail if needed. For example, calling {@code JsonObject.getString("results")} on the
       * below JSON...
       *
       * <code> { "results":[ {"name":"Mark Allen"} ] } </code>
       *
       * ... would return the string {@code "[{"name":"Mark Allen"}]"} instead of throwing an error. So we throw the
       * error ourselves.
       *
       * Per Antonello Naccarato, sometimes FB will return an empty JSON array instead of an empty string. Look for that
       * here.
       */
      if (rawValue.isArray() && rawValue.asArray().isEmpty()) {
        MAPPER_LOGGER.trace("Coercing an empty JSON array to an empty string for {}", fieldWithAnnotation);

        return "";
      }

      /*
       * If the user wants a string, _always_ give her a string.
       *
       * This is useful if, for example, you've got a @Facebook-annotated string field that you'd like to have a numeric
       * type shoved into.
       *
       * User beware: this will turn *anything* into a string, which might lead to results you don't expect.
       */
      return jsonHelper.getStringFrom(rawValue);
    }

    if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
      return jsonHelper.getIntegerFrom(rawValue);
    }
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) {
      return jsonHelper.getBooleanFrom(rawValue);
    }
    if (Long.class.equals(type) || Long.TYPE.equals(type)) {
      return jsonHelper.getLongFrom(rawValue);
    }
    if (Double.class.equals(type) || Double.TYPE.equals(type)) {
      return jsonHelper.getDoubleFrom(rawValue);
    }
    if (Float.class.equals(type) || Float.TYPE.equals(type)) {
      return jsonHelper.getFloatFrom(rawValue);
    }
    if (BigInteger.class.equals(type)) {
      return jsonHelper.getBigIntegerFrom(rawValue);
    }
    if (BigDecimal.class.equals(type)) {
      return jsonHelper.getBigDecimalFrom(rawValue);
    }
    if (List.class.equals(type)) {
      return toJavaList(rawValue.toString(), getFirstParameterizedTypeArgument(fieldWithAnnotation.getField()));
    }
    if (Map.class.equals(type)) {
      return convertJsonObjectToMap(rawValue.toString(), fieldWithAnnotation.getField());
    }

    if (type.isEnum()) {
      Class<? extends Enum> enumType = type.asSubclass(Enum.class);
      try {
        return Enum.valueOf(enumType, rawValue.asString());
      } catch (IllegalArgumentException iae) {
        MAPPER_LOGGER.debug("Cannot map string {} to enum {}, try fallback toUpperString next...", rawValue.asString(),
          enumType.getName());
      }
      try {
        return Enum.valueOf(enumType, rawValue.asString().toUpperCase());
      } catch (IllegalArgumentException iae) {
        MAPPER_LOGGER.debug("Mapping string {} to enum {} not possible", rawValue.asString(), enumType.getName());
      }
    }

    if (Date.class.equals(type)) {
      return DateUtils.toDateFromLongFormat(jsonHelper.getStringFrom(rawValue));
    }

    String rawValueAsString = jsonHelper.getStringFrom(rawValue);

    // Hack for issue #76 where FB will sometimes return a Post's Comments as
    // "[]" instead of an object type (wtf)F
    if (Comments.class.isAssignableFrom(type) && rawValue instanceof JsonArray) {
      MAPPER_LOGGER.debug(
        "Encountered comment array '{}' but expected a {} object instead.  Working around that by coercing "
            + "into an empty {} instance...",
        rawValueAsString, Comments.class.getSimpleName(), Comments.class.getSimpleName());

      JsonObject workaroundJsonObject = new JsonObject();
      workaroundJsonObject.add("total_count", 0);
      workaroundJsonObject.add("data", new JsonArray());
      rawValueAsString = workaroundJsonObject.toString();
    }

    // Some other type - recurse into it
    return toJavaObject(rawValueAsString, type);
  }

  private Map convertJsonObjectToMap(String json, Field field) {
    Class<?> firstParam = getFirstParameterizedTypeArgument(field);
    if (!String.class.equals(firstParam)) {
      throw new FacebookJsonMappingException("The java type map needs to have a 'String' key, but is " + firstParam);
    }

    Class<?> secondParam = getSecondParameterizedTypeArgument(field);

    if (json.startsWith("{")) {
      JsonObject jsonObject = Json.parse(json).asObject();
      Map<String, Object> map = new HashMap();
      for (String key : jsonObject.names()) {
        String value = jsonHelper.getStringFrom(jsonObject.get(key));
        map.put(key, toJavaObject(value, secondParam));
      }
      return map;
    }

    return null;
  }

  private JsonValue primitiveToJsonValue(Object object) {
    if (object == null) {
      return Json.NULL;
    }

    Class<?> type = object.getClass();

    if (object instanceof String) {
      return Json.value((String) object);
    }

    if (object instanceof Integer || Integer.TYPE.equals(type)) {
      return Json.value((Integer) object);
    }

    if (object instanceof Boolean || Boolean.TYPE.equals(type)) {
      return Json.value((Boolean) object);
    }

    if (object instanceof Long || Long.TYPE.equals(type)) {
      return Json.value((Long) object);
    }

    if (object instanceof Double || Double.TYPE.equals(type)) {
      return Json.value((Double) object);
    }

    if (object instanceof Float || Float.TYPE.equals(type)) {
      return Json.value((Float) object);
    }

    if (object instanceof Byte || Byte.TYPE.equals(type)) {
      return Json.value((Byte) object);
    }
    if (object instanceof Short || Short.TYPE.equals(type)) {
      return Json.value((Short) object);
    }

    if (object instanceof Character || Character.TYPE.equals(type)) {
      return Json.value(Character.toString((Character) object));
    }

    return Json.NULL;

  }
}
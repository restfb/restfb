/*
 * Copyright (c) 2010 Mark Allen.
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

import static com.restfb.json.JSONObject.NULL;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.restfb.ReflectionUtils.FieldWithAnnotation;
import com.restfb.json.JSONArray;
import com.restfb.json.JSONException;
import com.restfb.json.JSONObject;

/**
 * Default implementation of a JSON-to-Java mapper.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultJsonMapper implements JsonMapper {
  private static final Logger logger =
      Logger.getLogger(DefaultJsonMapper.class);

  /**
   * @see com.restfb.JsonMapper#toJavaList(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> List<T> toJavaList(String json, Class<T> type)
      throws FacebookJsonMappingException {
    json = StringUtils.trimToEmpty(json);

    if (StringUtils.isBlank(json))
      throw new FacebookJsonMappingException(
        "JSON is an empty string - can't map it.");

    if (DefaultEnumClass.class.equals(type))
      throw new FacebookJsonMappingException(
        "You must specify the 'contains' attribute of the @"
            + Facebook.class.getSimpleName()
            + " annotation when applying it to a List because of type erasure. "
            + "Offending JSON is '" + json + "'.");

    if (json.startsWith("{")) {
      // Sometimes Facebook returns the empty object {} when it really should be
      // returning an empty list [] (example: do an FQL query for a user's
      // affiliations - it's a list except when there are none, then it turns
      // into an object). Check for that special case here.
      if (isEmptyObject(json)) {
        if (logger.isTraceEnabled())
          logger.trace("Encountered {} when we should've seen []. "
              + "Mapping the {} as an empty list and moving on...");
        return new ArrayList<T>();
      }

      throw new FacebookJsonMappingException(
        "JSON is an object but is being mapped as a list "
            + "instead. Offending JSON is '" + json + "'.");
    }

    try {
      List<T> list = new ArrayList<T>();

      JSONArray jsonArray = new JSONArray(json);
      for (int i = 0; i < jsonArray.length(); i++)
        list.add(toJavaObject(jsonArray.get(i).toString(), type));

      return list;
    } catch (Exception e) {
      throw new FacebookJsonMappingException(
        "Unable to convert Facebook response " + "JSON to a list of "
            + type.getName() + " instances", e);
    }
  }

  /**
   * @see com.restfb.JsonMapper#toJavaObject(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> T toJavaObject(String json, Class<T> type)
      throws FacebookJsonMappingException {
    json = StringUtils.trimToEmpty(json);

    if (StringUtils.isBlank(json))
      throw new FacebookJsonMappingException(
        "JSON is an empty string - can't map it.");

    if (json.startsWith("["))
      throw new FacebookJsonMappingException(
        "JSON is an array but is being mapped as an object "
            + "- you should map it as a List instead. Offending JSON is '"
            + json + "'.");

    try {
      List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation =
          ReflectionUtils.findFieldsWithAnnotation(type, Facebook.class);

      // If there are no annotated fields, assume we're mapping to a built-in
      // type. If this is actually the empty object, just return a new instance
      // of the corresponding Java type.
      if (fieldsWithAnnotation.size() == 0)
        if (isEmptyObject(json))
          return type.newInstance();
        else
          return toPrimitiveJavaType(json, type);

      JSONObject jsonObject = new JSONObject(json);
      T instance = type.newInstance();

      // For each Facebook-annotated field on the current Java object, pull data
      // out of the JSON object and put it in the Java object
      for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
        // TODO: duplicate logic, pull out when we support automatic
        // camel-casing

        String facebookFieldName = fieldWithAnnotation.getAnnotation().value();
        Field field = fieldWithAnnotation.getField();

        // If no Facebook field name was specified in the annotation, assume
        // it's the same name as the Java field
        if (StringUtils.isBlank(facebookFieldName)) {
          if (logger.isTraceEnabled())
            logger.trace("No explicit Facebook field name found for " + field
                + ", so defaulting to the field name itself ("
                + field.getName() + ")");
          facebookFieldName = field.getName();
        }

        if (!jsonObject.has(facebookFieldName)) {
          if (logger.isTraceEnabled())
            logger.trace("No JSON value present for '" + facebookFieldName
                + "', skipping. Offending JSON is '" + json + "'.");
          continue;
        }

        // Set the field's value
        field.setAccessible(true);
        field.set(instance, toJavaType(fieldWithAnnotation, jsonObject,
          facebookFieldName));
      }

      return instance;
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      throw new FacebookJsonMappingException(
        "Unable to map JSON to Java. Offending JSON is '" + json + "'.", e);
    }
  }

  /**
   * @see com.restfb.JsonMapper#toJson(java.lang.Object)
   */
  @Override
  public String toJson(Object object) throws FacebookJsonMappingException {
    // Delegate to recursive method
    return toJsonInternal(object).toString();
  }

  /**
   * TODO: Document
   * 
   * @param object
   * @return
   * @throws FacebookJsonMappingException
   */
  protected Object toJsonInternal(Object object)
      throws FacebookJsonMappingException {
    if (object == null)
      return NULL;

    if (object instanceof List<?>) {
      JSONArray jsonArray = new JSONArray();
      for (Object o : (List<?>) object)
        jsonArray.put(toJsonInternal(o));

      return jsonArray;
    }

    if (object instanceof Map<?, ?>) {
      JSONObject jsonObject = new JSONObject();
      for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
        if (!(entry.getKey() instanceof String))
          throw new FacebookJsonMappingException(
            "Your Map keys must be of type " + String.class
                + " in order to be converted to JSON.  Offending map is "
                + object);

        try {
          jsonObject.put((String) entry.getKey(), toJsonInternal(entry
            .getValue()));
        } catch (JSONException e) {
          throw new FacebookJsonMappingException("Unable to process value '"
              + entry.getValue() + "' for key '" + entry.getKey() + "' in Map "
              + object, e);
        }
      }

      return jsonObject;
    }

    if (object instanceof String || ReflectionUtils.isPrimitive(object))
      return object;

    if (object instanceof BigInteger)
      return ((BigInteger) object).longValue();

    if (object instanceof BigDecimal)
      return ((BigDecimal) object).doubleValue();

    // We've passed the special-case bits, so let's try to marshal this as a
    // plain old Javabean...

    List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation =
        ReflectionUtils.findFieldsWithAnnotation(object.getClass(),
          Facebook.class);

    JSONObject jsonObject = new JSONObject();

    for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {

      // TODO: duplicate logic, pull out when we support automatic camel-casing
      String facebookFieldName = fieldWithAnnotation.getAnnotation().value();
      Field field = fieldWithAnnotation.getField();

      // If no Facebook field name was specified in the annotation, assume
      // it's the same name as the Java field
      if (StringUtils.isBlank(facebookFieldName)) {
        if (logger.isTraceEnabled())
          logger.trace("No explicit Facebook field name found for " + field
              + ", so defaulting to the field name itself (" + field.getName()
              + ")");
        facebookFieldName = field.getName();
      }

      field.setAccessible(true);

      try {
        jsonObject.put(facebookFieldName, toJsonInternal(field.get(object)));
      } catch (Exception e) {
        throw new FacebookJsonMappingException("Unable to process field '"
            + facebookFieldName + "' for " + object.getClass(), e);
      }
    }

    return jsonObject;
  }

  /**
   * Given a {@code json} value of something like {@code MyValue} or {@code 123}
   * , return a representation of that value of type {@code type}.
   * <p>
   * This is to support non-legal JSON served up by Facebook for API calls like
   * {@code Friends.get} (example result: {@code [222333,1240079]}).
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
  protected <T> T toPrimitiveJavaType(String json, Class<T> type)
      throws FacebookJsonMappingException {

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

    throw new FacebookJsonMappingException("Don't know how to map JSON to "
        + type + ". Are you sure you're mapping to the right class? "
        + "Offending JSON is '" + json + "'.");
  }

  /**
   * Extracts JSON data for a field according to its {@code Facebook} annotation
   * and returns it converted to the proper Java type.
   * 
   * @param fieldWithAnnotation
   *          The field/annotation pair which specifies what Java type to
   *          convert to.
   * @param jsonObject
   *          "Raw" JSON object to pull data from.
   * @param facebookFieldName
   *          Specifies what JSON field to pull "raw" data from.
   * @return A
   * @throws JSONException
   *           If an error occurs while mapping JSON to Java.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  protected Object toJavaType(
      FieldWithAnnotation<Facebook> fieldWithAnnotation, JSONObject jsonObject,
      String facebookFieldName) throws JSONException,
      FacebookJsonMappingException {
    Class<?> type = fieldWithAnnotation.getField().getType();
    Object rawValue = jsonObject.get(facebookFieldName);

    // Short-circuit right off the bat if we've got a null value.
    if (NULL.equals(rawValue))
      return null;

    if (String.class.equals(type)) {
      // Special handling here for better error checking.
      // Since JSONObject.getString() will return literal JSON text even if it's
      // _not_ a JSON string, we check the marshaled type and bail if needed.
      // For example, calling JSONObject.getString("results") on the below
      // JSON...
      // {"results":[{"name":"Mark Allen"}]}
      // ... would return the string "[{"name":"Mark Allen"}]" instead of
      // throwing an error. So we throw the error ourselves.

      // Per Antonello Naccarato, sometimes FB will return an empty JSON array
      // instead of an empty string. Look for that here.
      if (rawValue instanceof JSONArray)
        if (((JSONArray) rawValue).length() == 0) {
          if (logger.isDebugEnabled())
            logger.debug("Coercing an empty JSON array "
                + "to an empty string for " + fieldWithAnnotation);
          return "";
        }

      if (rawValue instanceof String)
        return rawValue;

      throw new FacebookJsonMappingException("You cannot map the '"
          + facebookFieldName
          + "' field in the following JSON as a String type: "
          + jsonObject.toString());
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
      return new BigDecimal(jsonObject.getString(facebookFieldName))
        .floatValue();
    if (BigInteger.class.equals(type))
      return new BigInteger(jsonObject.getString(facebookFieldName));
    if (BigDecimal.class.equals(type))
      return new BigDecimal(jsonObject.getString(facebookFieldName));
    if (List.class.equals(type))
      return toJavaList(rawValue.toString(), fieldWithAnnotation
        .getAnnotation().contains());

    // Some other type - recurse into it
    return toJavaObject(rawValue.toString(), type);
  }

  /**
   * Is the given JSON equivalent to the empty object (<code>{}</code>)?
   * 
   * @param json
   *          The JSON to check.
   * @return {@code true} if the JSON is equivalent to the empty object, {@code
   *         false} otherwise.
   */
  protected boolean isEmptyObject(String json) {
    // TODO: nicer way to do this than the replaceAll() call?
    return "{}".equals(json.replaceAll("\\s", ""));
  }
}
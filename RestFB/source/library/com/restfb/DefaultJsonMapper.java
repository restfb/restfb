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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.restfb.ReflectionUtils.FieldWithAnnotation;
import com.restfb.json.JSONArray;
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
   * @see com.restfb.JsonMapper#toJavaObject(java.lang.String, java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T toJavaObject(String json, Class<T> type)
      throws FacebookJsonMappingException {
    if (StringUtils.isBlank(json))
      throw new FacebookJsonMappingException(
        "JSON is an empty string - can't map it.");

    try {
      List<FieldWithAnnotation<Facebook>> fieldsWithAnnotation =
          ReflectionUtils.findFieldsWithAnnotation(type, Facebook.class);

      // If there are no annotated fields, assume we're mapping to a built-in
      // type.
      if (fieldsWithAnnotation.size() == 0) {
        if (String.class.equals(type))
          return (T) json;
        else if (Integer.class.equals(type) || Integer.TYPE.equals(type))
          return (T) new Integer(json);
        else if (Boolean.class.equals(type) || Boolean.TYPE.equals(type))
          return (T) new Boolean(json);
        else if (Long.class.equals(type) || Long.TYPE.equals(type))
          return (T) new Long(json);
        else if (Double.class.equals(type) || Double.TYPE.equals(type))
          return (T) new Double(json);
        else if (BigInteger.class.equals(type))
          return (T) new BigInteger(json);
        else if (BigDecimal.class.equals(type))
          return (T) new BigDecimal(json);

        throw new FacebookJsonMappingException("Don't know how to map JSON to "
            + type.getClass()
            + ". Are you sure you're mapping to the right class? "
            + "Offending JSON is '" + json + "'.");
      }

      if (json.startsWith("["))
        throw new FacebookJsonMappingException(
          "JSON is an array but is being mapped as an object "
              + "- you should map it as a List instead. Offending JSON is '"
              + json + "'.");

      JSONObject jsonObject = new JSONObject(json);
      T instance = type.newInstance();

      for (FieldWithAnnotation<Facebook> fieldWithAnnotation : fieldsWithAnnotation) {
        String facebookFieldName = fieldWithAnnotation.getAnnotation().value();

        Field field = fieldWithAnnotation.getField();
        Class<?> fieldType = field.getType();
        Object fieldValue = null;

        if (!jsonObject.has(facebookFieldName)) {
          if (logger.isDebugEnabled())
            logger.debug("No JSON value present for '" + facebookFieldName
                + "', skipping. Offending JSON is '" + json + "'.");
          continue;
        }

        if (String.class.equals(fieldType)) {
          fieldValue = jsonObject.getString(facebookFieldName);
        } else if (Integer.class.equals(fieldType)
            || Integer.TYPE.equals(fieldType)) {
          fieldValue = new Integer(jsonObject.getInt(facebookFieldName));
        } else if (Boolean.class.equals(fieldType)
            || Boolean.TYPE.equals(fieldType)) {
          fieldValue = new Boolean(jsonObject.getBoolean(facebookFieldName));
        } else if (Long.class.equals(fieldType) || Long.TYPE.equals(fieldType)) {
          fieldValue = new Long(jsonObject.getLong(facebookFieldName));
        } else if (Double.class.equals(fieldType)
            || Double.TYPE.equals(fieldType)) {
          fieldValue = new Double(jsonObject.getDouble(facebookFieldName));
        } else if (BigInteger.class.equals(fieldType)) {
          fieldValue = new BigInteger(jsonObject.getString(facebookFieldName));
        } else if (BigDecimal.class.equals(fieldType)) {
          fieldValue = new BigDecimal(jsonObject.getString(facebookFieldName));
        } else if (List.class.equals(fieldType)) {
          JSONArray array = jsonObject.getJSONArray(facebookFieldName);
          Class<?> containsType =
              fieldWithAnnotation.getAnnotation().contains();

          List<Object> list = new ArrayList<Object>();
          for (int i = 0; i < array.length(); i++)
            list.add(toJavaObject(array.get(i).toString(), containsType));

          fieldValue = list;
        } else {
          // Some other type - recurse into it
          fieldValue =
              toJavaObject(jsonObject.get(facebookFieldName).toString(),
                fieldType);
        }

        field.setAccessible(true);
        field.set(instance, fieldValue);
      }

      return instance;
    } catch (FacebookJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      throw new FacebookJsonMappingException(
        "Unable to map JSON to Java. Offending JSON is '" + json + "'.", e);
    }
  }
}
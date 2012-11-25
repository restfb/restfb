package com.restfb.json;

/**
 * The <code>JsonString</code> interface allows a <code>toJSONString()</code> method so that a class can change the
 * behavior of <code>JsonObject.toString()</code>, <code>JsonArray.toString()</code>, and <code>JsonWriter.value(</code>
 * Object<code>)</code>. The <code>toJSONString</code> method will be used instead of the default behavior of using the
 * Object's <code>toString()</code> method and quoting the result.
 */
public interface JsonString {
  /**
   * The <code>toJSONString</code> method allows a class to produce its own JSON serialization.
   * 
   * @return A strictly syntactically correct JSON text.
   */
  public String toJsonString();
}
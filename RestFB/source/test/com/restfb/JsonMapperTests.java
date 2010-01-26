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

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit tests that exercise {@link JsonMapper} implementations.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperTests {
  /**
   * Can we handle the empty list?
   */
  @Test
  public void emptyList() throws FacebookJsonMappingException {
    List<Object> objects = createJsonMapper().toJavaList("[]", Object.class);
    Assert.assertTrue(objects.size() == 0);
  }

  /**
   * Can we handle the empty object?
   */
  @Test
  public void emptyObject() throws FacebookJsonMappingException {
    Object object = createJsonMapper().toJavaObject("{}", Object.class);
    Assert.assertTrue(object != null);
  }

  /**
   * Can we handle simple primitive mapping?
   */
  @Test
  public void simplePrimitive() throws FacebookJsonMappingException {
    String tag =
        createJsonMapper().toJavaObject(jsonFromClasspath("tag"), String.class);
    Assert.assertTrue("Good".equals(tag));
  }

  /**
   * Can we handle simple numeric mapping?
   */
  @Test
  public void simplePrimitiveNumber() throws FacebookJsonMappingException {
    Integer number =
        createJsonMapper().toJavaObject(jsonFromClasspath("number"),
          Integer.class);
    Assert.assertTrue(number.equals(1234));
  }

  /**
   * Can we handle simple primitive list mapping?
   */
  @Test
  public void simplePrimitiveList() throws FacebookJsonMappingException {
    List<String> tags =
        createJsonMapper().toJavaList(jsonFromClasspath("tags"), String.class);
    Assert.assertTrue(tags.size() == 3);
    Assert.assertTrue("Good".equals(tags.get(0)));
    Assert.assertTrue("Better".equals(tags.get(1)));
    Assert.assertTrue("Best".equals(tags.get(2)));
  }

  /**
   * Can we handle simple primitive numeric list mapping?
   */
  @Test
  public void simplePrimitiveNumericList() throws FacebookJsonMappingException {
    List<Integer> numbers =
        createJsonMapper().toJavaList(jsonFromClasspath("numbers"),
          Integer.class);
    Assert.assertTrue(numbers.size() == 3);
    Assert.assertTrue(numbers.get(0).equals(1234));
    Assert.assertTrue(numbers.get(1).equals(5678));
    Assert.assertTrue(numbers.get(2).equals(9012));
  }

  /**
   * Can we handle simple object mapping?
   */
  @Test
  public void simpleObject() throws FacebookJsonMappingException {
    BasicUser basicUser =
        createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"),
          BasicUser.class);
    Assert.assertTrue(basicUser.uid.equals(1234L));
    Assert.assertTrue("Test Person".equals(basicUser.name));
  }

  /**
   * Can we handle simple list mapping?
   */
  @Test
  public void simpleObjectWithList() throws FacebookJsonMappingException {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"),
          UserWithPhotos.class);
    Assert.assertTrue(userWithPhotos.photos.size() == 2);
  }

  /**
   * Do we properly find Facebook-annotated fields defined in a superclass?
   */
  @Test
  public void fieldsFromSuperclass() throws FacebookJsonMappingException {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"),
          UserWithPhotos.class);
    Assert.assertTrue(userWithPhotos.uid.equals(1234L));
    Assert.assertTrue("Test Person".equals(userWithPhotos.name));
    Assert.assertTrue(userWithPhotos.photos.size() == 2);
  }

  /**
   * Can we map to Facebook-annotated fields even when they're marked private?
   */
  @Test
  public void privateFields() throws FacebookJsonMappingException {
    PrivateUser privateUser =
        createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"),
          PrivateUser.class);
    Assert.assertTrue(privateUser.getUid().equals(1234L));
  }

  static class BasicUser {
    @Facebook(value = "uid")
    Long uid;

    @Facebook(value = "name")
    String name;
  }

  static class Photo {
    @Facebook(value = "id")
    Long id;
  }

  static class UserWithPhotos extends BasicUser {
    @Facebook(value = "photos", contains = Photo.class)
    List<Photo> photos;
  }

  private JsonMapper createJsonMapper() {
    return new DefaultJsonMapper();
  }

  private String jsonFromClasspath(String pathToJson) {
    try {
      return StringUtils.fromInputStream(ClasspathWebRequestor.class
        .getResourceAsStream("/json/" + pathToJson + ".json"));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load JSON from the classpath",
        e);
    }
  }
}
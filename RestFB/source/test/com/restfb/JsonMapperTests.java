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
    Assert.assertTrue(userWithPhotos.photos.get(0).photoId.equals(1L));
    Assert.assertTrue(userWithPhotos.photos.get(1).photoId.equals(2L));
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

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  public void authCreateToken() throws FacebookJsonMappingException {
    String token =
        createJsonMapper().toJavaObject(
          jsonFromClasspath("api/auth.createToken"), String.class);
    Assert.assertTrue("3e4a22bb2f5ed75114b0fc9995ea85f1".equals(token));
  }

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  public void usersGetLoggedInUser() throws FacebookJsonMappingException {
    Long uid =
        createJsonMapper().toJavaObject(
          jsonFromClasspath("api/users.getLoggedInUser"), Long.class);
    Assert.assertTrue(uid.equals(1240077L));
  }

  /**
   * Can we successfully map the results of the friends.get call?
   */
  @Test
  public void friendsGet() throws FacebookJsonMappingException {
    List<Long> friendUids =
        createJsonMapper().toJavaList(jsonFromClasspath("api/friends.get"),
          Long.class);
    Assert.assertTrue(friendUids.size() == 2);
    Assert.assertTrue(friendUids.get(0).equals(222333L));
    Assert.assertTrue(friendUids.get(1).equals(1240079L));
  }

  static class BasicUser {
    @Facebook
    Long uid;

    @Facebook("name")
    String name;
  }

  static class Photo {
    @Facebook("id")
    Long photoId;
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
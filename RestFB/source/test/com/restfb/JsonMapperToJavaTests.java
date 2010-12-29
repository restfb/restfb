/*
 * Copyright (c) 2010-2011 Mark Allen.
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

import static junit.framework.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * Unit tests that exercise {@link JsonMapper} implementations, specifically the
 * "convert JSON to Java" functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperToJavaTests extends AbstractJsonMapperTests {
  /**
   * Can we handle the empty list?
   */
  @Test
  public void emptyList() throws FacebookJsonMappingException {
    List<Object> objects = createJsonMapper().toJavaList("[]", Object.class);
    assertTrue(objects.size() == 0);
  }

  /**
   * Can we handle the empty object?
   */
  @Test
  public void emptyObject() throws FacebookJsonMappingException {
    Object object = createJsonMapper().toJavaObject("{}", Object.class);
    assertTrue(object != null);
  }

  /**
   * Can we handle simple primitive mapping?
   */
  @Test
  public void simplePrimitive() throws FacebookJsonMappingException {
    String tag = createJsonMapper().toJavaObject(jsonFromClasspath("tag"), String.class);
    assertTrue("Good".equals(tag));
  }

  /**
   * Can we handle simple numeric mapping?
   */
  @Test
  public void simplePrimitiveNumber() throws FacebookJsonMappingException {
    Integer number = createJsonMapper().toJavaObject(jsonFromClasspath("number"), Integer.class);
    assertTrue(number.equals(1234));
  }

  /**
   * Can we handle simple primitive list mapping?
   */
  @Test
  public void simplePrimitiveList() throws FacebookJsonMappingException {
    List<String> tags = createJsonMapper().toJavaList(jsonFromClasspath("tags"), String.class);
    assertTrue(tags.size() == 3);
    assertTrue("Good".equals(tags.get(0)));
    assertTrue("Better".equals(tags.get(1)));
    assertTrue("Best".equals(tags.get(2)));
  }

  /**
   * Can we handle simple primitive numeric list mapping?
   */
  @Test
  public void simplePrimitiveNumericList() throws FacebookJsonMappingException {
    List<Integer> numbers = createJsonMapper().toJavaList(jsonFromClasspath("numbers"), Integer.class);
    assertTrue(numbers.size() == 3);
    assertTrue(numbers.get(0).equals(1234));
    assertTrue(numbers.get(1).equals(5678));
    assertTrue(numbers.get(2).equals(9012));
  }

  /**
   * Can we handle simple object mapping?
   */
  @Test
  public void simpleObject() throws FacebookJsonMappingException {
    BasicUser basicUser = createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"), BasicUser.class);
    assertTrue(basicUser.uid.equals(1234L));
    assertTrue("Test Person".equals(basicUser.name));
  }

  /**
   * Can we handle simple list mapping?
   */
  @Test
  public void simpleObjectWithList() throws FacebookJsonMappingException {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"), UserWithPhotos.class);
    assertTrue(userWithPhotos.photos.size() == 2);
    assertTrue(userWithPhotos.photos.get(0).photoId.equals(1L));
    assertTrue(userWithPhotos.photos.get(1).photoId.equals(2L));
  }

  /**
   * Do we properly find Facebook-annotated fields defined in a superclass?
   */
  @Test
  public void fieldsFromSuperclass() throws FacebookJsonMappingException {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"), UserWithPhotos.class);
    assertTrue(userWithPhotos.uid.equals(1234L));
    assertTrue("Test Person".equals(userWithPhotos.name));
    assertTrue(userWithPhotos.photos.size() == 2);
  }

  /**
   * Can we map to Facebook-annotated fields even when they're marked private?
   */
  @Test
  public void privateFields() throws FacebookJsonMappingException {
    PrivateUser privateUser = createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"), PrivateUser.class);
    assertTrue(privateUser.getUid().equals(1234L));
  }

  /**
   * Can we handle slightly-more-complex mapping, including the workaround for
   * Facebook's "we gave you an empty object instead of an empty list" bug?
   */
  @Test
  public void usersWithAffiliations() throws FacebookJsonMappingException {
    List<UserWithAffiliations> usersWithAffiliations =
        createJsonMapper().toJavaList(jsonFromClasspath("users-with-affiliations"), UserWithAffiliations.class);

    assertTrue(usersWithAffiliations.size() == 3);
    Assert.assertTrue("Heather Merlin".equals(usersWithAffiliations.get(0).name));
    Assert.assertTrue(("https://secure-profile.facebook.com/profile6/" + "13580/1406/n284asf55_7662.jpg")
      .equals(usersWithAffiliations.get(0).bigPictureUrl));
    assertTrue(usersWithAffiliations.get(0).affiliations.size() == 1);
    assertTrue("Intuit".equals(usersWithAffiliations.get(0).affiliations.get(0).name));

    // Make sure the weird Facebook "empty object means empty list" workaround
    // works
    assertTrue(usersWithAffiliations.get(2).affiliations.size() == 0);
  }

  /**
   * Can we handle nulls nicely?
   */
  @Test
  public void nulls() throws FacebookJsonMappingException {
    UserWithAffiliations userWithAffiliations =
        createJsonMapper().toJavaObject(jsonFromClasspath("nulls"), UserWithAffiliations.class);

    assertTrue(userWithAffiliations != null);
    assertTrue(userWithAffiliations.uid != null);
    assertTrue(userWithAffiliations.name == null);
    assertTrue(userWithAffiliations.bigPictureUrl == null);
    assertTrue(userWithAffiliations.affiliations == null);
  }

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  public void authCreateToken() throws FacebookJsonMappingException {
    String token = createJsonMapper().toJavaObject(jsonFromClasspath("api/auth.createToken"), String.class);
    assertTrue("3e4a22bb2f5ed75114b0fc9995ea85f1".equals(token));
  }

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  public void usersGetLoggedInUser() throws FacebookJsonMappingException {
    Long uid = createJsonMapper().toJavaObject(jsonFromClasspath("api/users.getLoggedInUser"), Long.class);
    assertTrue(uid.equals(1240077L));
  }

  /**
   * Can we successfully map the results of the friends.get call?
   */
  @Test
  public void friendsGet() throws FacebookJsonMappingException {
    List<Long> friendUids = createJsonMapper().toJavaList(jsonFromClasspath("api/friends.get"), Long.class);
    assertTrue(friendUids.size() == 2);
    assertTrue(friendUids.get(0).equals(222333L));
    assertTrue(friendUids.get(1).equals(1240079L));
  }

  /**
   * Can we successfully map the case where Facebook sends us an empty array
   * instead of an empty string?
   */
  @Test
  public void emptyArray() throws FacebookJsonMappingException {
    BasicUser user = createJsonMapper().toJavaObject(jsonFromClasspath("empty-array-as-string"), BasicUser.class);
    assertTrue("".equals(user.name));
  }

  /**
   * Workaround where Facebook can return the illegal JSON "false" instead of an
   * object - just map as null instead of throwing an exception.
   */
  @Test
  public void testFalseInsteadOfObject() throws FacebookJsonMappingException {
    User user = createJsonMapper().toJavaObject("false", User.class);
    assertTrue(user == null);
  }

  @Test
  public void testMultipleFieldsWithSameName() {
    JsonMapper jsonMapper = createJsonMapper();

    User user1 = jsonMapper.toJavaObject(jsonFromClasspath("user-with-hometown-v1"), User.class);
    assertTrue("Beograd".equals(user1.getHometownAsString()));

    User user2 = jsonMapper.toJavaObject(jsonFromClasspath("user-with-hometown-v2"), User.class);
    assertTrue("Belgrade, Serbia".equals(user2.getHometown().getName()));
    assertTrue("Belgrade, Serbia".equals(user2.getHometownAsString()));

    Post post1 = jsonMapper.toJavaObject(jsonFromClasspath("post-with-likes-v1"), Post.class);
    assertTrue(post1.getLikesAsNumber() == 4);

    Post post2 = jsonMapper.toJavaObject(jsonFromClasspath("post-with-likes-v2"), Post.class);
    assertTrue(post2.getLikes().getCount() == 49);
    assertTrue(post2.getLikesAsNumber() == 49);
  }

  /**
   * Makes sure we handle "null" when inside of a list instead of throwing a
   * mapping exception.
   */
  public void testNulls() throws FacebookJsonMappingException {
    List<NamedFacebookType> types =
        createJsonMapper().toJavaList(jsonFromClasspath("nulls-in-list"), NamedFacebookType.class);
    assertTrue(types.size() == 3);
  }

  static class BasicUser {
    @Facebook
    Long uid;

    @Facebook
    String name;
  }

  static class Photo {
    @Facebook("id")
    Long photoId;
  }

  static class UserWithPhotos extends BasicUser {
    @Facebook
    List<Photo> photos;
  }

  static class Affiliation {
    @Facebook
    String name;

    @Facebook
    String type;
  }

  static class UserWithAffiliations extends BasicUser {
    @Facebook("pic_big")
    String bigPictureUrl;

    @Facebook
    List<Affiliation> affiliations;
  }
}
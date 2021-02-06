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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.testutils.AssertJson;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Unit tests that exercise {@link JsonMapper} implementations, specifically the "convert Java to JSON" functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperToJsonTest extends AbstractJsonMapperTests {
  /**
   * Can we handle null?
   */
  @Test
  public void nullObject() {
    String json = createJsonMapper().toJson(null);
    assertThat(json).isEqualTo("null");
  }

  /**
   * Can we handle the empty list?
   */
  @Test
  public void emptyList() {
    String json = createJsonMapper().toJson(new ArrayList<>());
    assertThat(json).isEqualTo("[]");
  }

  @Test
  public void emptyFacebookList() {
    ListObject obj = new ListObject();
    String json = createJsonMapper().toJson(obj, true);
    assertThat(json).isEqualTo("{\"id\":12345}");
  }

  @Test
  public void emptyFacebookMap() {
    MapObject obj = new MapObject();
    String json = createJsonMapper().toJson(obj, true);
    assertThat(json).isEqualTo("{\"id\":12345}");
  }

  /**
   * Can we handle the empty object?
   */
  @Test
  public void emptyObject() {
    String json = createJsonMapper().toJson(new Object());
    assertThat(json).isEqualTo("{}");
  }

  /**
   * Can we handle primitives?
   */
  @Test
  public void primitives() {
    // Close your eyes and pretend that string is a primitive here
    assertThat(createJsonMapper().toJson("Testing")).isEqualTo("Testing");
    assertThat(createJsonMapper().toJson(true)).isEqualTo("true");
    assertThat(createJsonMapper().toJson(1)).isEqualTo("1");
    assertThat(createJsonMapper().toJson(1L)).isEqualTo("1");
    assertThat(createJsonMapper().toJson(1F)).isEqualTo("1");
    assertThat(createJsonMapper().toJson(1.1F)).isEqualTo("1.1");
    assertThat(createJsonMapper().toJson(1D)).isEqualTo("1");
    assertThat(createJsonMapper().toJson(1.1D)).isEqualTo("1.1");
    assertThat(createJsonMapper().toJson('1')).isEqualTo("1");
    assertThat(createJsonMapper().toJson(new Byte("1"))).isEqualTo("1");
    assertThat(createJsonMapper().toJson(new Short("1"))).isEqualTo("1");
    assertThat(createJsonMapper().toJson(new BigInteger("1"))).isEqualTo("1");
    assertThat(createJsonMapper().toJson(new BigDecimal("1"))).isEqualTo("1");
    assertThat(createJsonMapper().toJson(new BigDecimal("1.1"))).isEqualTo("1.1");
  }

  /**
   * Can we handle a basic Javabean?
   */
  @Test
  public void basicJavabean() {
    BasicUser basicUser = new BasicUser();
    basicUser.uid = 12345L;
    basicUser.name = "Fred";
    String json = createJsonMapper().toJson(basicUser);
    String expectedJson = "{\"uid\":12345,\"name\":\"Fred\"}";
    AssertJson.assertEquals(expectedJson, json);
  }

  /**
   * Can we handle a more complex Javabean?
   */
  @Test
  public void complexJavabean() {
    UserWithPhotos userWithPhotos = new UserWithPhotos();
    userWithPhotos.uid = 12345L;
    userWithPhotos.name = null;
    userWithPhotos.photos = new ArrayList<>();
    userWithPhotos.photos.add(new Photo());
    Photo photo = new Photo();
    photo.photoId = 5678L;
    photo.location = "Las Vegas";
    userWithPhotos.photos.add(photo);

    String json = createJsonMapper().toJson(userWithPhotos);
    String expectedJson =
        "{\"uid\":12345,\"photos\":[{\"id\":null,\"location\":null},{\"id\":5678,\"location\":\"Las Vegas\"}],\"name\":null}";
    AssertJson.assertEquals(expectedJson, json);
  }

  /**
   * Can we handle a full stream.publish example?
   * <p>
   * See http://wiki.developers.facebook.com/index.php/Attachment_(Streams).
   */
  @Test
  public void streamPublish() {
    ActionLink category = new ActionLink();
    category.href = "http://bit.ly/KYbaN";
    category.text = "humor";

    Properties properties = new Properties();
    properties.category = category;
    properties.ratings = "5 stars";

    Medium medium = new Medium();
    medium.href = "http://bit.ly/187gO1";
    medium.src =
        "http://icanhascheezburger.files.wordpress.com/2009/03/funny-pictures-your-cat-is-bursting-with-joy1.jpg";
    medium.type = "image";

    List<Medium> media = new ArrayList<>();
    media.add(medium);

    Attachment attachment = new Attachment();
    attachment.name = "i'm bursting with joy";
    attachment.href = "http://bit.ly/187gO1";
    attachment.caption = "{*actor*} rated the lolcat 5 stars";
    attachment.description = "a funny looking cat";
    attachment.properties = properties;
    attachment.media = media;

    String json = createJsonMapper().toJson(attachment);
    String expectedJson =
        "{\"description\":\"a funny looking cat\",\"name\":\"i'm bursting with joy\",\"caption\":\"{*actor*} rated the lolcat 5 stars\",\"properties\":{\"category\":{\"text\":\"humor\",\"href\":\"http://bit.ly/KYbaN\"},\"ratings\":\"5 stars\"},\"media\":[{\"src\":\"http://icanhascheezburger.files.wordpress.com/2009/03/funny-pictures-your-cat-is-bursting-with-joy1.jpg\",\"type\":\"image\",\"href\":\"http://bit.ly/187gO1\"}],\"href\":\"http://bit.ly/187gO1\"}";
    AssertJson.assertEquals(expectedJson, json);
  }

  @Test
  public void specialTypes() {
    JsonMapper jsonMapper = createJsonMapper();
    SpecialJavaTypes special = new SpecialJavaTypes();
    special.decimal = new BigDecimal("1234567.4");
    special.integer = new BigInteger("1234567");

    String string = jsonMapper.toJson(special);
    String expectedJson = "{\"integer\":1234567, \"decimal\":1234567.4}";

    AssertJson.assertEquals(expectedJson, string);
  }

  /**
   * Can we handle an empty Map?
   */
  @Test
  public void emptyMap() {
    assertThat(createJsonMapper().toJson(new HashMap<String, Object>())).isEqualTo("{}");
  }

  /**
   * Can we handle a Map?
   */
  @Test
  public void map() {
    UserWithPhotos basicUser = new UserWithPhotos();
    basicUser.uid = 12345L;
    basicUser.name = "Fred";

    Map<String, Object> map = new HashMap<>();
    map.put("testId", new BigInteger("412"));
    map.put("floatId", 123.45F);
    map.put("basicUser", basicUser);

    String json = createJsonMapper().toJson(map);
    String expectedJson =
        "{\"floatId\":123.45,\"testId\":412,\"basicUser\":{\"uid\":12345,\"photos\":null,\"name\":\"Fred\"}}";
    AssertJson.assertEquals(expectedJson, json);
  }

  @Test
  public void mapWithNoStringKey() {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(1, 3);
    map.put(2, 1);
    try {
      String json = createJsonMapper().toJson(map);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException fe) {
      assertThat(fe.getMessage()).startsWith("Your Map keys must be of type class java.lang.String");
    }
  }

  @Test
  public void mapWithInfiniteFloatValueKey() {
    Map<String, Float> map = new HashMap<>();
    map.put("test", Float.POSITIVE_INFINITY);
    try {
      String json = createJsonMapper().toJson(map);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException fe) {
      assertThat(fe.getMessage()).startsWith("Unable to process value");
    }
  }

  @Test
  public void duplicateAnnotations() {
    DuplicateAnnotationsUser user = new DuplicateAnnotationsUser();
    user.hometown = "Philadelphia, PA";
    user.hometownObject = new HashMap<String, Object>() {
      {
        put("id", "123");
        put("name", "Philly");
      }
    };

    String json = createJsonMapper().toJson(user);

    // The current behavior is that it's luck of the draw which field is
    // selected to marshal to JSON if there are multiple fields with the same
    // mapping
    assertThat(json).contains("Philly", "Philadelphia");
  }

  @Test
  public void convertDate() {
    Date now = new Date(1474581731000L);
    String myDate = createJsonMapper().toJson(now);
    assertThat(myDate).isEqualTo("2016-09-22T22:02:11");
  }

  static class ListObject {

    @Facebook
    Long id = 12345L;

    @Facebook
    List<Integer> list = new ArrayList<>();

  }

  static class MapObject {

    @Facebook
    Long id = 12345L;

    @Facebook
    Map<Integer, Integer> list = new HashMap<>();

  }

  static class BasicUser {
    @Facebook
    Long uid;

    @Facebook
    String name;

    Byte ignored;
  }

  static class Photo {
    @Facebook("id")
    Long photoId;

    @Facebook
    String location;
  }

  static class DuplicateAnnotationsUser {
    @Facebook
    String hometown;

    @Facebook("hometown")
    Map<String, Object> hometownObject;
  }

  static class UserWithPhotos extends BasicUser {
    @Facebook
    List<Photo> photos;
  }

  static class ActionLink {
    @Facebook
    String text;

    @Facebook
    String href;
  }

  static class Medium {
    @Facebook
    String type;

    @Facebook
    String src;

    @Facebook
    String href;
  }

  static class Properties {
    @Facebook
    ActionLink category;

    @Facebook
    String ratings;
  }

  static class Attachment {
    @Facebook
    String name;

    @Facebook
    String href;

    @Facebook
    String caption;

    @Facebook
    String description;

    @Facebook
    Properties properties;

    @Facebook
    List<Medium> media;
  }

  static class SpecialJavaTypes {
    @Facebook
    BigDecimal decimal;

    @Facebook
    BigInteger integer;
  }
}
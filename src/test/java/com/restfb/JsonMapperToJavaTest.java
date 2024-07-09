/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.JsonMapperToJavaTest.Story.StoryTag;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.Json;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;
import com.restfb.types.*;

/**
 * Unit tests that exercise {@link JsonMapper} implementations, specifically the "convert JSON to Java" functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
class JsonMapperToJavaTest extends AbstractJsonMapperTests {

  /**
   * check type == null is handled correct
   */
  @Test
  void nullAsList() {
    assertThrows(FacebookJsonMappingException.class, () -> createJsonMapper().toJavaList("[]", null));
  }

  /**
   * Can we handle the empty list?
   */
  @Test
  void emptyList() {
    List<Object> objects = createJsonMapper().toJavaList("[]", Object.class);
    assertThat(objects).isEmpty();
  }

  /**
   * Can we handle the empty object?
   */
  @Test
  void emptyObject() {
    Object object = createJsonMapper().toJavaObject("{}", Object.class);
    assertThat(object).isNotNull();
  }

  /**
   * Can we handle simple primitive mapping?
   */
  @Test
  void simplePrimitive() {
    String tag = createJsonMapper().toJavaObject(jsonFromClasspath("tag"), String.class);
    assertThat(tag).isEqualTo("Good");
  }

  /**
   * Can we handle simple numeric mapping?
   */
  @Test
  void simplePrimitiveNumber() {
    Integer number = createJsonMapper().toJavaObject(jsonFromClasspath("number"), Integer.class);
    assertThat(number).isEqualTo(1234);
  }

  /**
   * Can we handle simple primitive list mapping?
   */
  @Test
  void simplePrimitiveList() {
    List<String> tags = createJsonMapper().toJavaList(jsonFromClasspath("tags"), String.class);
    assertThat(tags).hasSize(3).containsExactly("Good", "Better", "Best");
  }

  /**
   * Can we handle simple primitive numeric list mapping?
   */
  @Test
  void simplePrimitiveNumericList() {
    List<Integer> numbers = createJsonMapper().toJavaList(jsonFromClasspath("numbers"), Integer.class);
    assertThat(numbers).hasSize(3).contains(1234, 5678, 9012);
  }

  /**
   * Can we handle simple object mapping?
   */
  @Test
  void simpleObject() {
    BasicUser basicUser = createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"), BasicUser.class);
    assertThat(basicUser.uid).isEqualTo(1234L);
    assertThat(basicUser.name).isEqualTo("Test Person");
  }

  /**
   * Can we handle simple list mapping?
   */
  @Test
  void simpleObjectWithList() {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"), UserWithPhotos.class);
    assertThat(userWithPhotos.photos).hasSize(2);
    assertThat(userWithPhotos.photos.get(0).photoId).isEqualTo(1L);
    assertThat(userWithPhotos.photos.get(1).photoId).isEqualTo(2L);
  }

  /**
   * Do we properly find Facebook-annotated fields defined in a superclass?
   */
  @Test
  void fieldsFromSuperclass() {
    UserWithPhotos userWithPhotos =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"), UserWithPhotos.class);
    assertThat(userWithPhotos.uid).isEqualTo(1234L);
    assertThat(userWithPhotos.name).isEqualTo("Test Person");
    assertThat(userWithPhotos.photos).hasSize(2);
  }

  /**
   * Can we map to Facebook-annotated fields even when they're marked private?
   */
  @Test
  void privateFields() {
    PrivateUser privateUser = createJsonMapper().toJavaObject(jsonFromClasspath("basic-user"), PrivateUser.class);
    assertThat(privateUser.getUid()).isEqualTo(1234);
  }

  /**
   * Can we handle slightly-more-complex mapping, including the workaround for Facebook's "we gave you an empty object
   * instead of an empty list" bug?
   */
  @Test
  void usersWithAffiliations() {
    List<UserWithAffiliations> usersWithAffiliations =
        createJsonMapper().toJavaList(jsonFromClasspath("users-with-affiliations"), UserWithAffiliations.class);

    assertThat(usersWithAffiliations).hasSize(3);
    assertThat(usersWithAffiliations.get(0).name).isEqualTo("Heather Merlin");
    assertThat(usersWithAffiliations.get(0).bigPictureUrl)
      .isEqualTo("https://secure-profile.facebook.com/profile6/13580/1406/n284asf55_7662.jpg");
    assertThat(usersWithAffiliations.get(0).affiliations).hasSize(1);
    assertThat(usersWithAffiliations.get(0).affiliations.get(0).name).isEqualTo("Intuit");

    // Make sure the weird Facebook "empty object means empty list" workaround
    // works
    assertThat(usersWithAffiliations.get(2).affiliations).isEmpty();
  }

  /**
   * Can we handle nulls nicely?
   */
  @Test
  void nulls() {
    UserWithAffiliations userWithAffiliations =
        createJsonMapper().toJavaObject(jsonFromClasspath("nulls"), UserWithAffiliations.class);

    assertThat(userWithAffiliations).isNotNull();
    assertThat(userWithAffiliations.uid).isNotNull();
    assertThat(userWithAffiliations.name).isNull();
    assertThat(userWithAffiliations.bigPictureUrl).isNull();
    assertThat(userWithAffiliations.affiliations).isNull();
  }

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  void authCreateToken() {
    String token = createJsonMapper().toJavaObject(jsonFromClasspath("api/auth.createToken"), String.class);
    assertThat(token).isEqualTo("3e4a22bb2f5ed75114b0fc9995ea85f1");
  }

  /**
   * Can we successfully map the results of the auth.createToken call?
   */
  @Test
  void usersGetLoggedInUser() {
    Long uid = createJsonMapper().toJavaObject(jsonFromClasspath("api/users.getLoggedInUser"), Long.class);
    assertThat(uid).isEqualTo(1240077L);
  }

  /**
   * Can we successfully map the results of the friends.get call?
   */
  @Test
  void friendsGet() {
    List<Long> friendUids = createJsonMapper().toJavaList(jsonFromClasspath("api/friends.get"), Long.class);
    assertThat(friendUids).hasSize(2).containsExactly(222333L, 1240079L);
  }

  @Test
  void unicodeGet() {
    User user = createJsonMapper().toJavaObject(jsonFromClasspath("api/unicode"), User.class);
    assertThat(user).isNotNull();

    User user2 = new DefaultFacebookClient("blub", new DefaultWebRequestor() {
      @Override
      public Response executeGet(Request request) {
        return new Response(HttpURLConnection.HTTP_OK, "{\"id\":\"b4TUmsLXoK\",\"name\":\"ⲉ鯨ɯ摓㻦恛҉祐\\f፲\"}");
      }
    }, new DefaultJsonMapper(), Version.LATEST).fetchObject("me", User.class);
    assertThat(user2).isNotNull();
  }

  /**
   * test reading in a sample page conversation
   */
  @Test
  void conversation() {
    Conversation conversation = createJsonMapper().toJavaObject(jsonFromClasspath("conversation"), Conversation.class);
    assertThat(conversation.getId()).isEqualTo("t_id.378684585488220");

    List<Message> messages = conversation.getMessages();
    assertThat(messages).hasSize(2);

    List<Message.Attachment> attachments = messages.get(0).getAttachments();
    assertThat(attachments).hasSize(2);
    assertThat(attachments.get(0).getImageData()).isNull();
    assertThat(attachments.get(1).getImageData()).isNotNull();
  }

  /**
   * Can we successfully map the case where Facebook sends us an empty array instead of an empty string?
   */
  @Test
  void emptyArray() {
    BasicUser user = createJsonMapper().toJavaObject(jsonFromClasspath("empty-array-as-string"), BasicUser.class);
    assertThat(user.name).isEmpty();
  }

  /**
   * test reading in the sample account from
   * <a href="https://developers.facebook.com/docs/facebook-login/access-tokens/#pagetokens">here</a>
   */
  @Test
  void accountTest() {
    Account account = createJsonMapper().toJavaObject(jsonFromClasspath("account"), Account.class);
    assertThat(account.getCategory()).isEqualTo("Product/service");
    assertThat(account.getAccessToken()).isEqualTo("{access-token}");
    assertThat(account.getId()).isEqualTo("1234567890");
    assertThat(account.getName()).isEqualTo("Sample Page");
    assertThat(account.getTasks()).hasSize(6).contains("ANALYZE", "ADVERTISE", "MESSAGING", "MODERATE",
      "CREATE_CONTENT", "MANAGE");
  }

  /**
   * Workaround where Facebook can return the illegal JSON "false" instead of an object - just map as null instead of
   * throwing an exception.
   */
  @Test
  void testFalseInsteadOfObject() {
    User user = createJsonMapper().toJavaObject("false", User.class);
    assertThat(user).isNull();
  }

  @Test
  void testMultipleFieldsWithSameName() {
    JsonMapper jsonMapper = createJsonMapper();

    User user1 = jsonMapper.toJavaObject(jsonFromClasspath("user-with-hometown-v1"), User.class);
    assertThat(user1.getHometownName()).isEqualTo("Beograd");
    assertThat(user1.getHometown()).isNull();

    User user2 = jsonMapper.toJavaObject(jsonFromClasspath("user-with-hometown-v2"), User.class);
    assertThat(user2.getHometown().getName()).isEqualTo("Belgrade, Serbia");
    assertThat(user2.getHometownName()).isEqualTo("Belgrade, Serbia");

    Post post1 = jsonMapper.toJavaObject(jsonFromClasspath("post-with-likes-v1"), Post.class);
    assertThat(post1.getLikesCount()).isEqualTo(4);
    assertThat(post1.getLikes()).isNull();

    Post post2 = jsonMapper.toJavaObject(jsonFromClasspath("post-with-likes-v2"), Post.class);
    assertThat(post2.getLikes().getTotalCount()).isEqualTo(49);
    assertThat(post2.getLikesCount()).isEqualTo(49);
  }

  /**
   * Makes sure we handle "null" when inside of a list instead of throwing a mapping exception.
   */
  @Test
  void testNulls() {
    List<NamedFacebookType> types =
        createJsonMapper().toJavaList(jsonFromClasspath("nulls-in-list"), NamedFacebookType.class);
    assertThat(types).hasSize(3);
  }

  /**
   * Can we handle the JsonObject types?
   */
  @Test
  void story() {
    Set<String> actualStoryTagIds = new HashSet<>();
    Set<String> expectedStoryTagIds = Stream.of("123","456").collect(Collectors.toSet());

    JsonMapper jsonMapper = createJsonMapper();
    Story story = jsonMapper.toJavaObject(jsonFromClasspath("story"), Story.class);

    for (String fieldName : story.storyTags.names()) {
      List<StoryTag> storyTags = jsonMapper.toJavaList(story.storyTags.get(fieldName).toString(), StoryTag.class);
      storyTags.stream().map(tag -> tag.id).forEach(actualStoryTagIds::add);
    }

    assertThat(actualStoryTagIds).containsExactlyElementsOf(expectedStoryTagIds);
  }

  @Test
  void specialTypes() {
    JsonMapper jsonMapper = createJsonMapper();
    SpecialJavaTypes object =
        jsonMapper.toJavaObject("{\"integer\":\"1234567\", \"decimal\":\"1234567.4\"}", SpecialJavaTypes.class);
    assertThat(object.decimal).isNotNull();
    assertThat(object.integer).isNotNull();

    assertThat(object.integer.intValue()).isEqualTo(1234567);
    assertThat(object.decimal.intValue()).isEqualTo(1234567);
    assertThat(object.decimal.floatValue()).isEqualTo(1234567.4f);
  }

  @Test
  void dateType() {
    JsonMapper jsonMapper = createJsonMapper();
    Post postObj = jsonMapper.toJavaObject("{\"created_time\": \"2014-08-26T20:55:07+0000\"}", Post.class);
    assertThat(postObj.getCreatedTime()).hasTime(1409086507000L);
  }

  @Test
  void jsonMappingCompleted() {
    JsonMapper jsonMapper = createJsonMapper();

    BasicJsonMappingCompletedClass basicJsonMappingCompletedClass =
        jsonMapper.toJavaObject("{}", BasicJsonMappingCompletedClass.class);
    assertThat(basicJsonMappingCompletedClass.hasMapper).isTrue();
    assertThat(basicJsonMappingCompletedClass.blankSignatureWorks).isTrue();

    ExtendedJsonMappingCompletedClass extendedJsonMappingCompletedClass =
        jsonMapper.toJavaObject("{}", ExtendedJsonMappingCompletedClass.class);
    assertThat(extendedJsonMappingCompletedClass.hasMapper).isTrue();
    assertThat(extendedJsonMappingCompletedClass.blankSignatureWorks).isTrue();
    assertThat(extendedJsonMappingCompletedClass.subclassWorksToo).isTrue();

    try {
      jsonMapper.toJavaObject("{}", IllegalJsonMappingCompletedClass.class);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException e) {
      // Expected
    }
  }

  @Test
  void jsonEmptyArray() {
    User u = createJsonMapper().toJavaObject("[]", User.class);
    assertThat(u).isNotNull();
    assertThat(u.getName()).isNull();
  }

  /**
   * check if a empty string is not mapped and a exception with the correct message is thrown instead
   */
  @Test
  void emptyStringAsList() {
    try {
      createJsonMapper().toJavaList("", HashMap.class);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException fe) {
      assertThat(fe.getMessage()).startsWith("JSON is an empty string");
    }
  }

  /**
   * check if a empty string is not mapped and a exception with the correct message is thrown instead
   */
  @Test
  void emptyStringAsObject() {
    try {
      createJsonMapper().toJavaObject("", String.class);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException fe) {
      assertThat(fe.getMessage()).startsWith("JSON is an empty string");
    }
  }

  @Test
  void mapArrayAsObject() {
    try {
      createJsonMapper().toJavaObject("[{\"test\":123}]", String.class);
      failBecauseExceptionWasNotThrown(FacebookJsonMappingException.class);
    } catch (FacebookJsonMappingException fe) {
      assertThat(fe.getMessage()).startsWith("JSON is an array but is being mapped as an object");
    }
  }

  @Test
  void primitives() {
    Integer integerObject = createJsonMapper().toJavaObject("12345", Integer.class);
    assertThat(integerObject.intValue()).isEqualTo(12345);
    Long longObject = createJsonMapper().toJavaObject("12345", Long.class);
    assertThat(longObject.intValue()).isEqualTo(12345);
    Double doubleObject = createJsonMapper().toJavaObject("12345.321", Double.class);
    assertThat(doubleObject.doubleValue()).isEqualTo(12345.321d);
    Float floatObject = createJsonMapper().toJavaObject("12345.321", Float.class);
    assertThat(floatObject.floatValue()).isEqualTo(12345.321f);
    BigInteger bigIntObject = createJsonMapper().toJavaObject("12345", BigInteger.class);
    assertThat(bigIntObject.intValue()).isEqualTo(12345);
    BigDecimal bigDecObject = createJsonMapper().toJavaObject("12345.321", BigDecimal.class);
    assertThat(bigDecObject.doubleValue()).isEqualTo(12345.321d);
    Boolean boolObject1 = createJsonMapper().toJavaObject("true", Boolean.class);
    assertThat(boolObject1.booleanValue()).isTrue();
    Boolean boolObject2 = createJsonMapper().toJavaObject("false", Boolean.class);
    assertThat(boolObject2.booleanValue()).isFalse();
    String StringObject1 = createJsonMapper().toJavaObject("test", String.class);
    assertThat(StringObject1).isEqualTo("test");
    String StringObject2 = createJsonMapper().toJavaObject("\"test\"", String.class);
    assertThat(StringObject2).isEqualTo("test");
  }

  @Test
  void jsonObjectToMap() {
    String jsonString = "{ \"my_map\": { \"key1\": 1, \"key2\": 2}}";
    MapTestType object = createJsonMapper().toJavaObject(jsonString, MapTestType.class);
    assertThat(object).isNotNull();
    assertThat(object.myMap).isNotNull().containsEntry("key1", 1L).containsEntry("key2", 2L);
  }

  @Test
  void jsonObjectToBadMap() {
    String jsonString = "{ \"my_map\": { \"key1\": 1, \"key2\": 2}}";
    assertThrows(FacebookJsonMappingException.class, () -> {
      createJsonMapper().toJavaObject(jsonString, BadMapTestType.class);
    });
  }

  @Test
  void jsonListOfListOfString() {
    String jsonString = "{\"body_text\":[[\"body-example-1\",\"body-example-2\",\"body-example-3\"]]}";
    JsonMapper mapper = new DefaultJsonMapper();
    ListListString example = mapper.toJavaObject(jsonString, ListListString.class);
    assertThat(example).isNotNull();
  }

  @Test
  void jsonToObjectWithOptionals() {
    String jsonString = "{ \"text\": \"justsometext\", \"number\": 123456, \"emptyNumberButNull\":null}";
    JavaTypeWithOptionalField obj = createJsonMapper().toJavaObject(jsonString, JavaTypeWithOptionalField.class);
    assertThat(obj.number).isInstanceOf(Optional.class).hasValue(123456);
    assertThat(obj.text).isInstanceOf(Optional.class).hasValue("justsometext");
    assertThat(obj.emptyNumber).isInstanceOf(Optional.class).isEmpty();
    assertThat(obj.emptyNumberButNull).isInstanceOf(Optional.class).isEmpty();
  }

  static class ListListString {

    List<String> bodyText = new ArrayList<>();

    @Facebook("body_text")
    String rawBodyText;

    @JsonMappingCompleted
    void onAfterMappingCompleted() {
      JsonArray array = Json.parse(rawBodyText).asArray().get(0).asArray();
      array.valueStream().map(JsonValue::asString).forEach(bodyText::add);
    }
  }

  static class MapTestType {
    @Facebook("my_map")
    Map<String, Long> myMap;
  }

  static class BadMapTestType {
    @Facebook("my_map")
    Map<Integer, Long> myMap;
  }

  static class Story {
    @Facebook
    String story;

    @Facebook("story_tags")
    JsonObject storyTags;

    static class StoryTag {
      @Facebook
      String id;

      @Facebook
      String name;

      @Facebook
      Integer offset;

      @Facebook
      Integer length;
    }
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

  static class JavaTypeWithOptionalField {

    @Facebook
    Optional<String> text;

    @Facebook
    Optional<Integer> number;

    @Facebook
    Optional<Long> emptyNumber;

    @Facebook
    Optional<Float> emptyNumberButNull;
  }

  static class SpecialJavaTypes {
    @Facebook
    BigDecimal decimal;

    @Facebook
    BigInteger integer;
  }

  static class BasicJsonMappingCompletedClass {
    boolean hasMapper = false;
    boolean blankSignatureWorks = false;

    @JsonMappingCompleted
    protected void jsonMappingCompleted(JsonMapper jsonMapper) {
      hasMapper = jsonMapper != null;
    }

    @JsonMappingCompleted
    protected void jsonMappingCompleted() {
      blankSignatureWorks = true;
    }
  }

  static class ExtendedJsonMappingCompletedClass extends BasicJsonMappingCompletedClass {
    boolean subclassWorksToo = false;

    @JsonMappingCompleted
    private void subclass() {
      subclassWorksToo = true;
    }
  }

  static class IllegalJsonMappingCompletedClass {
    @JsonMappingCompleted
    protected void jsonMappingCompleted(JsonMapper jsonMapper, String someOtherParameter) {
      // Should never get here, illegal signature
    }
  }
}
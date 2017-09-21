/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook;

import static com.restfb.logging.RestFBLogger.VALUE_FACTORY_LOGGER;

import com.restfb.JsonMapper;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

/**
 * Factory to convert the value field of the change into a class with special fields
 */
public class ChangeValueFactory {

  private String field;

  private String value;

  public ChangeValueFactory setField(String field) {
    this.field = field;
    return this;
  }

  public ChangeValueFactory setValue(String value) {
    this.value = value;

    return this;
  }

  public ChangeValue buildWithMapper(JsonMapper mapper) {

    if (value == null) {
      return null;
    }

    if (value.startsWith("{")) {

      JsonObject valueObj = new JsonObject(value);

      String classDefinition;
      if (valueObj != null && field != null) {
        classDefinition = field.toUpperCase();
        if (valueObj.has("item")) {
          classDefinition += "_" + valueObj.getString("item").toUpperCase();
        }
        if (valueObj.has("verb")) {
          classDefinition += "_" + valueObj.getString("verb").toUpperCase();
        }

        try {
          ChangeValueEnumeration changeValueEnum = ChangeValueEnumeration.valueOf(classDefinition);
          return mapper.toJavaObject(value, changeValueEnum.getValueClass());
        } catch (IllegalArgumentException iae) {
          VALUE_FACTORY_LOGGER.warn("undefined change value detected: " + classDefinition);
          VALUE_FACTORY_LOGGER.warn("please provide this information to the restfb team: " + value);
          return new FallBackChangeValue(valueObj);
        }
      }
    }

    if (value.startsWith("[")) {
      return new ListJsonChangeValue(new JsonArray(value));
    }

    return new SimpleStringChangeValue(value);

  }

  enum ChangeValueEnumeration {
    FEED_ALBUM_ADD(FeedAlbumAddValue.class), //
    FEED_ALBUM_EDITED(FeedAlbumEditedValue.class), //
    FEED_COMMENT_ADD(FeedCommentValue.class), //
    FEED_COMMENT_EDITED(FeedCommentValue.class), //
    FEED_COMMENT_REMOVE(FeedCommentValue.class), //
    FEED_COMMENT_HIDE(FeedCommentValue.class), //
    FEED_COMMENT_UNHIDE(FeedCommentValue.class), //
    FEED_EVENT_ADD(FeedEventValue.class), //
    FEED_PHOTO_ADD(FeedPhotoAddValue.class), //
    FEED_PHOTO_REMOVE(FeedPhotoRemoveValue.class), //
    FEED_PHOTO_EDITED(FeedPhotoAddValue.class), //
    FEED_PHOTO_HIDE(FeedPhotoAddValue.class), //
    FEED_PHOTO_UNHIDE(FeedPhotoAddValue.class), //
    FEED_VIDEO_ADD(FeedVideoValue.class), //
    FEED_VIDEO_EDITED(FeedVideoValue.class), //
    FEED_VIDEO_REMOVE(FeedVideoRemoveValue.class), //
    FEED_STATUS_ADD(FeedStatusValue.class), //
    FEED_STATUS_EDITED(FeedStatusValue.class), //
    FEED_STATUS_HIDE(FeedStatusValue.class), //
    FEED_STATUS_UNHIDE(FeedStatusValue.class), //
    FEED_POST_ADD(FeedPostValue.class), //
    FEED_POST_EDITED(FeedPostValue.class), //
    FEED_POST_EDIT(FeedPostValue.class), //
    FEED_POST_HIDE(FeedPostValue.class), //
    FEED_POST_REMOVE(FeedPostValue.class), //
    FEED_POST_UNHIDE(FeedPostValue.class), //
    FEED_REACTION_ADD(FeedReactionValue.class), //
    FEED_REACTION_EDIT(FeedReactionValue.class), //
    FEED_REACTION_REMOVE(FeedReactionValue.class), //
    FEED_SHARE_ADD(FeedShareValue.class), //
    FEED_SHARE_EDITED(FeedShareValue.class), //
    FEED_SHARE_HIDE(FeedShareValue.class), //
    FEED_SHARE_REMOVE(FeedShareValue.class), //
    FEED_SHARE_UNHIDE(FeedShareValue.class), //
    FEED_LIKE_ADD(FeedLikeValue.class), //
    FEED_LIKE_REMOVE(FeedLikeValue.class), //
    MENTION_POST_ADD(MentionPostAddValue.class), //
    RATINGS_RATING_ADD(RatingsRatingValue.class), //
    RATINGS_RATING_EDIT(RatingsRatingValue.class), //
    RATINGS_RATING_REMOVE(RatingsRatingValue.class), //
    RATINGS_COMMENT_ADD(RatingsCommentValue.class), //
    RATINGS_COMMENT_EDITED(RatingsCommentValue.class), //
    RATINGS_COMMENT_REMOVE(RatingsCommentValue.class), //
    RATINGS_LIKE_ADD(RatingsLikeValue.class), //
    RATINGS_LIKE_REMOVE(RatingsLikeValue.class), //
    RATINGS_REACTION_ADD(RatingsReactionValue.class), //
    RATINGS_REACTION_REMOVE(RatingsReactionValue.class), //
    RATINGS_REACTION_EDIT(RatingsReactionValue.class), //
    CONVERSATIONS(PageConversation.class), //
    LEADGEN(PageLeadgen.class);

    private Class<ChangeValue> valueClass;

    ChangeValueEnumeration(Class valueClass) {
      this.valueClass = valueClass;
    }

    public Class<ChangeValue> getValueClass() {
      return valueClass;
    }
  }
}

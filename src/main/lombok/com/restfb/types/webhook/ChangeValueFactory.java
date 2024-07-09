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
package com.restfb.types.webhook;

import static com.restfb.logging.RestFBLogger.VALUE_FACTORY_LOGGER;

import com.restfb.JsonMapper;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;
import com.restfb.types.webhook.instagram.InstagramCommentsValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.instagram.InstagramStoryInsightsValue;
import com.restfb.types.webhook.whatsapp.*;

/**
 * Factory to convert the value field of the change into a class with special fields
 */
public class ChangeValueFactory {

  private String field;

  private JsonValue value;

  private ChangeValue.Verb userObjectVerb;

  public ChangeValueFactory setField(String field) {
    this.field = field;
    return this;
  }

  public ChangeValueFactory setValue(String value) {
    if (value != null && (value.startsWith("{") || value.startsWith("["))) {
      this.value = Json.parse(value);
    } else {
      this.value = Json.value(value);
    }

    return this;
  }

  public ChangeValueFactory setUserObjectVerb(ChangeValue.Verb userObjectVerb) {
    this.userObjectVerb = userObjectVerb;
    return this;
  }

  public ChangeValue buildWithMapper(JsonMapper mapper) {

    if (value.isString()) {
      return new SimpleStringChangeValue(value.asString());
    }

    if (value.isArray()) {
      return new ListJsonChangeValue(value.asArray().values());
    }

    if (value.isObject()) {

      JsonObject objValue = value.asObject();
      String classDefinition;
      if (objValue != null && field != null) {
        classDefinition = field.toUpperCase();
        if (objValue.get("item") != null) {
          classDefinition += "_" + objValue.get("item").asString().toUpperCase();
        }
        if (objValue.get("verb") != null) {
          classDefinition += "_" + objValue.get("verb").asString().toUpperCase();
        }
        if (userObjectVerb != null) {
          classDefinition += "_" + userObjectVerb.name();
        }
        if (objValue.get("messaging_product") != null) {
          classDefinition += "_" + objValue.get("messaging_product").asString().toUpperCase();
        }

        // special handling for permissions
        String verbString = (objValue.get("verb") != null && objValue.get("verb").isString()) ? objValue.get("verb").asString() : "";
        if (verbString.equals("granted") || verbString.equals("revoked")) {
          classDefinition = verbString.toUpperCase();
        }

        try {
          ChangeValueEnumeration changeValueEnum = ChangeValueEnumeration.valueOf(classDefinition);
          return mapper.toJavaObject(objValue.toString(), changeValueEnum.getValueClass());
        } catch (IllegalArgumentException iae) {
          VALUE_FACTORY_LOGGER.warn("undefined change value detected: {}", classDefinition);
          VALUE_FACTORY_LOGGER.warn("please provide this information to the restfb team: {}", objValue.toString());
          return new FallBackChangeValue(objValue);
        }
      }
    }

    return null;
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
    FEED_VIDEO_HIDE(FeedVideoValue.class), //
    FEED_VIDEO_UNHIDE(FeedVideoValue.class), //
    FEED_VIDEO_REMOVE(FeedVideoRemoveValue.class), //
    FEED_VIDEO_UNBLOCK(FeedVideoBlockMute.class), //
    FEED_VIDEO_BLOCK(FeedVideoBlockMute.class), //
    FEED_VIDEO_MUTE(FeedVideoBlockMute.class), //
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
    MENTION_COMMENT_ADD(MentionCommentAddValue.class), //
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
    LEADGEN(PageLeadgen.class), //

    // Instagram
    STORY_INSIGHTS(InstagramStoryInsightsValue.class), //
    MENTIONS(InstagramMentionsValue.class), //
    COMMENTS(InstagramCommentsValue.class), //
    LIVE_COMMENTS(InstagramCommentsValue.class), //

    // User
    HOMETOWN_ADD(UserPageValue.class), //
    MOVIES_ADD(UserPageValue.class), //
    LIKES_ADD(UserPageValue.class), //
    LOCATION_ADD(UserPageValue.class), //
    TELEVISION_ADD(UserPageValue.class), //
    GRANTED(PermissionChangeValue.class), //
    REVOKED(PermissionChangeValue.class), //

    // Whatsapp Business Account
    MESSAGE_TEMPLATE_STATUS_UPDATE(MessageTemplateStatusUpdateValue.class), //
    PHONE_NUMBER_NAME_UPDATE(PhoneNumberNameUpdateValue.class), //
    PHONE_NUMBER_QUALITY_UPDATE(PhoneNumberQualityUpdateValue.class), //
    ACCOUNT_UPDATE(AccountUpdateValue.class), //
    ACCOUNT_REVIEW_UPDATE(AccountReviewUpdateValue.class),//

    // Whatsapp Business Platform
    MESSAGES_WHATSAPP(WhatsappMessagesValue.class);

    private Class<ChangeValue> valueClass;

    ChangeValueEnumeration(Class valueClass) {
      this.valueClass = valueClass;
    }

    public Class<ChangeValue> getValueClass() {
      return valueClass;
    }
  }
}

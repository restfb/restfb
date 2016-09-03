/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
package com.restfb.types;

import static com.restfb.json.JsonObject.getNames;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Comment Graph API type</a>.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Comment extends FacebookType {

  /**
   * User who posted the comment.
   *
   * @return User who posted the comment.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType from;

  /**
   * Text contents of the comment.
   *
   * @return Text contents of the comment.
   */
  @Getter
  @Setter
  @Facebook
  private String message;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Date on which the comment was created.
   *
   * @return Date on which the comment was created.
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * Duplicate mapping for "likes" since FB can return it differently in different situations.
   *
   * -- GETTER -- The likes on this post.
   * <p>
   * Sometimes this can be {@code null} - check {@link #getLikeCount()} instead in that case.
   *
   * @return The likes on this comment.
   *
   */
  @Getter
  @Setter
  @Facebook
  private Likes likes;

  /**
   * The number of likes on this comment.
   *
   * @return The number of likes on this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("like_count")
  private Long likeCount;

  /**
   * Number of replies to this comment.
   *
   * @return Number of replies to this comment
   */
  @Getter
  @Setter
  @Facebook("comment_count")
  private long commentCount;

  /**
   * This field is returned only if the authenticated user can remove this comment.
   *
   * @return This field is returned only if the authenticated user can remove this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("can_remove")
  private Boolean canRemove;

  /**
   * This field is returned only if the authenticated user likes this comment
   *
   * @return This field is returned only if the authenticated user likes this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("user_likes")
  private Boolean userLikes;

  /**
   * If this comment is a reply, this field returns the parent comment, otherwise no value
   *
   * @return the parent Comment
   * @since 1.6.13
   */
  @Getter
  @Setter
  @Facebook
  private Comment parent;

  /**
   * Specifies whether you can reply to this comment
   *
   * @return can_comment
   * @since 1.6.13
   */
  @Getter
  @Setter
  @Facebook("can_comment")
  private Boolean canComment;

  /**
   * @deprecated use {@see Comment#getCanComment} instead
   * @return can_comment
   */
  @Deprecated
  public boolean isCanComment() {
    if (canComment != null) {
      return canComment.booleanValue();
    }
    return false;
  }

  /**
   * Whether the viewer can hide this comment
   *
   * @return can_hide
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook("can_hide")
  private Boolean canHide;

  /**
   * @deprecated use {@see Comment#getCanHide} instead
   * @return can_hide
   */
  @Deprecated
  public boolean isCanHide() {
    if (canHide != null) {
      return canHide.booleanValue();
    }
    return false;
  }

  /**
   * Whether the viewer can send a private reply to this comment (Page viewers only)
   *
   * @RestFB.GraphApi.Since 2.5
   * @return Whether the viewer can send a private reply to this comment
   */
  @Getter
  @Setter
  @Facebook("can_reply_privately")
  private Boolean canReplyPrivately;

  /**
   * For comments with private replies, gets conversation between the Page and author of the comment (Page viewers only)
   *
   * @RestFB.GraphApi.Since 2.5
   * @return conversation between Page and author of the comment
   */
  @Getter
  @Setter
  @Facebook("private_reply_conversation")
  private Conversation privateReplyConversation;

  /**
   * Whether this comment is hidden. The original poster can still see the comment, along with the page admin and anyone
   * else tagged in the comment
   *
   * @return is_hidden
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook("is_hidden")
  private Boolean isHidden;

  /**
   * Whether the viewer can like this comment
   *
   * @return can_like
   */
  @Getter
  @Setter
  @Facebook("can_like")
  private Boolean canLike;

  /**
   * Parent object this comment was made on.
   *
   * @return object
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType object;

  /**
   * The replies to this comment
   *
   * @return replies
   */
  @Getter
  @Setter
  @Facebook("comments")
  private Comments comments;

  /**
   * Attachment (image, link, album, video) added to a comment.
   *
   * To force Facebook to fill the <code>attachment</code> field you have to fetch the comment with the
   * <code>fields=attachment</code> parameter, otherwise the attachments are <code>null</code>.
   *
   * @return Attachment on the comment
   */
  @Getter
  @Setter
  @Facebook
  private StoryAttachment attachment;

  @Facebook("message_tags")
  private String rawMessageTags;

  private List<MessageTag> messageTags = new ArrayList<MessageTag>();

  private static final long serialVersionUID = 2L;

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }

  /**
   * Post-JSON-mapping operation that populates the {@code messageTags} field "by hand".
   *
   * @param jsonMapper
   *          The {@code JsonMapper} that was used to map to this type.
   */
  @JsonMappingCompleted
  protected void jsonMappingCompleted(JsonMapper jsonMapper) {

    if (rawMessageTags == null) {
      return;
    }

    try {
      messageTags = jsonMapper.toJavaList(rawMessageTags, MessageTag.class);
      return;
    } catch (FacebookJsonMappingException je) {
      // message tags not in Graph API 2.5 format, ignore this exception and try another way
    }

    try {
      JsonObject rawMessageTagsObject = jsonMapper.toJavaObject(rawMessageTags, JsonObject.class);
      for (String key : getNames(rawMessageTagsObject)) {
        String tagArrayString = rawMessageTagsObject.get(key).toString();
        messageTags.addAll(jsonMapper.toJavaList(tagArrayString, MessageTag.class));
      }
      return;
    } catch (FacebookJsonMappingException je) {

    }
  }

  /**
   * Represents the Replies to a Comment</a>.
   *
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Comments extends AbstractFacebookType {

    /**
     * The number of comments.
     *
     * @return The number of comments.
     */
    @Getter
    @Setter
    @Facebook
    private Long count = 0L;

    @Facebook
    private List<Comment> data = new ArrayList<Comment>();

    private static final long serialVersionUID = 1L;

    /**
     * The comments.
     *
     * @return The comments.
     */
    public List<Comment> getData() {
      return unmodifiableList(data);
    }

    public boolean addData(Comment comment) {
      return data.add(comment);
    }

    public boolean removeData(Comment comment) {
      return data.remove(comment);
    }
  }

  /**
   * Objects tagged in the message (Users, Pages, etc).
   *
   * @return Objects tagged in the message (Users, Pages, etc).
   * @since 1.6.10
   */
  public List<MessageTag> getMessageTags() {
    return unmodifiableList(messageTags);
  }

  public void addMessageTag(MessageTag messageTag) {
    messageTags.add(messageTag);
  }

  public void removeMessageTag(MessageTag messageTag) {
    messageTags.remove(messageTag);
  }
}

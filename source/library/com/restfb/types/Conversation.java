/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/page/#conversations"> Conversation Graph
 * API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Felipe Kurkowski
 */
public class Conversation extends FacebookType {
  @Facebook
  private String link;

  @Facebook
  private String subject;

  @Facebook
  private String snippet;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook("message_count")
  private Long messageCount;

  /**
   * Facebook does not send the unread count if there aren't any new messages. In order to keep data consistency, we set
   * the default value to zero. If this value is sent, the {@link com.restfb.JsonMapper} will override it.
   */
  @Facebook("unread_count")
  private Long unreadCount = 0L;

  @Facebook
  private List<Tag> tags = new ArrayList<Tag>();

  @Facebook
  private List<NamedFacebookType> participants = new ArrayList<NamedFacebookType>();

  @Facebook("former_participants")
  private List<NamedFacebookType> formerParticipants = new ArrayList<NamedFacebookType>();

  @Facebook
  private List<NamedFacebookType> senders = new ArrayList<NamedFacebookType>();

  @Facebook("can_reply")
  private Boolean canReply;

  @Facebook("is_subscribed")
  private Boolean isSubscribed;

  @Facebook
  private List<Message> messages;

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/page/#conversations"> Tag Graph API
   * type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @author Felipe Kurkowski
   */
  public static class Tag implements Serializable {

    @Facebook
    private String name;

    private static final long serialVersionUID = 1L;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The name field for this type.
     * 
     * @return The name field for this type.
     */
    public String getName() {
      return name;
    }
  }

  /**
   * The title of a message in the conversation
   * 
   * @return The title of a message in the conversation
   */
  public String getSnippet() {
    return snippet;
  }

  /**
   * Last update time of the conversation
   * 
   * @return Last update time of the conversation
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * The number of messages in the conversation
   * 
   * @return The number of messages in the conversation
   */
  public Long getMessageCount() {
    return messageCount;
  }

  /**
   * The number of unread messages in the conversation
   * 
   * @return The number of unread messages in the conversation
   */
  public Long getUnreadCount() {
    return unreadCount;
  }

  /**
   * A list of tags indicating the message folder, and whether the conversation is read and seen.
   * 
   * @return A list of tags indicating the message folder, and whether the conversation is read and seen.
   */
  public List<Tag> getTags() {
    return unmodifiableList(tags);
  }

  /**
   * Users who are on this message conversation
   * 
   * @return Users who are on this message conversation
   */
  public List<NamedFacebookType> getParticipants() {
    return unmodifiableList(participants);
  }

  /**
   * Users who send a message on the conversation
   * 
   * @return Users who send a message on the conversation
   */
  public List<NamedFacebookType> getSenders() {
    return unmodifiableList(senders);
  }

  /**
   * Whether The Page can reply to the conversation
   * 
   * @return Whether The Page can reply to the conversation
   */
  public Boolean getCanReply() {
    return canReply;
  }

  /**
   * Whether you are subscribed to the conversation
   * 
   * @return Whether you are subscribed to the conversation
   */
  public Boolean getSubscribed() {
    return isSubscribed;
  }

  /**
   * List of all messages in the conversation
   * 
   * @return List of all messages in the conversation
   */
  public List<Message> getMessages() {
    return unmodifiableList(messages);
  }

  /**
   * A URL for this conversation.
   * 
   * @return A URL for this conversation.
   */
  public String getLink() {
    return link;
  }

  /**
   * The subject of this conversation.
   * 
   * @return The subject of this conversation.
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Users who used to be on this message conversation.
   * 
   * @return Users who used to be on this message conversation.
   */
  public List<NamedFacebookType> getFormerParticipants() {
    return unmodifiableList(formerParticipants);
  }
}

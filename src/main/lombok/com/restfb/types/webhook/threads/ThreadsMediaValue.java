/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.threads;

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.types.threads.TdMedia;
import com.restfb.types.threads.ThMedia;
import com.restfb.types.webhook.ChangeValue;

import lombok.Getter;
import lombok.Setter;

/**
 * Media data shared by the four documented
 * <a href="https://developers.facebook.com/documentation/threads/webhooks/">Threads notification events</a>. Use
 * {@link #toThMedia()} for shared media fields and this value's reference getters for webhook identities, including
 * {@code owner_id}.
 * <p>
 * For mentions, {@code has_replies}, {@code is_reply}, {@code replied_to}, and {@code root_post} can be absent without
 * {@code threads_read_replies} permission. Missing Boolean fields in the media model remain {@code null}, not
 * {@code false}.
 */
@SuppressWarnings("removal")
public class ThreadsMediaValue extends ChangeValue {

  private TdMedia media;

  private String mediaJson;

  private JsonMapper mediaMapper;

  /**
   * Time of deletion, separate from the media's publication timestamp and the notification's send time.
   */
  @Getter
  @Setter
  @Facebook("deleted_at")
  private Date deletedAt;

  @Facebook("root_post")
  private String rawRootPost;

  @Facebook("replied_to")
  private String rawRepliedTo;

  @Facebook("owner")
  private String rawOwner;

  @Facebook("quoted_post")
  private String rawQuotedPost;

  @Facebook("reposted_post")
  private String rawRepostedPost;

  /**
   * Top-level post, including its owner ID and username when supplied.
   */
  @Getter
  @Setter
  private ThreadsReference rootPost;

  /**
   * Immediate parent, which need not be the root post.
   */
  @Getter
  @Setter
  private ThreadsReference repliedTo;

  /**
   * Owner reference supplied by deletion notifications.
   */
  @Getter
  @Setter
  private ThreadsReference owner;

  /**
   * Quoted post, when present. Accepts an ID or an expanded reference without assuming that optional webhook references
   * always have one shape.
   */
  @Getter
  @Setter
  private ThreadsReference quotedPost;

  /**
   * Reposted post, when present. Accepts an ID or an expanded reference.
   */
  @Getter
  @Setter
  private ThreadsReference repostedPost;

  /**
   * Maps a typed Threads webhook change value and retains its incoming JSON for {@link #toThMedia()}.
   *
   * @param <T>
   *          the webhook event value type
   * @param json
   *          the JSON of the webhook change's {@code value} object
   * @param type
   *          the class to map
   * @param mapper
   *          the mapper used for the webhook
   * @return the mapped value
   */
  public static <T extends ThreadsMediaValue> T fromJson(String json, Class<T> type, JsonMapper mapper) {
    T value = mapper.toJavaObject(json, type);
    if (value != null) {
      ThreadsMediaValue mediaValue = value;
      mediaValue.setMedia(mapper.toJavaObject(json, TdMedia.class));
      mediaValue.mediaJson = json;
      mediaValue.mediaMapper = mapper;
    }
    return value;
  }

  /**
   * Maps a fresh snapshot of the media in the incoming webhook JSON. Changes made to legacy media via
   * {@link #getMedia()} or {@link #setMedia(TdMedia)} do not affect this snapshot. For complete webhook identity
   * information use {@link #getRootPost()}, {@link #getRepliedTo()}, and {@link #getOwner()}.
   *
   * @return the media from the original payload, or {@code null} if there was no webhook payload mapped by the factory
   */
  public ThMedia toThMedia() {
    return mediaMapper == null ? null : mediaMapper.toJavaObject(mediaJson, ThMedia.class);
  }

  /**
   * @deprecated Use {@link #toThMedia()} for new code. Scheduled for removal no earlier than 2028.
   */
  @Deprecated(forRemoval = true)
  public TdMedia getMedia() {
    return media;
  }

  /**
   * @deprecated This only sets the legacy media. Scheduled for removal no earlier than 2028.
   */
  @Deprecated(forRemoval = true)
  public void setMedia(TdMedia media) {
    this.media = media;
  }

  @JsonMapper.JsonMappingCompleted
  private void mapReferences(JsonMapper mapper) {
    rootPost = toReference(rawRootPost, mapper);
    repliedTo = toReference(rawRepliedTo, mapper);
    owner = toReference(rawOwner, mapper);
    quotedPost = toReference(rawQuotedPost, mapper);
    repostedPost = toReference(rawRepostedPost, mapper);
  }

  private ThreadsReference toReference(String raw, JsonMapper mapper) {
    if (raw == null) {
      return null;
    }
    if (raw.startsWith("{")) {
      return mapper.toJavaObject(raw, ThreadsReference.class);
    }
    ThreadsReference reference = new ThreadsReference();
    reference.setId(raw);
    return reference;
  }
}

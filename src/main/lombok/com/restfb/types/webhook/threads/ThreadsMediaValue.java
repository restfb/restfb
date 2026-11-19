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
import com.restfb.types.webhook.ChangeValue;

import lombok.Getter;
import lombok.Setter;

/**
 * Media data shared by the four documented
 * <a href="https://developers.facebook.com/documentation/threads/webhooks/">Threads notification events</a>. Common
 * fields are mapped once using {@link TdMedia}, rather than duplicating the media model for every event. Use this
 * value's reference getters for webhook identities, including {@code owner_id}.
 * <p>
 * For mentions, {@code has_replies}, {@code is_reply}, {@code replied_to}, and {@code root_post} can be absent without
 * {@code threads_read_replies} permission. Missing Boolean fields in {@link #getMedia()} remain {@code null}, not
 * {@code false}.
 */
public class ThreadsMediaValue extends ChangeValue {

  /**
   * Shared media fields, including ID, username, timestamps, attachments, author profile, and optional flags. Populated
   * by {@link com.restfb.types.webhook.ChangeValueFactory} from the same JSON value. For complete webhook identity
   * information, prefer {@link #getRootPost()}, {@link #getRepliedTo()}, and {@link #getOwner()} over the corresponding
   * REST media references.
   */
  @Getter
  @Setter
  private TdMedia media;

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

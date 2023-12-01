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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MessagingAttachment {

  /**
   * The "audio" attachment type.
   */
  public static final String AUDIO = "audio";

  /**
   * The "fallback" attachment type.
   */
  public static final String FALLBACK = "fallback";

  /**
   * The "file" attachment type.
   */
  public static final String FILE = "file";

  /**
   * The "image" attachment type.
   */
  public static final String IMAGE = "image";

  /**
   * The "location" attachment type.
   */
  public static final String LOCATION = "location";

  /**
   * The "video" attachment type.
   */
  public static final String VIDEO = "video";

  /**
   * The "unsupported type" attachment type
   */
  public static final String UNSUPPORTED_TYPE = "unsupported_type";

  /**
   * The "template" attachment type.
   */
  public static final String TEMPLATE = "template";

  /**
   * The "story_mention" attachment type.
   */
  public static final String STORY_MENTION = "story_mention";

  @Getter
  @Setter
  @Facebook
  private String type;

  @Getter
  @Setter
  @Facebook
  private String title;

  @Getter
  @Setter
  @Facebook
  private String url;

  @Facebook("payload")
  private String fallbackPayload;

  @Getter
  @Setter
  @Facebook
  private MessagingPayload payload;

  @JsonMapper.JsonMappingCompleted
  private void selectPayload() {
    if (payload == null) {
      payload = new MessagingPayload();
      payload.setFallback(fallbackPayload);
    }
  }

  /**
   * convenience method to check if the attachment type is audio
   *
   * @return {@code true} if audio, {@code false} if not audio
   */
  public boolean isAudio() {
    return AUDIO.equals(type);
  }

  /**
   * convenience method to check if the attachment type is fallback
   *
   * @return {@code true} if fallback, {@code false} if not fallback
   */
  public boolean isFallback() {
    return FALLBACK.equals(type);
  }

  /**
   * convenience method to check if the attachment type is file
   *
   * @return {@code true} if file, {@code false} if not file
   */
  public boolean isFile() {
    return FILE.equals(type);
  }

  /**
   * convenience method to check if the attachment type is image
   *
   * @return {@code true} if image, {@code false} if not image
   */
  public boolean isImage() {
    return IMAGE.equals(type);
  }

  /**
   * convenience method to check if the attachment type is location
   *
   * @return {@code true} if location, {@code false} if not location
   */
  public boolean isLocation() {
    return LOCATION.equals(type);
  }

  /**
   * convenience method to check if the attachment type is video
   *
   * @return {@code true} if video, {@code false} if not video
   */
  public boolean isVideo() {
    return VIDEO.equals(type);
  }

  /**
   * convenience method to check if the attachment type is template
   *
   * @return {@code true} if template, {@code false} if not template
   */
  public boolean isTemplate() {
    return TEMPLATE.equals(type);
  }

  /**
   * convenience method to check if the attachment type is unsupported
   *
   * @return {@code true} if unsupported type, {@code false} if supported
   */
  public boolean isUnsupportedType() {
    return UNSUPPORTED_TYPE.equals(type);
  }

  /**
   * convenience method to check if the attachment type is story_mention
   *
   * @return {@code true} if story_mention type, {@code false} if not story_mention
   */
  public boolean isStoryMention() {
    return STORY_MENTION.equals(type);
  }
}

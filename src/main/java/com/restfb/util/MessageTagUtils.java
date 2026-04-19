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
package com.restfb.util;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.restfb.types.MessageTag;

/**
 * Helper to transform a Graph API {@code message} + {@code message_tags} pair into a normalized representation that
 * uses the mention syntax (e.g. {@code @[12345]}).
 */
public final class MessageTagUtils {

  private MessageTagUtils() {
    // prevent instantiation
  }

  /**
   * Rebuilds a message so that tagged segments are replaced by their mention syntax.
   *
   * @param message
   *          original text returned by the Graph API
   * @param tags
   *          message tags as returned by the Graph API
   * @return the normalized message or the original one if the tags don't provide enough information
   */
  public static String normalizeMessage(String message, List<MessageTag> tags) {
    if (message == null || tags == null || tags.isEmpty()) {
      return message;
    }

    List<MessageTag> applicableTags = new ArrayList<>();
    for (MessageTag tag : tags) {
      if (isApplicable(tag)) {
        applicableTags.add(tag);
      }
    }

    if (applicableTags.isEmpty()) {
      return message;
    }

    applicableTags.sort(tagComparator());

    StringBuilder normalized = new StringBuilder(message.length() + applicableTags.size() * 4);
    int cursor = 0;

    for (MessageTag tag : applicableTags) {
      int start = tag.getOffset();
      int end = start + tag.getLength();

      if (start < cursor || start < 0 || end < start || end > message.length()) {
        return message;
      }

      normalized.append(message, cursor, start);
      normalized.append(buildMention(tag));
      cursor = end;
    }

    normalized.append(message.substring(cursor));
    return normalized.toString();
  }

  private static boolean isApplicable(MessageTag tag) {
    return tag != null && tag.getOffset() != null && tag.getLength() != null && tag.getOffset() >= 0
        && tag.getLength() >= 0;
  }

  private static Comparator<MessageTag> tagComparator() {
    return comparingInt(MessageTag::getOffset).thenComparing((a, b) -> Integer.compare(b.getLength(), a.getLength()));
  }

  private static String buildMention(MessageTag tag) {
    String id = tag.getId();
    if (id != null && !id.isEmpty()) {
      return "@[" + id + "]";
    }

    String name = tag.getName();
    if (name != null) {
      return name;
    }

    return "";
  }
}

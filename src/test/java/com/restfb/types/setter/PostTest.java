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
package com.restfb.types.setter;

import org.junit.jupiter.api.Test;

import com.restfb.types.Post;
import com.restfb.types.api.SetterGetterTestBase;

class PostTest extends SetterGetterTestBase {

  @Test
  void test() {
    Post obj = new Post();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    addIgnoredField("rawMessageTags");
    addIgnoredField("rawStoryTags");
    addIgnoredField("rawScheduledPublishTime");
    addIgnoredField("likesCount");
    addIgnoredField("messageTags");
    addIgnoredField("allowedAdvertisingObjectives");
    testInstance(obj);
  }

  @Test
  void testAction() {
    Post.Action obj = new Post.Action();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  void testProperty() {
    Post.Property obj = new Post.Property();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  void testShares() {
    Post.Shares obj = new Post.Shares();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  void testFeedTargeting() {
    Post.FeedTargeting obj = new Post.FeedTargeting();
    addIgnoredField("rawRelevantUntilTs");
    testInstance(obj);
  }

  @Test
  void testAttachments() {
    Post.Attachments obj = new Post.Attachments();
    testInstance(obj);
  }

}

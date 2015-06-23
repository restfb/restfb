/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

import com.restfb.types.Post;
import com.restfb.types.api.SetterGetterTestBase;
import org.junit.Test;

public class PostTest extends SetterGetterTestBase {

    @Test
    public void test() {
	Post obj = new Post();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	addIgnoredField("rawMessageTags");
	addIgnoredField("likesCount");
	addIgnoredField("messageTags");
	testInstance(obj);
    }

    @Test
    public void testAction() {
	Post.Action obj = new Post.Action();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testComments() {
	Post.Comments obj = new Post.Comments();
	addIgnoredField("summary");
	testInstance(obj);
    }

    @Test
    public void testLikes() {
	Post.Likes obj = new Post.Likes();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	addIgnoredField("summary");
	testInstance(obj);
    }

    @Test
    public void testMessageTag() {
	Post.MessageTag obj = new Post.MessageTag();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testPlace() {
	Post.Place obj = new Post.Place();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testPrivacy() {
	Post.Privacy obj = new Post.Privacy();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testProperty() {
	Post.Property obj = new Post.Property();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testShares() {
	Post.Shares obj = new Post.Shares();
	addIgnoredField("rawUpdatedTime");
	addIgnoredField("rawCreatedTime");
	testInstance(obj);
    }

    @Test
    public void testFeedTargeting() {
	Post.FeedTargeting obj = new Post.FeedTargeting();
	testInstance(obj);
    }

    @Test
    public void testAttachments() {
	Post.Attachments obj = new Post.Attachments();
	testInstance(obj);
    }

}

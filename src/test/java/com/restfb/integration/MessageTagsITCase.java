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
package com.restfb.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.*;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Comment;
import com.restfb.types.MessageTag;

class MessageTagsITCase extends RestFbIntegrationTestBase {

  @Test
  void fetchComments() {
    // get FacebookClient
    FacebookClient facebookClient = new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.LATEST);

    // get messages associated with this post
    String postId = "162523134477_10156566664184478";
    Connection<Comment> postComments = facebookClient.fetchConnection(postId + "/comments", Comment.class,
      Parameter.with("fields", "id, permalink_url, from, created_time, message, message_tags, attachment"));

    assertNotNull(postComments);

    for (List<Comment> commts : postComments) {
      for (Comment comm : commts) {
        // The two "if"s below are 2 examples of comment ids that work and not work. You can
        // put breakpoints in them to stop executing only at the interesting comments.
        if (comm.getId().equals("10156566664184478_10156576809999478")) {
          System.out.println("This one works - the message_tags list has 1 element with MessageTags.type = page\n");
        } else if (comm.getId().equals("10156566664184478_10156576298439478")) {
          System.out.println(
            "This one does not works - the message_tags list is empty altough the FB explorer indicats 1 element with MessageTags.type = user\n");
        } else {
          continue;
        }

        System.out.println("Comment date: " + comm.getCreatedTime());
        System.out.println("          id: " + comm.getId());
        System.out.println("         msg: " + comm.getMessage());
        System.out.println("        user: " + comm.getFrom().getName());

        // get mentions from field message_tags if present.
        List<MessageTag> msgtaglist = comm.getMessageTags();
        if ((msgtaglist != null) && (!msgtaglist.isEmpty())) {
          System.out.println("msgtaglist.size() = " + msgtaglist.size());
          for (MessageTag mt : msgtaglist) {
            System.out.printf("msgtag.name = %s, msgtag.type = %s\n", mt.getName(), mt.getType());
          }
        }
      }
    }
  }
}

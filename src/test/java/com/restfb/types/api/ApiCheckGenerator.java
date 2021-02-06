/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
package com.restfb.types.api;

import com.restfb.types.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiCheckGenerator extends BaseTestCheck {

  public void createApiProperties() throws IOException {
    Properties props = new Properties();

    // @TODO: search Classes in package and replace this with a nicer call
    props.setProperty("Account.methods", joinMethods(fetchMethodsFromClass(Account.class)));
    props.setProperty("Album.methods", joinMethods(fetchMethodsFromClass(Album.class)));
    props.setProperty("AppRequest.methods", joinMethods(fetchMethodsFromClass(AppRequest.class)));
    props.setProperty("AppRequest.Application.methods",
      joinMethods(fetchMethodsFromClass(AppRequest.Application.class)));
    props.setProperty("Application.methods", joinMethods(fetchMethodsFromClass(Application.class)));
    props.setProperty("CategorizedFacebookType.methods",
      joinMethods(fetchMethodsFromClass(CategorizedFacebookType.class)));
    props.setProperty("Category.methods", joinMethods(fetchMethodsFromClass(Category.class)));
    props.setProperty("Checkin.methods", joinMethods(fetchMethodsFromClass(Checkin.class)));
    props.setProperty("Comment.methods", joinMethods(fetchMethodsFromClass(Comment.class)));
    props.setProperty("Conversation.methods", joinMethods(fetchMethodsFromClass(Conversation.class)));
    props.setProperty("Conversation.Tag.methods", joinMethods(fetchMethodsFromClass(Conversation.Tag.class)));
    props.setProperty("DeAuth.methods", joinMethods(fetchMethodsFromClass(DeAuth.class)));
    props.setProperty("Event.methods", joinMethods(fetchMethodsFromClass(Event.class)));
    props.setProperty("Event.Category.methods", joinMethods(fetchMethodsFromClass(Event.Category.class)));
    props.setProperty("Event.Owner.methods", joinMethods(fetchMethodsFromClass(Event.Owner.class)));
    props.setProperty("FacebookType.methods", joinMethods(fetchMethodsFromClass(FacebookType.class)));
    props.setProperty("FacebookType.Metadata.methods", joinMethods(fetchMethodsFromClass(FacebookType.Metadata.class)));
    props.setProperty("FriendList.methods", joinMethods(fetchMethodsFromClass(FriendList.class)));
    props.setProperty("Group.methods", joinMethods(fetchMethodsFromClass(Group.class)));
    props.setProperty("Insight.methods", joinMethods(fetchMethodsFromClass(Insight.class)));
    props.setProperty("Link.methods", joinMethods(fetchMethodsFromClass(Link.class)));
    props.setProperty("Location.methods", joinMethods(fetchMethodsFromClass(Location.class)));
    props.setProperty("Message.methods", joinMethods(fetchMethodsFromClass(Message.class)));
    props.setProperty("Message.Attachment.methods", joinMethods(fetchMethodsFromClass(Message.Attachment.class)));
    props.setProperty("Message.ImageData.methods", joinMethods(fetchMethodsFromClass(Message.ImageData.class)));
    props.setProperty("NamedFacebookType.methods", joinMethods(fetchMethodsFromClass(NamedFacebookType.class)));
    props.setProperty("Note.methods", joinMethods(fetchMethodsFromClass(Note.class)));
    props.setProperty("OpenGraphRating.methods", joinMethods(fetchMethodsFromClass(OpenGraphRating.class)));
    props.setProperty("Page.methods", joinMethods(fetchMethodsFromClass(Page.class)));
    props.setProperty("Page.Cover.methods", joinMethods(fetchMethodsFromClass(Page.Cover.class)));
    props.setProperty("PageConnection.methods", joinMethods(fetchMethodsFromClass(PageConnection.class)));
    props.setProperty("PageRating.methods", joinMethods(fetchMethodsFromClass(PageRating.class)));
    props.setProperty("Photo.methods", joinMethods(fetchMethodsFromClass(Photo.class)));
    props.setProperty("Photo.Image.methods", joinMethods(fetchMethodsFromClass(Photo.Image.class)));
    props.setProperty("Photo.Tag.methods", joinMethods(fetchMethodsFromClass(Photo.Tag.class)));
    props.setProperty("Place.methods", joinMethods(fetchMethodsFromClass(Place.class)));
    props.setProperty("Post.methods", joinMethods(fetchMethodsFromClass(Post.class)));
    props.setProperty("Post.Action.methods", joinMethods(fetchMethodsFromClass(Post.Action.class)));
    props.setProperty("Post.MessageTag.methods", joinMethods(fetchMethodsFromClass(MessageTag.class)));
    props.setProperty("Post.Property.methods", joinMethods(fetchMethodsFromClass(Post.Property.class)));
    props.setProperty("Post.Shares.methods", joinMethods(fetchMethodsFromClass(Post.Shares.class)));
    props.setProperty("Review.methods", joinMethods(fetchMethodsFromClass(Review.class)));
    props.setProperty("TestUser.methods", joinMethods(fetchMethodsFromClass(TestUser.class)));
    props.setProperty("Thread.methods", joinMethods(fetchMethodsFromClass(com.restfb.types.Thread.class)));
    props.setProperty("Url.methods", joinMethods(fetchMethodsFromClass(Url.class)));
    props.setProperty("User.methods", joinMethods(fetchMethodsFromClass(User.class)));
    props.setProperty("User.Currency.methods", joinMethods(fetchMethodsFromClass(User.Currency.class)));
    props.setProperty("User.Sport.methods", joinMethods(fetchMethodsFromClass(User.Sport.class)));
    props.setProperty("Video.methods", joinMethods(fetchMethodsFromClass(Video.class)));

    FileOutputStream out = new FileOutputStream("source/test/resources/api.properties");
    props.store(out, "");
  }

  public static void main(String[] args) throws IOException {
    ApiCheckGenerator gen = new ApiCheckGenerator();
    gen.createApiProperties();
  }
}

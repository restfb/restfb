/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

import com.restfb.types.Account;
import com.restfb.types.Album;
import com.restfb.types.AppRequest;
import com.restfb.types.Application;
import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Category;
import com.restfb.types.Checkin;
import com.restfb.types.Comment;
import com.restfb.types.Conversation;
import com.restfb.types.DeAuth;
import com.restfb.types.Event;
import com.restfb.types.FacebookType;
import com.restfb.types.FriendList;
import com.restfb.types.Group;
import com.restfb.types.Insight;
import com.restfb.types.Link;
import com.restfb.types.Location;
import com.restfb.types.Message;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Note;
import com.restfb.types.Page;
import com.restfb.types.PageConnection;
import com.restfb.types.PageRating;
import com.restfb.types.Photo;
import com.restfb.types.Place;
import com.restfb.types.Post;
import com.restfb.types.Question;
import com.restfb.types.QuestionOption;
import com.restfb.types.Review;
import com.restfb.types.StatusMessage;
import com.restfb.types.TestUser;
import com.restfb.types.Url;
import com.restfb.types.User;
import com.restfb.types.Venue;
import com.restfb.types.Video;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiCheckGenerator extends BaseTestCheck {

  public void createApiProperties() throws IOException {
    Properties props = new Properties();

    // @TODO: search Klasses in package and replace this with a nicer call
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
    props.setProperty("Checkin.Place.methods", joinMethods(fetchMethodsFromClass(Checkin.Place.class)));
    props.setProperty("Comment.methods", joinMethods(fetchMethodsFromClass(Comment.class)));
    props.setProperty("Comment.Attachment.methods", joinMethods(fetchMethodsFromClass(Comment.Attachment.class)));
    props.setProperty("Comment.Comments.methods", joinMethods(fetchMethodsFromClass(Comment.Comments.class)));
    props.setProperty("Comment.Image.methods", joinMethods(fetchMethodsFromClass(Comment.Image.class)));
    props.setProperty("Comment.Media.methods", joinMethods(fetchMethodsFromClass(Comment.Media.class)));
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
    props.setProperty("Post.Comments.methods", joinMethods(fetchMethodsFromClass(Post.Comments.class)));
    props.setProperty("Post.Likes.methods", joinMethods(fetchMethodsFromClass(Post.Likes.class)));
    props.setProperty("Post.MessageTag.methods", joinMethods(fetchMethodsFromClass(Post.MessageTag.class)));
    props.setProperty("Post.Place.methods", joinMethods(fetchMethodsFromClass(Post.Place.class)));
    props.setProperty("Post.Privacy.methods", joinMethods(fetchMethodsFromClass(Post.Privacy.class)));
    props.setProperty("Post.Property.methods", joinMethods(fetchMethodsFromClass(Post.Property.class)));
    props.setProperty("Post.Shares.methods", joinMethods(fetchMethodsFromClass(Post.Shares.class)));
    props.setProperty("Question.methods", joinMethods(fetchMethodsFromClass(Question.class)));
    props.setProperty("QuestionOption.methods", joinMethods(fetchMethodsFromClass(QuestionOption.class)));
    props.setProperty("Review.methods", joinMethods(fetchMethodsFromClass(Review.class)));
    props.setProperty("StatusMessage.methods", joinMethods(fetchMethodsFromClass(StatusMessage.class)));
    props.setProperty("StatusMessage.methods", joinMethods(fetchMethodsFromClass(StatusMessage.class)));
    props.setProperty("StatusMessage.methods", joinMethods(fetchMethodsFromClass(StatusMessage.class)));
    props.setProperty("TestUser.methods", joinMethods(fetchMethodsFromClass(TestUser.class)));
    props.setProperty("Thread.methods", joinMethods(fetchMethodsFromClass(com.restfb.types.Thread.class)));
    props.setProperty("Url.methods", joinMethods(fetchMethodsFromClass(Url.class)));
    props.setProperty("User.methods", joinMethods(fetchMethodsFromClass(User.class)));
    props.setProperty("User.Currency.methods", joinMethods(fetchMethodsFromClass(User.Currency.class)));
    props.setProperty("User.Education.methods", joinMethods(fetchMethodsFromClass(User.Education.class)));
    props.setProperty("User.EducationClass.methods", joinMethods(fetchMethodsFromClass(User.EducationClass.class)));
    props.setProperty("User.Picture.methods", joinMethods(fetchMethodsFromClass(User.Picture.class)));
    props.setProperty("User.Sport.methods", joinMethods(fetchMethodsFromClass(User.Sport.class)));
    props.setProperty("User.Work.methods", joinMethods(fetchMethodsFromClass(User.Work.class)));
    props.setProperty("Venue.methods", joinMethods(fetchMethodsFromClass(Venue.class)));
    props.setProperty("Video.methods", joinMethods(fetchMethodsFromClass(Video.class)));

    FileOutputStream out = new FileOutputStream("source/test/resources/api.properties");
    props.store(out, "");
  }

  public static void main(String[] args) throws IOException {
    ApiCheckGenerator gen = new ApiCheckGenerator();
    gen.createApiProperties();
  }
}

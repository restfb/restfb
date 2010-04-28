/*
 * Copyright (c) 2010 Mark Allen.
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

package com.restfb.example;

import static java.lang.System.out;

import java.util.Arrays;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * Examples of RestFB's Graph API functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Example {
  /**
   * RestFB Graph API client.
   */
  private final FacebookClient facebookClient;

  /**
   * Entry point. You must provide a single argument on the command line: a
   * valid Graph API access token.
   * 
   * @param args
   *          Command-line arguments.
   * @throws FacebookException
   *           If an error occurs while talking to the Facebook Graph API.
   */
  public static void main(String[] args) throws FacebookException {
    new Example(args[0]).runEverything();
  }

  Example(String accessToken) {
    facebookClient = new DefaultFacebookClient(accessToken);
  }

  void runEverything() throws FacebookException {
    fetchObject();
    fetchObjects();
    fetchConnections();
    search();
    metadata();
    paging();
    selection();
    rawJsonResponse();
  }

  void fetchObject() throws FacebookException {
    User user = facebookClient.fetchObject("me", User.class);
    Page page = facebookClient.fetchObject("cocacola", Page.class);

    out.println("* Fetching single objects *");
    out.println("User name: " + user.getName());
    out.println("Page fan count: " + page.getFanCount());
  }

  void fetchObjects() throws FacebookException {
    FetchObjectsResults fetchObjectsResults =
        facebookClient.fetchObjects(Arrays.asList("me", "cocacola"),
          FetchObjectsResults.class);

    out.println("* Fetching multiple objects at once *");
    out.println("User name: " + fetchObjectsResults.me.getName());
    out.println("Page fan count: " + fetchObjectsResults.page.getFanCount());
  }

  /**
   * Holds results from a "fetchObjects" call.
   */
  static class FetchObjectsResults {
    @Facebook(contains = User.class)
    User me;

    @Facebook(value = "cocacola", contains = Page.class)
    Page page;
  }

  void fetchConnections() throws FacebookException {
    Connection<User> myFriends =
        facebookClient.fetchConnection("me/friends", User.class);
    Connection<Post> myFeed =
        facebookClient.fetchConnection("me/feed", Post.class);

    out.println("* Fetching connections *");
    out.println("Count of my friends: " + myFriends.getData().size());
    out.println("First item in my feed: "
        + myFeed.getData().get(0).getMessage());
  }

  void search() throws FacebookException {
    Connection<Post> publicSearch =
        facebookClient.fetchConnection("search", Post.class, Parameter.with(
          "q", "watermelon"), Parameter.with("type", "post"));

    Connection<User> targetedSearch =
        facebookClient.fetchConnection("me/home", User.class, Parameter.with(
          "q", "Mark"), Parameter.with("type", "user"));

    out.println("* Searching connections *");
    out.println("Public search: " + publicSearch.getData().get(0).getMessage());
    out.println("Posts on my wall by friends named Mark: "
        + targetedSearch.getData().size());
  }

  void metadata() throws FacebookException {
    User userWithMetadata =
        facebookClient.fetchObject("me", User.class, Parameter.with("metadata",
          1));

    out.println("* Fetching metadata *");
    out.println("User metadata: has albums? "
        + userWithMetadata.getMetadata().getConnections().hasAlbums());
  }

  void paging() throws FacebookException {
  // TODO: implement
  }

  void selection() throws FacebookException {
  // TODO: implement
  }

  void rawJsonResponse() throws FacebookException {
    System.out.println("* Raw JSON *");
    System.out.println("User object JSON: "
        + facebookClient.fetchObject("me", String.class));
  }
}
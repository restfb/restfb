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

import java.util.Arrays;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
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
  }

  void fetchObject() throws FacebookException {
    User user = facebookClient.fetchObject("me", User.class);
    Page page = facebookClient.fetchObject("cocacola", Page.class);

    System.out.println(String.format("** FETCH OBJECT **\nUser: %s\nPage: %s",
      user, page));
  }

  void fetchObjects() throws FacebookException {
    List<User> users =
        facebookClient.fetchObjects(Arrays.asList("me", "arjun"), User.class);

    System.out.println(String.format("** FETCH OBJECTS **\nUsers: %s", users));
  }

  void fetchConnections() throws FacebookException {
    Connection<User> myFriends =
        facebookClient.fetchConnection("me/friends", User.class);
    Connection<Post> myFeed =
        facebookClient.fetchConnection("me/feed", Post.class);

    System.out.println(String.format(
      "** FETCH CONNECTIONS **\nMy Friends: %s\nMy Feed: %s", myFriends
        .getData(), myFeed.getData()));
  }
}
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class GraphReaderExample {
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
   * @throws IllegalArgumentException
   *           If no command-line arguments are provided.
   */
  public static void main(String[] args) throws FacebookException {
    if (args.length == 0)
      throw new IllegalArgumentException(
        "You must provide an OAuth access token parameter. "
            + "See README for more information.");

    new GraphReaderExample(args[0]).runEverything();
  }

  GraphReaderExample(String accessToken) {
    facebookClient = new DefaultFacebookClient(accessToken);
  }

  void runEverything() throws FacebookException {
    fetchObject();
    fetchObjects();
    fetchConnections();
    query();
    multiquery();
    search();
    metadata();
    paging();
    selection();
    parameters();
    rawJsonResponse();
  }

  void fetchObject() throws FacebookException {
    out.println("* Fetching single objects *");

    User user = facebookClient.fetchObject("me", User.class);
    Page page = facebookClient.fetchObject("cocacola", Page.class);

    out.println("User name: " + user.getName());
    out.println("Page fan count: " + page.getFanCount());
  }

  void fetchObjects() throws FacebookException {
    out.println("* Fetching multiple objects at once *");

    FetchObjectsResults fetchObjectsResults =
        facebookClient.fetchObjects(Arrays.asList("me", "cocacola"),
          FetchObjectsResults.class);

    out.println("User name: " + fetchObjectsResults.me.getName());
    out.println("Page fan count: " + fetchObjectsResults.page.getFanCount());
  }

  /**
   * Holds results from a "fetchObjects" call.
   */
  public static class FetchObjectsResults {
    @Facebook
    User me;

    @Facebook(value = "cocacola")
    Page page;
  }

  void fetchConnections() throws FacebookException {
    out.println("* Fetching connections *");

    Connection<User> myFriends =
        facebookClient.fetchConnection("me/friends", User.class);
    Connection<Post> myFeed =
        facebookClient.fetchConnection("me/feed", Post.class);

    out.println("Count of my friends: " + myFriends.getData().size());
    out.println("First item in my feed: "
        + myFeed.getData().get(0).getMessage());
  }

  void query() throws FacebookException {
    out.println("* FQL Query *");

    List<User> users =
        facebookClient.executeQuery("SELECT name FROM user WHERE uid=220439",
          User.class);

    out.println("User: " + users.get(0).getName());
  }

  void multiquery() throws FacebookException {
    out.println("* FQL Multiquery *");

    Map<String, String> queries = new HashMap<String, String>();
    queries.put("users",
      "SELECT name FROM user WHERE uid=220439 OR uid=7901103");
    queries.put("likers",
      "SELECT user_id FROM like WHERE object_id=122788341354");

    MultiqueryResults multiqueryResults =
        facebookClient.executeMultiquery(queries, MultiqueryResults.class);

    out.println("User count: " + multiqueryResults.users.size());
    out.println("People who liked: " + multiqueryResults.likers.size());
  }

  /**
   * Holds results from a "multiquery" call.
   */
  public static class MultiqueryResults {
    @Facebook(contains = User.class)
    List<User> users;

    @Facebook(contains = String.class)
    List<String> likers;
  }

  void search() throws FacebookException {
    out.println("* Searching connections *");

    Connection<Post> publicSearch =
        facebookClient.fetchConnection("search", Post.class, Parameter.with(
          "q", "watermelon"), Parameter.with("type", "post"));

    Connection<User> targetedSearch =
        facebookClient.fetchConnection("me/home", User.class, Parameter.with(
          "q", "Mark"), Parameter.with("type", "user"));

    out.println("Public search: " + publicSearch.getData().get(0).getMessage());
    out.println("Posts on my wall by friends named Mark: "
        + targetedSearch.getData().size());
  }

  void metadata() throws FacebookException {
    out.println("* Metadata *");

    User userWithMetadata =
        facebookClient.fetchObject("me", User.class, Parameter.with("metadata",
          1));

    out.println("User metadata: has albums? "
        + userWithMetadata.getMetadata().getConnections().hasAlbums());
  }

  void paging() throws FacebookException {
    out.println("* Paging support *");

    Connection<Post> feedPage1 =
        facebookClient.fetchConnection("me/feed", Post.class);
    Connection<Post> feedPage2 =
        feedPage1.hasNext() ? facebookClient.fetchConnection("me/feed",
          Post.class, Parameter.with("offset", feedPage1.getData().size()))
            : null;

    out.println("Posts on page 1: " + feedPage1.getData().size());
    out.println("Posts on page 2: "
        + (feedPage2 == null ? 0 : feedPage2.getData().size()));
  }

  void selection() throws FacebookException {
    out.println("* Selecting specific fields *");

    User user =
        facebookClient.fetchObject("me", User.class, Parameter.with("fields",
          "id,name,picture"));

    out.println("User picture: " + user.getPicture());
  }

  void parameters() throws FacebookException {
    out.println("* Parameter support *");

    Date oneWeekAgo =
        new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 7L);

    Connection<Post> filteredFeed =
        facebookClient.fetchConnection("me/feed", Post.class, Parameter.with(
          "limit", 3), Parameter.with("until", "yesterday"), Parameter.with(
          "since", oneWeekAgo));

    out.println("Filtered feed count: " + filteredFeed.getData().size());
  }

  void rawJsonResponse() throws FacebookException {
    System.out.println("* Raw JSON *");
    System.out.println("User object JSON: "
        + facebookClient.fetchObject("me", String.class));
  }
}
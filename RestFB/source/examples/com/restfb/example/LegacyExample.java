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

import java.util.List;

import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookException;
import com.restfb.LegacyFacebookClient;
import com.restfb.Parameter;

/**
 * Examples of RestFB's Legacy REST API functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class LegacyExample {
  /**
   * RestFB Legacy REST API client.
   */
  private LegacyFacebookClient legacyFacebookClient;

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
    new LegacyExample(args[0]).runEverything();
  }

  LegacyExample(String accessToken) {
    legacyFacebookClient = new DefaultLegacyFacebookClient(accessToken);
  }

  void runEverything() throws FacebookException {
    list();
  }

  void list() throws FacebookException {
    out.println("* Call that returns a list *");

    List<LegacyUser> users =
        legacyFacebookClient.executeForList("Users.getInfo", LegacyUser.class,
          Parameter.with("uids", "220439, 7901103"), Parameter.with("fields",
            "last_name, first_name"));

    out.println("Users: " + users);
  }

  /**
   * Holds user information as retrieved in {@link #list()}.
   */
  public static class LegacyUser {
    @Facebook("first_name")
    String firstName;

    @Facebook("last_name")
    String lastName;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s %s", firstName, lastName);
    }
  }
}
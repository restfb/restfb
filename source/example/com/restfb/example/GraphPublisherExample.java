/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

import java.util.Date;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

/**
 * Examples of RestFB's Graph API functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class GraphPublisherExample extends Example {
  /**
   * RestFB Graph API client.
   */
  private final FacebookClient facebookClient;

  /**
   * Entry point. You must provide a single argument on the command line: a
   * valid Graph API access token. In order for publishing to succeed, you must
   * use an access token for an application that has been granted stream_publish
   * and create_event rights.
   * 
   * @param args
   *          Command-line arguments.
   * @throws IllegalArgumentException
   *           If no command-line arguments are provided.
   */
  public static void main(String[] args) {
    if (args.length == 0)
      throw new IllegalArgumentException("You must provide an OAuth access token parameter. "
          + "See README for more information.");

    new GraphPublisherExample(args[0]).runEverything();
  }

  GraphPublisherExample(String accessToken) {
    facebookClient = new DefaultFacebookClient(accessToken);
  }

  void runEverything() {
    String messageId = publishMessage();
    delete(messageId);
    String eventId = publishEvent();
    delete(eventId);
    String photoId = publishPhoto();
    delete(photoId);
  }

  String publishMessage() {
    out.println("* Feed publishing *");

    FacebookType publishMessageResponse =
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", "RestFB test"));

    out.println("Published message ID: " + publishMessageResponse.getId());
    return publishMessageResponse.getId();
  }

  String publishEvent() {
    out.println("* Event publishing *");

    Date tomorrow = new Date(currentTimeMillis() + 1000L * 60L * 60L * 24L);
    Date twoDaysFromNow = new Date(currentTimeMillis() + 1000L * 60L * 60L * 48L);

    FacebookType publishEventResponse =
        facebookClient.publish("me/events", FacebookType.class, Parameter.with("name", "Party"),
          Parameter.with("start_time", tomorrow), Parameter.with("end_time", twoDaysFromNow));

    out.println("Published event ID: " + publishEventResponse.getId());
    return publishEventResponse.getId();
  }

  String publishPhoto() {
    out.println("* Binary file publishing *");

    FacebookType publishPhotoResponse =
        facebookClient.publish("me/photos", FacebookType.class,
          BinaryAttachment.with("cat.png", getClass().getResourceAsStream("/cat.png")),
          Parameter.with("message", "Test cat"));

    out.println("Published photo ID: " + publishPhotoResponse.getId());
    return publishPhotoResponse.getId();
  }

  void delete(String objectId) {
    out.println("* Object deletion *");
    out.println(format("Deleted %s: %s", objectId, facebookClient.deleteObject(objectId)));
  }
}
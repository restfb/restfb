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
package com.restfb.types.ads;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/conversion-action-query/">Conversion action
 * query type</a>
 */
public class ConversionActionQuery extends AbstractFacebookType {

  /**
   * Action type
   */
  @Getter
  @Setter
  @Facebook("action.type")
  private List<String> actionType = new ArrayList<>();

  /**
   * Application
   */
  @Getter
  @Setter
  @Facebook
  private List<String> application = new ArrayList<>();

  /**
   * Rule based offsite conversion
   */
  @Getter
  @Setter
  @Facebook("conversion_id")
  private List<String> conversionId = new ArrayList<>();

  /**
   * Event Type
   */
  @Getter
  @Setter
  @Facebook("event_type")
  private List<String> eventType = new ArrayList<>();

  /**
   * Event
   */
  @Getter
  @Setter
  @Facebook
  private List<String> event = new ArrayList<>();

  /**
   * Event creator
   */
  @Getter
  @Setter
  @Facebook("event.creator")
  private List<String> eventCreator = new ArrayList<>();

  /**
   * Facebook pixel id
   */
  @Getter
  @Setter
  @Facebook("fb_pixel")
  private List<String> fbPixel = new ArrayList<>();

  /**
   * Facebook pixel event
   */
  @Getter
  @Setter
  @Facebook("fb_pixel_event")
  private List<String> fbPixelEvent = new ArrayList<>();

  /**
   * Object
   */
  @Getter
  @Setter
  @Facebook
  private List<String> object = new ArrayList<>();

  /**
   * Object domain
   */
  @Getter
  @Setter
  @Facebook("object.domain")
  private List<String> objectDomain = new ArrayList<>();

  /**
   * Offer
   */
  @Getter
  @Setter
  @Facebook
  private List<String> offer = new ArrayList<>();

  /**
   * Offer creator
   */
  @Getter
  @Setter
  @Facebook("offer.creator")
  private List<String> offerCreator = new ArrayList<>();

  /**
   * Offsite pixel
   */
  @Getter
  @Setter
  @Facebook("offsite_pixel")
  private List<String> offsitePixel = new ArrayList<>();

  /**
   * Page
   */
  @Getter
  @Setter
  @Facebook
  private List<String> page = new ArrayList<>();

  /**
   * Page parent
   */
  @Getter
  @Setter
  @Facebook("page.parent")
  private List<String> pageParent = new ArrayList<>();

  /**
   * Post
   */
  @Getter
  @Setter
  @Facebook
  private List<String> post = new ArrayList<>();

  /**
   * Post object
   */
  @Getter
  @Setter
  @Facebook("post.object")
  private List<String> postObject = new ArrayList<>();

  /**
   * Post object wall
   */
  @Getter
  @Setter
  @Facebook("post.object.wall")
  private List<String> postObjectWall = new ArrayList<>();

  /**
   * Post wall
   */
  @Getter
  @Setter
  @Facebook("post.wall")
  private List<String> postWall = new ArrayList<>();

  /**
   * Product set id
   */
  @Getter
  @Setter
  @Facebook("product_set_id")
  private List<String> productSetId = new ArrayList<>();

  /**
   * Question
   */
  @Getter
  @Setter
  @Facebook
  private List<String> question = new ArrayList<>();

  /**
   * Question creator
   */
  @Getter
  @Setter
  @Facebook("question.creator")
  private List<String> questionCreator = new ArrayList<>();

  /**
   * Response
   */
  @Getter
  @Setter
  @Facebook
  private List<String> response = new ArrayList<>();

  /**
   * Subtype
   */
  @Getter
  @Setter
  @Facebook
  private List<String> subtype = new ArrayList<>();

}

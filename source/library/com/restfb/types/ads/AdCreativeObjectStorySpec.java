/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class AdCreativeObjectStorySpec extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("instagram_actor_id")
  private String instagramActorId;

  @Getter
  @Setter
  @Facebook("link_data")
  private AdCreativeLinkData linkData;

  @Getter
  @Setter
  @Facebook("offer_data")
  private AdCreativeOfferData offerData;

  @Getter
  @Setter
  @Facebook("page_id")
  private String pageId;

  @Getter
  @Setter
  @Facebook("photo_data")
  private AdCreativePhotoData photoData;

  @Getter
  @Setter
  @Facebook("template_data")
  private AdCreativeLinkData templateData;

  @Getter
  @Setter
  @Facebook("text_data")
  private AdCreativeTextData textData;

  @Getter
  @Setter
  @Facebook("video_data")
  private AdCreativeVideoData videoData;
}

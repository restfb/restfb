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

@Getter
@Setter
public class AdsHistogramStats extends AbstractFacebookType {
  private static final long serialVersionUID = 1L;
  @Facebook("1d_click")
  private List<Long> click1d = new ArrayList<>();
  @Facebook("1d_ev")
  private List<Long> ev1d = new ArrayList<>();
  @Facebook("1d_view")
  private List<Long> view1d = new ArrayList<>();
  @Facebook("28d_click")
  private List<Long> click28d = new ArrayList<>();
  @Facebook("28d_view")
  private List<Long> view28d = new ArrayList<>();
  @Facebook("7d_click")
  private List<Long> click7d = new ArrayList<>();
  @Facebook("7d_view")
  private List<Long> view7d = new ArrayList<>();
  @Facebook("action_brand")
  private String actionBrand;
  @Facebook("action_canvas_component_id")
  private String actionCanvasComponentId;
  @Facebook("action_canvas_component_name")
  private String actionCanvasComponentName;
  @Facebook("action_carousel_card_id")
  private String actionCarouselCardId;
  @Facebook("action_carousel_card_name")
  private String actionCarouselCardName;
  @Facebook("action_category")
  private String actionCategory;
  @Facebook("action_converted_product_id")
  private String actionConvertedProductId;
  @Facebook("action_destination")
  private String actionDestination;
  @Facebook("action_device")
  private String actionDevice;
  @Facebook("action_event_channel")
  private String actionEventChannel;
  @Facebook("action_link_click_destination")
  private String actionLinkClickDestination;
  @Facebook("action_location_code")
  private String actionLocationCode;
  @Facebook("action_reaction")
  private String actionReaction;
  @Facebook("action_target_id")
  private String actionTargetId;
  @Facebook("action_type")
  private String actionType;
  @Facebook("action_video_asset_id")
  private String actionVideoAssetId;
  @Facebook("action_video_sound")
  private String actionVideoSound;
  @Facebook("action_video_type")
  private String actionVideoType;
  @Facebook("dda")
  private List<Long> dda = new ArrayList<>();
  @Facebook("inline")
  private List<Long> inline = new ArrayList<>();
  @Facebook("interactive_component_sticker_id")
  private String interactiveComponentStickerId;
  @Facebook("interactive_component_sticker_response")
  private String interactiveComponentStickerResponse;
  @Facebook("skan_click")
  private List<Long> skanClick = new ArrayList<>();
  @Facebook("skan_view")
  private List<Long> skanView = new ArrayList<>();
  @Facebook("value")
  private List<Long> value = new ArrayList<>();
}

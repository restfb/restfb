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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/product-item/">Product Item
 * type</a>
 */
public class ProductItem extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("additional_image_urls")
  private List<String> additionalImageUrls;

  @Getter
  @Setter
  @Facebook("age_group")
  private String ageGroup;

  @Getter
  @Setter
  @Facebook("applinks")
  private AppLinks applinks;

  @Getter
  @Setter
  @Facebook("availability")
  private String availability;

  @Getter
  @Setter
  @Facebook("brand")
  private String brand;

  @Getter
  @Setter
  @Facebook("category")
  private String category;

  @Getter
  @Setter
  @Facebook("color")
  private String color;

  @Getter
  @Setter
  @Facebook("commerce_insights")
  private ProductItemCommerceInsights commerceInsights;

  @Getter
  @Setter
  @Facebook("condition")
  private String condition;

  @Getter
  @Setter
  @Facebook("custom_data")
  private Map<String, String> customData = new HashMap<String, String>();

  @Getter
  @Setter
  @Facebook("custom_label_0")
  private String customLabel0;

  @Getter
  @Setter
  @Facebook("custom_label_1")
  private String customLabel1;

  @Getter
  @Setter
  @Facebook("custom_label_2")
  private String customLabel2;

  @Getter
  @Setter
  @Facebook("custom_label_3")
  private String customLabel3;

  @Getter
  @Setter
  @Facebook("custom_label_4")
  private String customLabel4;

  @Getter
  @Setter
  @Facebook("description")
  private String description;

  @Getter
  @Setter
  @Facebook("expiration_date")
  private String expirationDate;

  @Getter
  @Setter
  @Facebook("gender")
  private String gender;

  @Getter
  @Setter
  @Facebook("gtin")
  private String gtin;

  @Getter
  @Setter
  @Facebook("image_url")
  private String imageUrl;

  @Getter
  @Setter
  @Facebook("manufacturer_part_number")
  private String manufacturerPartNumber;

  @Getter
  @Setter
  @Facebook("material")
  private String material;

  @Getter
  @Setter
  @Facebook("ordering_index")
  private Long orderingIndex;

  @Getter
  @Setter
  @Facebook("pattern")
  private String pattern;

  @Getter
  @Setter
  @Facebook("price")
  private String price;

  @Getter
  @Setter
  @Facebook("product_feed")
  private ProductFeed productFeed;

  @Getter
  @Setter
  @Facebook("product_type")
  private String productType;

  @Getter
  @Setter
  @Facebook("retailer_id")
  private String retailerId;

  @Getter
  @Setter
  @Facebook("retailer_product_group_id")
  private String retailerProductGroupId;

  @Getter
  @Setter
  @Facebook("review_rejection_reasons")
  private List<String> reviewRejectionReasons;

  @Getter
  @Setter
  @Facebook("review_status")
  private String reviewStatus;

  @Getter
  @Setter
  @Facebook("sale_price")
  private String salePrice;

  @Getter
  @Setter
  @Facebook("sale_price_end_date")
  private String salePriceEndDate;

  @Getter
  @Setter
  @Facebook("sale_price_start_date")
  private String salePriceStartDate;

  @Getter
  @Setter
  @Facebook("shipping_weight_unit")
  private String shippingWeightUnit;

  @Getter
  @Setter
  @Facebook("shipping_weight_value")
  private Double shippingWeightValue;

  @Getter
  @Setter
  @Facebook("size")
  private String size;

  @Getter
  @Setter
  @Facebook("start_date")
  private String startDate;

  @Getter
  @Setter
  @Facebook("url")
  private String url;

  @Getter
  @Setter
  @Facebook("visibility")
  private String visibility;
}

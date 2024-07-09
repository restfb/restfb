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
package com.restfb.types.whatsapp.platform.send.interactive;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Setter;

public class Section extends AbstractFacebookType {

  @Facebook("product_items")
  private List<Product> productItems;

  @Facebook
  private List<Row> rows;

  @Setter
  @Facebook
  private String title;

  public static class Product extends AbstractFacebookType {

    @Facebook("product_retailer_id")
    private String productRetailerId;

    public Product(String productRetailerId) {
      this.productRetailerId = productRetailerId;
    }
  }

  public static class Row extends AbstractFacebookType {

    @Facebook
    private String id;

    @Facebook
    private String title;

    @Setter
    @Facebook
    private String description;

    public Row(String id, String title) {
      this.id = id;
      this.title = title;
    }
  }

  public Section addRow(Row row) {
    if (rows == null) {
      rows = new ArrayList<>();
    }

    rows.add(row);
    return this;
  }

  public Section addProductItem(Product product) {
    if (productItems == null) {
      productItems = new ArrayList<>();
    }

    productItems.add(product);
    return this;
  }
}

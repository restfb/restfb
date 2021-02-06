/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.Page;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/businessmanager">Business type</a>.
 */
public class Business extends NamedAdsObject {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("creation_time")
  private Date creationTime;

  @Getter
  @Setter
  @Facebook("created_by")
  private NamedAdsObject createdBy;

  @Getter
  @Setter
  @Facebook("primary_page")
  private Page primaryPage;

  @Getter
  @Setter
  @Facebook("timezone_id")
  private Integer timezoneId;

  @Getter
  @Setter
  @Facebook("update_time")
  private Date updateTime;

  @Getter
  @Setter
  @Facebook("updated_by")
  private NamedAdsObject updatedBy;

  @Getter
  @Setter
  @Facebook("vertical_id")
  private Integer verticalId;

}

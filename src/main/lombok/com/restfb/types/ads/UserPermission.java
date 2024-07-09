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

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.features.HasCreatedTime;

import lombok.Getter;
import lombok.Setter;

public class UserPermission extends AbstractFacebookType implements HasCreatedTime {

  @Getter
  @Setter
  @Facebook("business_persona")
  private NamedAdsObject businessPersona;

  @Getter
  @Setter
  @Facebook
  private String role;

  @Getter
  @Setter
  @Facebook
  private String status;

  @Getter
  @Setter
  @Facebook
  private String email;

  @Getter
  @Setter
  @Facebook("created_by")
  private String createdBy;

  @Getter
  @Setter
  @Facebook("updated_by")
  private String updatedBy;

  @Facebook("page_permissions")
  private String pagePermissions;

  @Facebook("adaccount_permissions")
  private String adaccountPermissions;

  @Getter
  @Setter
  @Facebook
  private NamedAdsObject user;

  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  public JsonObject getPagePermissions() {
    if (pagePermissions != null) {
      return Json.parse(pagePermissions).asObject();
    } else {
      return null;
    }
  }

  public void setPagePermissions(JsonObject pagePermissions) {
    if (pagePermissions != null) {
      this.pagePermissions = pagePermissions.toString();
    } else {
      this.pagePermissions = null;
    }
  }

  public JsonObject getAdaccountPermissions() {
    if (adaccountPermissions != null) {
      return Json.parse(adaccountPermissions).asObject();
    } else {
      return null;
    }
  }

  public void setAdaccountPermissions(JsonObject adaccountPermissions) {
    if (adaccountPermissions != null) {
      this.adaccountPermissions = adaccountPermissions.toString();
    } else {
      this.adaccountPermissions = null;
    }
  }

}

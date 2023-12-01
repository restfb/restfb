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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Ported from <a href="https://github.com/facebook/facebook-java-business-sdk/blob/189b22281f75681939750fc71ae1429651865867/src/main/java/com/facebook/ads/sdk/MusicVideoCopyright.java">
 * Facebook Business Ads SDK</a>
 */
public class MusicVideoCopyright extends FacebookType {

  @Getter
  @Setter
  @Facebook("creation_time")
  private Date creationTime;

  @Getter
  @Setter
  @Facebook("displayed_matches_count")
  private Long displayedMatchesCount;

  @Getter
  @Setter
  @Facebook("in_conflict")
  private Boolean inConflict;

  @Getter
  @Setter
  @Facebook("isrc")
  private String isrc;

  @Getter
  @Setter
  @Facebook("match_rule")
  private VideoCopyrightRule matchRule;

  @Getter
  @Setter
  @Facebook("reference_file_status")
  private String referenceFileStatus;

  @Getter
  @Setter
  @Facebook("ridge_monitoring_status")
  private String ridgeMonitoringStatus;

  @Getter
  @Setter
  @Facebook("update_time")
  private Date updateTime;

  @Getter
  @Setter
  @Facebook("video_asset")
  private CopyrightReferenceContainer videoAsset;

  @Facebook("ownership_countries")
  private List<String> ownershipCountries = new ArrayList<>();

  @Facebook("whitelisted_fb_users")
  private List<User> whitelistedFbUsers = new ArrayList<>();

  @Facebook("whitelisted_ig_users")
  private List<String> whitelistedIgUsers = new ArrayList<>();

  public List<String> getOwnershipCountries() {
    return unmodifiableList(ownershipCountries);
  }

  public boolean addOwnershipCountry(String ownershipCountry) {
    return ownershipCountries.add(ownershipCountry);
  }

  public boolean removeOwnershipCountry(String ownershipCountry) {
    return ownershipCountries.remove(ownershipCountry);
  }

  public List<User> getWhitelistedFbUsers() {
    return unmodifiableList(whitelistedFbUsers);
  }

  public boolean addWhitelistedFbUser(User whitelistedFbUser) {
    return whitelistedFbUsers.add(whitelistedFbUser);
  }

  public boolean removeWhitelistedFbUsers(User whitelistedFbUser) {
    return whitelistedFbUsers.remove(whitelistedFbUser);
  }

  public List<String> getWhitelistedIgUsers() {
    return unmodifiableList(whitelistedIgUsers);
  }

  public boolean addWhitelistedIgUser(String whitelistedIgUser) {
    return whitelistedIgUsers.add(whitelistedIgUser);
  }

  public boolean removeWhitelistedIgUsers(String whitelistedIgUser) {
    return whitelistedIgUsers.remove(whitelistedIgUser);
  }
}

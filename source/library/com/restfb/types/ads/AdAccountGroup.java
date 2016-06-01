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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/adaccountgroup/">AdGroup Account Group
 * Type</a> .
 */
public class AdAccountGroup extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook
  private Integer status;

  @Getter
  @Setter
  @Facebook("account_group_id")
  private String accountGroupId;

  /**
   * The users who own the ad account group.
   */
  @Facebook
  private final List<AdAccountGroupUser> users = new ArrayList<AdAccountGroupUser>();

  /**
   * The AdGroup Accounts in the ad account group.
   */
  @Facebook
  private final List<AdAccountGroupAccount> accounts = new ArrayList<AdAccountGroupAccount>();

  public boolean addUser(AdAccountGroupUser user) {
    return users.add(user);
  }

  public boolean removeUser(AdAccountGroupUser user) {
    return users.remove(user);
  }

  public List<AdAccountGroupUser> getUsers() {
    return Collections.unmodifiableList(users);
  }

  public boolean addAccount(AdAccountGroupAccount account) {
    return accounts.add(account);
  }

  public boolean removeAccount(AdAccountGroupAccount account) {
    return accounts.remove(account);
  }

  public List<AdAccountGroupAccount> getAccounts() {
    return Collections.unmodifiableList(accounts);
  }
}

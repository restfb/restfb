/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

package com.restfb.experimental.api.impl;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.experimental.api.Pages;
import com.restfb.types.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagesImpl implements Pages {
  private final FacebookClient facebookClient;

  PagesImpl(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  @Override
  public String fetchAccessToken(String pageId) {
    Connection<Page> connection = facebookClient.fetchConnection("/me/accounts", Page.class);
    for (List<Page> pageList : connection) {
      for (Page page : pageList) {
        if (page.getId().equals(pageId)) {
          return page.getAccessToken();
        }
      }
    }

    return null;

  }

  @Override
  public Map<String, String> fetchAllAccessTokens() {

    Map<String, String> tokenMap = new HashMap<String, String>();

    Connection<Page> connection = facebookClient.fetchConnection("/me/accounts", Page.class);
    for (List<Page> pageList : connection) {
      for (Page page : pageList) {
        tokenMap.put(page.getId(), page.getAccessToken());
      }
    }

    return tokenMap;
  }
}

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
package com.restfb.integration;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Page;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SumLikesITCase extends RestFbIntegrationTestBase {

    @Test
    public void checkConnection() {
        DefaultFacebookClient client22 =
                new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_2);

        DefaultFacebookClient client27 =
                new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_7);

        long sum22 = 0;
        Connection<Page> pageConnection = client22.fetchConnection("search", Page.class, Parameter.with("q", "Student"), Parameter.with("type", "page"), Parameter.with("fields", "likes"));
        for (List<Page> singlePage : pageConnection) {
            for (Page p : singlePage) {
                sum22 += p.getFanCount();
            }
        }

        Connection<Page> pageConnection2 = client27.fetchConnection("search", Page.class, Parameter.with("q", "Student"), Parameter.with("type", "page"), Parameter.with("fields", "fan_count"));
        for (List<Page> singlePage : pageConnection2) {
            for (Page p : singlePage) {
                sum22 -= p.getFanCount();
            }
        }

        Assert.assertTrue(sum22 <= 0);
    }

}

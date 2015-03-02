/*
 * The MIT License
 *
 * Copyright 2015 viego2.
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
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.NamedFacebookType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FetchCursorOnlyConnectionITCase extends RestFbIntegrationTestBase {
    
    @Test
    public void fetchLikes() {
	DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_0);
	Connection<NamedFacebookType> con = client.fetchConnection("40796308305_1565015867092106/likes", NamedFacebookType.class);
	assertEquals("https://graph.facebook.com/v2.0/40796308305_1565015867092106/likes?after=MTU2NTAxOTcyMDQyNTA1NA==", con.getNextPageUrl());
    }
    
}

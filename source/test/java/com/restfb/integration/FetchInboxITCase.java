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

package com.restfb.integration;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FetchInboxITCase extends RestFbIntegrationTestBase {
    
    @Test
    public void fetchInboxThread() {
	DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_1);
	Connection<com.restfb.types.Thread> connection = client.fetchConnection("/" + getTestSettings().getUserId() + "/inbox", com.restfb.types.Thread.class);
	assertNotNull(connection.getData());
	
	List<com.restfb.types.Thread> threadList = connection.getData();
	assertTrue(threadList.size() > 0);
	assertTrue(threadList.get(0).getComments().size() > 0);
	
	for (com.restfb.types.Thread thread : threadList) {
	    assertNotNull(thread.getId());
	    assertNotNull(thread.getUpdatedTime());
	    assertNotNull(thread.getUnread());
	    assertNotNull(thread.getUnseen());
	}
    }
    
}

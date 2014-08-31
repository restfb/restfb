/*
 * The MIT License
 *
 * Copyright 2014 viego2.
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

package com.restfb.util;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

/**
 *
 * @author viego2
 */
public class exampleTest {
    
    public void test() {
        
        JsonObject obj = facebookClient.fetchObject("me/friends", JsonObject.class);
        
        Long totalCount = null;
        if (obj.has("summary")) {
            totalCount = obj.getJsonObject("summary").getLong("total_count");
        }
        
        Connection<User> con = new Connection<User>(FacebookClient, obj.toString(), User.class);
        
    }
    
}

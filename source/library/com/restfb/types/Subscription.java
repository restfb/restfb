/*
 * Copyright (c) 2010-2015 Mark Allen.
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

import com.restfb.Facebook;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/app/subscriptions">App Subscription type</a>.
 * 
 * @author <a href="http://restfb.com">Norbert Bartels</a>
 * @since 1.5
 */
public class Subscription implements Serializable {
    
    /**
     * Indicates the object type that this subscription applies to.
     * 
     * type: user, page, permissions, payments
     * 
     * @return the object type that this subscription applies to
     */
    @Getter
    @Setter
    @Facebook
    private String object;
    
    /**
     * The URL that will receive the POST request when an update is triggered.
     * 
     * @return the URL that will receive the POST request
     */
    @Getter
    @Setter
    @Facebook("callback_url")
    private String callbackUrl;
    
    /**
     * One or more of the set of valid fields in this object to subscribe to.
     * 
     * The set of valid fields is defined here:
     * https://developers.facebook.com/docs/graph-api/real-time-updates/v2.3#subscribefields
     * 
     * @return set of fields
     */
    @Getter
    @Setter
    @Facebook
    private List<String> fields;
    
    /**
     * Indicates whether or not the subscription is active.
     * 
     * @return if the subscription is active
     */
    @Getter
    @Setter
    @Facebook
    private Boolean active;
    
}

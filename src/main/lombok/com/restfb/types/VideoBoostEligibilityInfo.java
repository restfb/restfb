/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the video boost eligibility information type as definied here:
 * <a href="https://developers.facebook.com/docs/graph-api/reference/video-boost-eligibility-info/"></a>https://developers.facebook.com/docs/graph-api/reference/video-boost-eligibility-info/</a>
 */
public class VideoBoostEligibilityInfo extends FacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * Reason in case video is not eligible to boost
     */
    @Getter
    @Setter
    @Facebook("boost_ineligible_reason")
    private String boostIneligibleReason;

    /**
     * Whether a video is eligible to post
     */
    @Getter
    @Setter
    @Facebook("eligible_to_boost")
    private Boolean eligibleToBoost;

}

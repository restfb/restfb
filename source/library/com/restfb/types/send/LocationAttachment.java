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
package com.restfb.types.send;

import com.restfb.Facebook;

public class LocationAttachment extends MessageAttachment {

    @Facebook
    private LocationPayload payload;

    public LocationAttachment(double lat, double longVal) {
        setType("location");
        payload = new LocationPayload(lat, longVal);
    }

    private static class LocationPayload {

        @Facebook
        private Coordinates coordinates;

        public LocationPayload(double lat, double longVal) {
            coordinates = new Coordinates(lat, longVal);
        }

        private static class Coordinates {
            @Facebook
            private double lat;
            @Facebook("long")
            private double longVal;

            public Coordinates(double lat, double longVal) {
                this.lat = lat;
                this.longVal = longVal;
            }
        }
    }
}

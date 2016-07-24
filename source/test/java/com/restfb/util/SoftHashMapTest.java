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
package com.restfb.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class SoftHashMapTest {

    @Test
    public void checkSimpleSize() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        assertEquals(0l, hashMap.size());
        hashMap.put("test", 1);
        assertEquals(1l, hashMap.size());
    }

    @Test
    public void checkSimpleClear() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        hashMap.put("test", 1);
        assertEquals(1l, hashMap.size());
        hashMap.clear();
        assertEquals(0l, hashMap.size());
    }

    @Test
    public void checkSimpleGet() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        hashMap.put("test", 1);
        assertEquals(1l, hashMap.get("test").longValue());
    }

    @Test
    public void checkSimpleRemove() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        hashMap.put("test", 1);
        assertEquals(1l, hashMap.size());
        hashMap.remove("test");
        assertEquals(0l, hashMap.size());
    }

    @Test
    public void checkSimpleEntrySet() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        hashMap.put("one", 1);
        hashMap.put("two", 2);
        assertEquals(2l, hashMap.size());
        Set<Map.Entry<String, Integer>> copySet = hashMap.entrySet();
        assertEquals(2l, copySet.size());
    }

    @Test
    public void checkSimpleEntrySet2() {
        SoftHashMap<String, Integer> hashMap = new SoftHashMap<String, Integer>();
        hashMap.put("one", 1);
        hashMap.put("two", 2);
        Set<Map.Entry<String, Integer>> copySet = hashMap.entrySet();
        assertEquals(2l, copySet.size());
        for (Map.Entry<String, Integer> e : copySet) {
            if (e.getKey().equals("one")) {
                assertEquals(1l, e.getValue().longValue());
            }

            if (e.getKey().equals("two")) {
                assertEquals(2l, e.getValue().longValue());
            }
        }
    }
}

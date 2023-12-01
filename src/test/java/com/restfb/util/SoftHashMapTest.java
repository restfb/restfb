/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SoftHashMapTest {

  @Test
  void checkSimpleSize() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    assertThat(hashMap).isEmpty();
    hashMap.put("test", 1);
    assertThat(hashMap).hasSize(1);
  }

  @Test
  void checkSimpleClear() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    hashMap.put("test", 1);
    assertThat(hashMap).hasSize(1);
    hashMap.clear();
    assertThat(hashMap).isEmpty();
  }

  @Test
  void checkSimpleGet() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    hashMap.put("test", 1);
    assertThat(hashMap).containsEntry("test", 1);
  }

  @Test
  void checkSimpleRemove() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    hashMap.put("test", 1);
    assertThat(hashMap).hasSize(1);
    hashMap.remove("test");
    assertThat(hashMap).isEmpty();
  }

  @Test
  void checkSimpleEntrySet() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    hashMap.put("one", 1);
    hashMap.put("two", 2);
    assertThat(hashMap).hasSize(2);
    Set<Map.Entry<String, Integer>> copySet = hashMap.entrySet();
    assertThat(copySet).hasSize(2);
  }

  @Test
  void checkSimpleEntrySet2() {
    SoftHashMap<String, Integer> hashMap = new SoftHashMap<>();
    hashMap.put("one", 1);
    hashMap.put("two", 2);
    Set<Map.Entry<String, Integer>> copySet = hashMap.entrySet();
    assertThat(copySet).hasSize(2);
    for (Map.Entry<String, Integer> e : copySet) {
      if (e.getKey().equals("one")) {
        assertThat(e.getValue()).isEqualTo(1);
      }

      if (e.getKey().equals("two")) {
        assertThat(e.getValue()).isEqualTo(2);
      }
    }
  }
}

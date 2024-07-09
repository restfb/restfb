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
package com.restfb.types.ads;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class RTBDynamicPostTest extends AbstractJsonMapperTests {

  @Test
  void simpleCheck() {
    RTBDynamicPost dynamicPost =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v9_0/RTBDynamicPost_simple"), RTBDynamicPost.class);
    checkRTBDynamicPostObject(dynamicPost);
  }

  @Test
  void childCheck() {
    RTBDynamicPost dynamicPost =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v9_0/RTBDynamicPost_childs"), RTBDynamicPost.class);
    checkRTBDynamicPostObject(dynamicPost);
    assertNotNull(dynamicPost.getChildAttachments());
    assertEquals(3L, dynamicPost.getChildAttachments().size());

    DynamicPostChildAttachment attachment = dynamicPost.getChildAttachments().get(0);
    assertEquals(" ", attachment.getDescription());
    assertEquals("https://cdn.greatcloudprovider.org/images/aa8fba353b951d9a3af41e819b1cd86b",
      attachment.getImageUrl());
    assertEquals("https://www.ourwebshop.com/p/42364", attachment.getLink());
    assertEquals("898989898989898", attachment.getProductId());
    assertEquals("great microphone", attachment.getTitle());
  }

  private void checkRTBDynamicPostObject(RTBDynamicPost dynamicPost) {
    assertNotNull(dynamicPost);
    assertEquals("9876543212346", dynamicPost.getId());
    assertEquals(1L, dynamicPost.getComments().getTotalCount());
    assertEquals(1652629415000L, dynamicPost.getCreated().getTime());
    assertEquals("save delivery costs", dynamicPost.getMessage());
    assertEquals("great microphone", dynamicPost.getTitle());
    assertEquals("https://www.ourwebshop.com/p/42364", dynamicPost.getLink());
    assertEquals("3636363636363", dynamicPost.getOwnerId());
    assertNull(dynamicPost.getPlaceId());
    assertEquals("898989898989898", dynamicPost.getProductId());
    assertEquals(1L, dynamicPost.getComments().getTotalCount());
  }
}

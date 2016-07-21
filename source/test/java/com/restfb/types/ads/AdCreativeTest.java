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
package com.restfb.types.ads;

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

import java.util.List;

public class AdCreativeTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_6() {
    AdCreative adCreative =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/adCreative-issue507"), AdCreative.class);
    assertNotNull(adCreative);
    assertEquals("INVALID", adCreative.getObjectType());
    assertEquals("123_234", adCreative.getObjectStoryId());
    assertEquals("some text", adCreative.getName());
    assertEquals("https://www.abc.com/", adCreative.getThumbnailUrl());
    assertEquals("ACTIVE", adCreative.getRunStatus());
    assertEquals("456", adCreative.getId());

    assertNotNull(adCreative.getObjectStorySpec());
    AdCreativeObjectStorySpec spec = adCreative.getObjectStorySpec();
    assertEquals("123", spec.getPageId());

    assertNotNull(spec.getLinkData());
    AdCreativeLinkData linkData = spec.getLinkData();
    assertEquals("http://www.mycompany.com/", linkData.getLink());
    assertTrue(linkData.getMultiShareOptimized());
    assertTrue(linkData.getMultiShareEndCard());
    assertNotNull(linkData.getCallToAction());
    assertEquals("Some text", linkData.getMessage());
    assertEquals("LEARN_MORE", linkData.getCallToAction().getType());
    assertEquals("http://www.mycompany.com/", linkData.getCallToAction().getValue().getLink());

    List<AdCreativeLinkDataChildAttachment> childAttachments = linkData.getChildAttachments();
    assertEquals(3, childAttachments.size());

    for (AdCreativeLinkDataChildAttachment att : childAttachments) {
      assertEquals("http://www.mycompany.com/", att.getLink());
      assertEquals("LEARN_MORE", att.getCallToAction().getType());
    }
  }

  @Test
  public void checkV2_6_issue523() {
    AdCreative adCreative =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/adCreative-issue523"), AdCreative.class);
    assertNotNull(adCreative);
    AdsImageCrops crops = adCreative.getImageCrops();
    assertEquals(27L, crops.getThumb100x100().getLeft().longValue());
    assertEquals(0L, crops.getThumb100x100().getTop().longValue());
    assertEquals(655L, crops.getThumb100x100().getRight().longValue());
    assertEquals(628L, crops.getThumb100x100().getBottom().longValue());
  }
}

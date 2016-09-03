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
package com.restfb.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class PageLifeEventTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_7() {
    PageLifeEvent pageLifeEvent =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_7/page-life-event"), PageLifeEvent.class);
    assertEquals("493557264005313", pageLifeEvent.getId());
    assertEquals(1346237325000L, pageLifeEvent.getCreatedTime().getTime());
    assertEquals("Raggiunti 300 MI PIACE sulla pagina Facebook! :D", pageLifeEvent.getDescription());
    assertEquals(1346223600000L, pageLifeEvent.getEndTime().getTime());
    assertFalse(pageLifeEvent.getIsHidden());
    Page fromPage = pageLifeEvent.getFromPage();
    assertEquals("Costantino Carrara", fromPage.getName());
    assertEquals("288686894492352", fromPage.getId());
    assertEquals(1346223600000L, pageLifeEvent.getStartTime().getTime());
    assertEquals("300 MI PIACE!", pageLifeEvent.getTitle());
    assertEquals(1346237325000L, pageLifeEvent.getUpdatedTime().getTime());
  }

}

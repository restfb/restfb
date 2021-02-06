/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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
import static org.junit.Assert.assertNotNull;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class PlaceTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_1() {
    Place examplePlace = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/place"), Place.class);
    assertEquals(3, examplePlace.getCategoryList().size());
    Category cat1 = examplePlace.getCategoryList().get(0);
    assertEquals("Region", cat1.getName());
    assertEquals("115725465228008", cat1.getId());

    Category cat2 = examplePlace.getCategoryList().get(1);
    assertEquals("River", cat2.getName());
    assertEquals("407338945943828", cat2.getId());

    Category cat3 = examplePlace.getCategoryList().get(2);
    assertEquals("Ocean", cat3.getName());
    assertEquals("215492291888288", cat3.getId());
  }

  @Test
  public void checkV2_5_rating0() {
    Place examplePlace = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/place-with-rating"), Place.class);
    assertNotNull(examplePlace);
    assertEquals(0, examplePlace.getOverallRating().intValue());

    assertEquals("Skill Idiomas - Unidade Itaquera", examplePlace.getName());
  }
}

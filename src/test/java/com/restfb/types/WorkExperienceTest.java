/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

public class WorkExperienceTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_8_workexperience() {
    WorkExperience experience =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/work-experience"), WorkExperience.class);

    assertNotNull(experience);
    assertEquals(1193875200000L, experience.getStartDate().getTime());
    assertEquals(1348963200000L, experience.getEndDate().getTime());
    assertEquals("1234567890", experience.getEmployer().getId());
    assertEquals("Example Company", experience.getEmployer().getName());
    assertEquals("Computer Scientist", experience.getPosition().getName());
    assertEquals("108125275875311", experience.getPosition().getId());
  }
}

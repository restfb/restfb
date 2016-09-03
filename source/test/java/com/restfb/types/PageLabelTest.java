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
import static org.junit.Assert.assertNotNull;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class PageLabelTest extends AbstractJsonMapperTests {

    @Test
    public void checkBasic_V2_6() {
        PageLabel pageLabel = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/pagelabel-basic"), PageLabel.class);
        assertNotNull(pageLabel);
        assertEquals("833572353213", pageLabel.getId());
        assertEquals("Important", pageLabel.getName());
        assertEquals(1449649413000L, pageLabel.getCreationTime().getTime());
        assertNotNull(pageLabel.getFrom());
        assertEquals(0, pageLabel.getUsers().size());
    }

    @Test
    public void checkWithUsers_V2_6() {
        PageLabel pageLabel = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/pagelabel-with-users"), PageLabel.class);
        assertNotNull(pageLabel);
        assertEquals("833572353213", pageLabel.getId());
        assertEquals("Important", pageLabel.getName());
        assertEquals(1449649413000L, pageLabel.getCreationTime().getTime());
        assertNotNull(pageLabel.getFrom());
        assertEquals(1, pageLabel.getUsers().size());
        assertNotNull(pageLabel.getUsers().get(0));
    }
}

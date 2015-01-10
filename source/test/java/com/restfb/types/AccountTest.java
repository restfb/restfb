/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

import com.restfb.AbstractJsonMapperTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AccountTest extends AbstractJsonMapperTests {
    
    @Test
    public void checkV1_0() {
        Account exampleAccount =
        createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/account"), Account.class);
        assertEquals("testtoken", exampleAccount.getAccessToken());
        assertEquals("123456789", exampleAccount.getId());
        assertEquals(6, exampleAccount.getPerms().size());
        assertTrue(exampleAccount.getPerms().contains("BASIC_ADMIN"));
    }
    
    @Test
    public void checkV2_0() {
        Account exampleAccount =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/account"), Account.class);
        assertEquals("testtoken", exampleAccount.getAccessToken());
        assertEquals("123456789", exampleAccount.getId());
        assertEquals(6, exampleAccount.getPerms().size());
        assertTrue(exampleAccount.getPerms().contains("BASIC_ADMIN"));
    }
    
    @Test
    public void checkV2_1() {
        Account exampleAccount =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/account"), Account.class);
        assertEquals("testtoken", exampleAccount.getAccessToken());
        assertEquals("123456789", exampleAccount.getId());
        assertEquals(6, exampleAccount.getPerms().size());
        assertTrue(exampleAccount.getPerms().contains("BASIC_ADMIN"));
    }
    
}

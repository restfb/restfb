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
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PageTest extends AbstractJsonMapperTests {
    
    @Test
    public void checkV1_0() {
        Page examplePage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/page"), Page.class);
        assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
        assertEquals(1,examplePage.getCategoryList().size());
        Category cat = examplePage.getCategoryList().get(0);
        assertEquals("Company",cat.getName());
        assertEquals("2200",cat.getId());
    }
    
    @Test
    public void checkV2_0() {
        Page examplePage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/page"), Page.class);
        assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
        assertEquals(1,examplePage.getCategoryList().size());
        Category cat = examplePage.getCategoryList().get(0);
        assertEquals("Company",cat.getName());
        assertEquals("2200",cat.getId());
    }
    
    @Test
    public void checkV2_1() {
        Page examplePage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/page"), Page.class);
        assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
        assertEquals(1,examplePage.getCategoryList().size());
        Category cat = examplePage.getCategoryList().get(0);
        assertEquals("Company",cat.getName());
        assertEquals("2200",cat.getId());
    }
    
    @Test
    public void checkV2_3_settings() {
	List<Page.Settings> pageSettingList = createJsonMapper().toJavaList(jsonFromClasspath("v2_3/page-settings"), Page.Settings.class);
	assertEquals(11, pageSettingList.size());
	for (Page.Settings pageSetting : pageSettingList) {
	    assertNotNull(pageSetting.getSetting());
	    assertNotNull(pageSetting.getValue());
	}
    }
    
    @Test
    public void checkV2_3_engagement() {
	Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-engagement"), Page.class);
	assertNotNull(page);
	assertEquals("1234567890", page.getId());
	assertNotNull(page.getEngagement());
	assertEquals(37, page.getEngagement().getCount().intValue());
	assertEquals("37 people like this.", page.getEngagement().getSocialSentence());
    }
    
    @Test
    public void checkV2_3_voipinfo() {
	Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-voipinfo"), Page.class);
	assertNotNull(page);
	assertEquals("1234567890", page.getId());
	assertNotNull(page.getVoipInfo());
	assertTrue(page.getVoipInfo().getHasPermission());
	assertTrue(page.getVoipInfo().getHasMobileApp());
	assertTrue(page.getVoipInfo().getIsPushable());
	assertTrue(page.getVoipInfo().getIsCallable());
	assertTrue(page.getVoipInfo().getIsCallableWebrtc());
	assertEquals(1356044, page.getVoipInfo().getReasonCode().intValue());
	assertEquals("This person can't be called right now.", page.getVoipInfo().getReasonDescription());
    }
    
}

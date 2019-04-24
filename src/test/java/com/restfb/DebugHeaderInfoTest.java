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
package com.restfb;

import static org.junit.Assert.*;

import org.junit.Test;

public class DebugHeaderInfoTest {

  @Test
  public void usageHeader_EmptyString() {
    DebugHeaderInfo headerInfo = buildHeader("", "", "");
    assertNotNull(headerInfo);
    assertNull(headerInfo.getAppUsage());
    assertNull(headerInfo.getPageUsage());
    assertNull(headerInfo.getAdAccountUsage());
  }

  @Test
  public void usageHeader_percentageString() {
    DebugHeaderInfo headerInfo = buildHeader("17%", "28%", "20%");
    assertNotNull(headerInfo);
    assertNotNull(headerInfo.getAppUsage());
    DebugHeaderInfo.HeaderUsage appUsage = headerInfo.getAppUsage();
    assertTrue(appUsage.isPercentageOnly());
    assertNotNull(appUsage.getPercentage());
    assertNotNull(headerInfo.getPageUsage());
    assertFalse(appUsage.isAdAccountHeader());
    DebugHeaderInfo.HeaderUsage pageUsage = headerInfo.getPageUsage();
    assertTrue(pageUsage.isPercentageOnly());
    assertNotNull(pageUsage.getPercentage());
    assertFalse(pageUsage.isAdAccountHeader());
    DebugHeaderInfo.HeaderUsage adAccountUsage = headerInfo.getAdAccountUsage();
    assertTrue(adAccountUsage.isPercentageOnly());
    assertNotNull(adAccountUsage.getPercentage());
    assertFalse(adAccountUsage.isAdAccountHeader());
  }

  @Test
  public void usageHeader_json() {
    DebugHeaderInfo headerInfo = buildHeader("{\"call_count\":144,\"total_cputime\":73,\"total_time\":44}",
      "{\"call_count\":144,\"total_cputime\":73,\"total_time\":44}", "{\"acc_id_util_pct\":9.67}");
    assertNotNull(headerInfo);
    assertNotNull(headerInfo.getAppUsage());
    DebugHeaderInfo.HeaderUsage appUsage = headerInfo.getAppUsage();
    assertFalse(appUsage.isPercentageOnly());
    assertNull(appUsage.getPercentage());
    assertNotNull(appUsage.getCallCount());
    assertNotNull(appUsage.getTotalCputime());
    assertNotNull(appUsage.getTotalTime());
    assertNotNull(headerInfo.getPageUsage());
    assertFalse(appUsage.isAdAccountHeader());
    DebugHeaderInfo.HeaderUsage pageUsage = headerInfo.getPageUsage();
    assertFalse(pageUsage.isPercentageOnly());
    assertNull(pageUsage.getPercentage());
    assertNotNull(pageUsage.getCallCount());
    assertNotNull(pageUsage.getTotalCputime());
    assertNotNull(pageUsage.getTotalTime());
    assertNotNull(headerInfo.getPageUsage());
    assertFalse(pageUsage.isAdAccountHeader());
    DebugHeaderInfo.HeaderUsage adAccountUsage = headerInfo.getAdAccountUsage();
    assertFalse(adAccountUsage.isPercentageOnly());
    assertNull(adAccountUsage.getPercentage());
    assertTrue(adAccountUsage.isAdAccountHeader());
    assertEquals(9.67, adAccountUsage.getAccIdUtilPct(), 0.01);
  }

  private DebugHeaderInfo buildHeader(String appUsage, String pageUsage, String adAccountUsage) {
    String debug = "QeodrDSB0AN6qqY1eWNkPlsB93xSMEWg80qsyfUAMqzlB0AOvmMR6mypF0HHkoSiGP8k54AHwDrn5aQfMLvZrg";
    String rev = "3057133";
    String traceId = "HiH1euz4Umo";

    return DebugHeaderInfo.DebugHeaderInfoFactory.create() //
      .setDebug(debug) //
      .setRev(rev) //
      .setTraceId(traceId) //
      .setVersion(Version.LATEST) //
      .setAppUsage(appUsage) //
      .setPageUsage(pageUsage) //
      .setAdAccountUsage(adAccountUsage) //
      .build();

  }
}

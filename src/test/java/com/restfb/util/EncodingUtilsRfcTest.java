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
package com.restfb.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EncodingUtilsRfcTest {

  private String base64EncodedString;

  private String decodedString;

  @Parameterized.Parameters
  public static Collection testData() {
    return Arrays.asList(new Object[][] { //
        { "", "" }, //
        { "Zg==", "f" }, //
        { "Zg", "f" }, //
        { "Zg=", "f" }, //
        { "Zm8=", "fo" }, //
        { "Zm9v", "foo" }, //
        { "Zm9vYg==", "foob" }, //
        { "Zm9vYmE=", "fooba" }, //
        { "Zm9vYmFy", "foobar" } });
  }

  public EncodingUtilsRfcTest(String encodedString, String expectedString) {
    base64EncodedString = encodedString;
    decodedString = expectedString;
  }

  @Test
  public void base64Decode() {
    byte[] decoded = EncodingUtils.decodeBase64(base64EncodedString);
    assertThat(decoded).isEqualTo(decodedString.getBytes());
  }

}

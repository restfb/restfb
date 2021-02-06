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
package com.restfb.integration.base;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

abstract public class RestFbImageIntegrationTestBase extends RestFbIntegrationTestBase {

  String[] testImages = { "fruits.png", "lena.png", "tulips.png", "watch.png" };

  protected byte[] fetchBytesFromImage() {
    int idx = new Random().nextInt(testImages.length);
    String randomImage = (testImages[idx]);
    InputStream is = getClass().getResourceAsStream("/binary/" + randomImage);
    assertNotNull(is);
    return getBytesFromInputStream(is);
  }

  protected byte[] getBytesFromInputStream(InputStream is) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      byte[] buffer = new byte[65535];
      for (int len; (len = is.read(buffer)) != -1;) {
        os.write(buffer, 0, len);
      }
      os.flush();
      return os.toByteArray();
    } catch (IOException e) {
      return null;
    }
  }

}

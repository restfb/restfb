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
package com.restfb.logging;

import static junit.framework.TestCase.*;

import org.junit.Test;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;

public class JulMessageTest {

  @Test
  public void simpleMessage_escapedPlaceholder_noException() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("Simple\\{} text");
    assertEquals(tuple.getMessage(), "Simple\\{} text");
    assertNull(tuple.getThrowable());
  }

  @Test
  public void simpleMessage_escapedPlaceholderAtBeginning_noException() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("\\{}Simple text");
    assertEquals(tuple.getMessage(), "\\{}Simple text");
    assertNull(tuple.getThrowable());
  }

  @Test
  public void simpleMessage_noPlaceholder_withException() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("Simple text", new NullPointerException());
    assertEquals(tuple.getMessage(), "Simple text");
    assertNotNull(tuple.getThrowable());
  }

  @Test
  public void simpleMessage_with2Placeholder_noException() {
    String convertedText = JulMessage.convertMessageString("Simple{} text {}", 2, 3).getMessage();
    assertEquals("Simple2 text 3", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholderOnlyBetween_noException() {
    String convertedText = JulMessage.convertMessageString("Simple{} {} text", 2, 3).getMessage();
    assertEquals("Simple2 3 text", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholderAtBeginning_noException() {
    String convertedText = JulMessage.convertMessageString("{}Simple text {}", 2, 3).getMessage();
    assertEquals("2Simple text 3", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholder_withException() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("{}Simple text {}", 2,3, new NullPointerException());
    assertEquals("2Simple text 3", tuple.getMessage());
    assertNotNull(tuple.getThrowable());
  }

  @Test
  public void simpleMessage_withPlaceholder_Object() {
    Object testObj = new Object() {
      @Override
      public String toString() {
        return "'testobject'";
      }
    };
    String convertedText = JulMessage.convertMessageString("Simple object {}", testObj).getMessage();
    assertEquals("Simple object 'testobject'", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholder_long() {
    String convertedText = JulMessage.convertMessageString("Simple long {}", 3l).getMessage();
    assertEquals("Simple long 3", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholder_byte() {
    byte b = 100;
    String convertedText = JulMessage.convertMessageString("Simple byte {}", b).getMessage();
    assertEquals("Simple byte 100", convertedText);
  }

  @Test
  public void simpleMessage_withPlaceholder_BigDecimal() {
    BigDecimal bd = new BigDecimal(100);
    String convertedText = JulMessage.convertMessageString("Simple BigDecimal {}", bd).getMessage();
    assertEquals("Simple BigDecimal 100", convertedText);
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleMessage_withPlaceholder_argumentCountMismatch() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("{}Simple text {}", 2);
    assertEquals("2Simple text 3", tuple.getMessage());
    assertNotNull(tuple.getThrowable());
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleMessage_withPlaceholder_argumentCountMismatch_withException() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("{}Simple text {}", 2, new NullPointerException());
    assertEquals("2Simple text 3", tuple.getMessage());
    assertNotNull(tuple.getThrowable());
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleMessage_withPlaceholder_argumentCountMismatch2() {
    JulMessage.MessageTuple tuple = JulMessage.convertMessageString("{}Simple text", 2, 3);
    assertEquals("2Simple text 3", tuple.getMessage());
    assertNotNull(tuple.getThrowable());
  }
}

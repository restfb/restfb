/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
package com.restfb.types.setter;

import com.restfb.types.User;
import com.restfb.types.api.SetterGetterTestBase;

import org.junit.jupiter.api.Test;

class UserTest extends SetterGetterTestBase {

  @Test
  void test() {
    User obj = new User();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    addIgnoredField("rawPicture");
    addIgnoredField("hometownAsString");
    addIgnoredField("rawSharedLoginUpgradeRequiredBy");
    testInstance(obj);
  }

  @Test
  void testCurrency() {
    User.Currency obj = new User.Currency();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  void testSport() {
    User.Sport obj = new User.Sport();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

}

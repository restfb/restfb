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
package com.restfb.types.setter;

import com.restfb.types.User;
import com.restfb.types.api.SetterGetterTestBase;
import org.junit.Test;

public class UserTest extends SetterGetterTestBase {

  @Test
  public void test() {
    User obj = new User();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    addIgnoredField("rawPicture");
    addIgnoredField("hometownAsString");
    testInstance(obj);
  }

  @Test
  public void testCurrency() {
    User.Currency obj = new User.Currency();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  public void testEducation() {
    User.Education obj = new User.Education();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  public void testEducationClass() {
    User.EducationClass obj = new User.EducationClass();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  public void testPicture() {
    User.Picture obj = new User.Picture();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  public void testSport() {
    User.Sport obj = new User.Sport();
    addIgnoredField("rawUpdatedTime");
    addIgnoredField("rawCreatedTime");
    testInstance(obj);
  }

  @Test
  public void testWork() {
    User.Work obj = new User.Work();
    addIgnoredField("rawStartDate");
    addIgnoredField("rawEndDate");
    testInstance(obj);
  }

}

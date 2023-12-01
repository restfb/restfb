/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.AbstractJsonMapperTests;

class AccountTest extends AbstractJsonMapperTests {

  @DisplayName("check Account with json from version")
  @ParameterizedTest(name = "{index} ==> version ''{0}''")
  @MethodSource("jsonSupplier")
  void check(String version) {
    Account exampleAccount = createJsonMapper().toJavaObject(jsonFromClasspath(version + "/account"), Account.class);
    assertEquals("123123123123", exampleAccount.getId());
    assertNotNull(exampleAccount.getOwnerBusiness());
    assertEquals("123123123123", exampleAccount.getOwnerBusiness().getId());
    assertEquals("Test Company", exampleAccount.getOwnerBusiness().getName());
  }

  private static Stream<String> jsonSupplier() {
    return Stream.of("v10_0");
  }

}

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
package com.restfb;

import static org.assertj.core.api.Assertions.assertThat;

import com.restfb.types.FacebookType;
import com.restfb.types.User;

import org.junit.Test;

import java.util.List;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperErrorSwallowingTest extends AbstractJsonMapperTests {

  @Test(expected = IllegalArgumentException.class)
  public void NullJsonMappingErrorHandler() {
    new DefaultJsonMapper(null);
  }

  /**
   * Does the mapper return null for empty strings?
   */
  @Test
  public void emptyStrings() {
    assertThat(createErrorSwallowingJsonMapper().toJavaObject("", Integer.class)).isNull();
    assertThat(createErrorSwallowingJsonMapper().toJavaObject("", String.class)).isNull();
    assertThat(createErrorSwallowingJsonMapper().toJavaList("", String.class)).isNull();
  }

  /**
   * Does the mapper return null for only those fields that are mis-mapped?
   */
  @Test
  public void objectWithIncorrectFields() {
    MostlyIncorrectUser mostlyIncorrectUser = createErrorSwallowingJsonMapper()
      .toJavaObject(jsonFromClasspath("user-with-photos"), MostlyIncorrectUser.class);

    assertThat(mostlyIncorrectUser.uid).isNull();
    assertThat(mostlyIncorrectUser.name).isNull();
    assertThat(mostlyIncorrectUser.photos.get(0).getId()).isEqualTo("1");
  }

  /**
   * Does the mapper return null for only those list elements that are mis-mapped?
   */
  @Test
  public void listWithIncorrectObject() {
    List<User> users =
        createErrorSwallowingJsonMapper().toJavaList(jsonFromClasspath("incorrect-user-list"), User.class);

    assertThat(users.get(0).getId()).isEqualTo("123");
    assertThat(users.get(1)).isNull();
    assertThat(users.get(2).getId()).isEqualTo("456");
    assertThat(users).hasSize(3);
  }

  static class MostlyIncorrectUser {
    // Incorrect mapping
    @Facebook
    Boolean uid;

    // Incorrect mapping
    @Facebook
    Double name;

    // Correct mapping
    @Facebook
    List<FacebookType> photos;
  }
}
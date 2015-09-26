/*******************************************************************************
 * Copyright (c) 2013, 2015 EclipseSource.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.restfb.json;

import static com.restfb.json.TestUtil.assertException;
import static org.junit.Assert.*;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;


public class JsonParser_Test {

  @Test
  public void parse_rejectsEmptyString() {
    assertParseException(0, "Unexpected end of input", "");
  }

  @Test
  public void parse_rejectsEmptyReader() {
    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        try {
          new JsonParser(new StringReader("")).parse();
        } catch (IOException exception) {
          throw new RuntimeException(exception);
        }
      }
    });

    assertEquals(0, exception.getOffset());
    assertThat(exception.getMessage(), StringStartsWith.startsWith("Unexpected end of input at"));
  }

  @Test
  public void parse_acceptsArrays() {
    assertEquals(new JsonArray(), parse("[]"));
  }

  @Test
  public void parse_acceptsObjects() {
    assertEquals(new JsonObject(), parse("{}"));
  }

  @Test
  public void parse_acceptsStrings() {
    assertEquals(new JsonString(""), parse("\"\""));
  }

  @Test
  public void parse_acceptsLiterals() {
    assertSame(Json.NULL, parse("null"));
  }

  @Test
  public void parse_stripsPadding() {
    assertEquals(new JsonArray(), parse(" [ ] "));
  }

  @Test
  public void parse_ignoresAllWhiteSpace() {
    assertEquals(new JsonArray(), parse("\t\r\n [\t\r\n ]\t\r\n "));
  }

  @Test
  public void parse_failsWithUnterminatedString() {
    assertParseException(5, "Unexpected end of input", "[\"foo");
  }

  @Test
  public void parse_handlesLineBreaksAndColumnsCorrectly() {
    assertParseException(0, 1, 0, "!");
    assertParseException(2, 2, 0, "[\n!");
    assertParseException(3, 2, 0, "[\r\n!");
    assertParseException(6, 3, 1, "[ \n \n !");
    assertParseException(7, 2, 3, "[ \r\n \r !");
  }

  @Test
  public void parse_handlesInputsThatExceedBufferSize() throws IOException {
    String input = "[ 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47 ]";

    JsonValue value = new JsonParser(new StringReader(input), 3).parse();

    assertEquals("[2,3,5,7,11,13,17,19,23,29,31,37,41,43,47]", value.toString());
  }

  @Test
  public void parse_handlesStringsThatExceedBufferSize() throws IOException {
    String input = "[ \"lorem ipsum dolor sit amet\" ]";

    JsonValue value = new JsonParser(new StringReader(input), 3).parse();

    assertEquals("[\"lorem ipsum dolor sit amet\"]", value.toString());
  }

  @Test
  public void parse_handlesNumbersThatExceedBufferSize() throws IOException {
    String input = "[ 3.141592653589 ]";

    JsonValue value = new JsonParser(new StringReader(input), 3).parse();

    assertEquals("[3.141592653589]", value.toString());
  }

  @Test
  public void parse_handlesPositionsCorrectlyWhenInputExceedsBufferSize() {
    final String input = "{\n  \"a\": 23,\n  \"b\": 42,\n}";

    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        try {
          new JsonParser(new StringReader(input), 3).parse();
        } catch (IOException e) {
        }
      }
    });

    assertEquals(4, exception.getLine());
    assertEquals(0, exception.getColumn());
    assertEquals(24, exception.getOffset());
  }

  @Test
  public void arrays_empty() {
    assertEquals("[]", parse("[]").toString());
  }

  @Test
  public void arrays_singleValue() {
    assertEquals("[23]", parse("[23]").toString());
  }

  @Test
  public void arrays_multipleValues() {
    assertEquals("[23,42]", parse("[23,42]").toString());
  }

  @Test
  public void arrays_withWhitespaces() {
    assertEquals("[23,42]", parse("[ 23 , 42 ]").toString());
  }

  @Test
  public void arrays_nested() {
    assertEquals("[[23],42]", parse("[[23],42]").toString());
  }

  @Test
  public void arrays_illegalSyntax() {
    assertParseException(1, "Expected value", "[,]");
    assertParseException(4, "Expected ',' or ']'", "[23 42]");
    assertParseException(4, "Expected value", "[23,]");
  }

  @Test
  public void arrays_incomplete() {
    assertParseException(1, "Unexpected end of input", "[");
    assertParseException(2, "Unexpected end of input", "[ ");
    assertParseException(3, "Unexpected end of input", "[23");
    assertParseException(4, "Unexpected end of input", "[23 ");
    assertParseException(4, "Unexpected end of input", "[23,");
    assertParseException(5, "Unexpected end of input", "[23, ");
  }

  @Test
  public void objects_empty() {
    assertEquals("{}", parse("{}").toString());
  }

  @Test
  public void objects_singleValue() {
    assertEquals("{\"foo\":23}", parse("{\"foo\":23}").toString());
  }

  @Test
  public void objects_multipleValues() {
    assertEquals("{\"foo\":23,\"bar\":42}", parse("{\"foo\":23,\"bar\":42}").toString());
  }

  @Test
  public void objects_whitespace() {
    assertEquals("{\"foo\":23,\"bar\":42}", parse("{ \"foo\" : 23, \"bar\" : 42 }").toString());
  }

  @Test
  public void objects_nested() {
    assertEquals("{\"foo\":{\"bar\":42}}", parse("{\"foo\":{\"bar\":42}}").toString());
  }

  @Test
  public void objects_illegalSyntax() {
    assertParseException(1, "Expected name", "{,}");
    assertParseException(1, "Expected name", "{:}");
    assertParseException(1, "Expected name", "{23}");
    assertParseException(4, "Expected ':'", "{\"a\"}");
    assertParseException(5, "Expected ':'", "{\"a\" \"b\"}");
    assertParseException(5, "Expected value", "{\"a\":}");
    assertParseException(8, "Expected name", "{\"a\":23,}");
    assertParseException(8, "Expected name", "{\"a\":23,42");
  }

  @Test
  public void objects_incomplete() {
    assertParseException(1, "Unexpected end of input", "{");
    assertParseException(2, "Unexpected end of input", "{ ");
    assertParseException(2, "Unexpected end of input", "{\"");
    assertParseException(4, "Unexpected end of input", "{\"a\"");
    assertParseException(5, "Unexpected end of input", "{\"a\" ");
    assertParseException(5, "Unexpected end of input", "{\"a\":");
    assertParseException(6, "Unexpected end of input", "{\"a\": ");
    assertParseException(7, "Unexpected end of input", "{\"a\":23");
    assertParseException(8, "Unexpected end of input", "{\"a\":23 ");
    assertParseException(8, "Unexpected end of input", "{\"a\":23,");
    assertParseException(9, "Unexpected end of input", "{\"a\":23, ");
  }

  @Test
  public void strings_emptyString_isAccepted() {
    assertEquals("", parse("\"\"").asString());
  }

  @Test
  public void strings_asciiCharacters_areAccepted() {
    assertEquals(" ", parse("\" \"").asString());
    assertEquals("a", parse("\"a\"").asString());
    assertEquals("foo", parse("\"foo\"").asString());
    assertEquals("A2-D2", parse("\"A2-D2\"").asString());
    assertEquals("\u007f", parse("\"\u007f\"").asString());
  }

  @Test
  public void strings_nonAsciiCharacters_areAccepted() {
    assertEquals("Русский", parse("\"Русский\"").asString());
    assertEquals("العربية", parse("\"العربية\"").asString());
    assertEquals("日本語", parse("\"日本語\"").asString());
  }

  @Test
  public void strings_controlCharacters_areRejected() {
    // JSON string must not contain characters < 0x20
    assertParseException(3, "Expected valid string character", "\"--\n--\"");
    assertParseException(3, "Expected valid string character", "\"--\r\n--\"");
    assertParseException(3, "Expected valid string character", "\"--\t--\"");
    assertParseException(3, "Expected valid string character", "\"--\u0000--\"");
    assertParseException(3, "Expected valid string character", "\"--\u001f--\"");
  }

  @Test
  public void strings_validEscapes_areAccepted() {
    // valid escapes are \" \\ \/ \b \f \n \r \t and unicode escapes
    assertEquals(" \" ", parse("\" \\\" \"").asString());
    assertEquals(" \\ ", parse("\" \\\\ \"").asString());
    assertEquals(" / ", parse("\" \\/ \"").asString());
    assertEquals(" \u0008 ", parse("\" \\b \"").asString());
    assertEquals(" \u000c ", parse("\" \\f \"").asString());
    assertEquals(" \r ", parse("\" \\r \"").asString());
    assertEquals(" \n ", parse("\" \\n \"").asString());
    assertEquals(" \t ", parse("\" \\t \"").asString());
  }

  @Test
  public void strings_escape_atStart() {
    assertEquals("\\x", parse("\"\\\\x\"").asString());
  }

  @Test
  public void strings_escape_atEnd() {
    assertEquals("x\\", parse("\"x\\\\\"").asString());
  }

  @Test
  public void strings_illegalEscapes_areRejected() {
    assertParseException(2, "Expected valid escape sequence", "\"\\a\"");
    assertParseException(2, "Expected valid escape sequence", "\"\\x\"");
    assertParseException(2, "Expected valid escape sequence", "\"\\000\"");
  }

  @Test
  public void strings_validUnicodeEscapes_areAccepted() {
    assertEquals("\u0021", parse("\"\\u0021\"").asString());
    assertEquals("\u4711", parse("\"\\u4711\"").asString());
    assertEquals("\uffff", parse("\"\\uffff\"").asString());
    assertEquals("\uabcdx", parse("\"\\uabcdx\"").asString());
  }

  @Test
  public void strings_illegalUnicodeEscapes_areRejected() {
    assertParseException(3, "Expected hexadecimal digit", "\"\\u \"");
    assertParseException(3, "Expected hexadecimal digit", "\"\\ux\"");
    assertParseException(5, "Expected hexadecimal digit", "\"\\u20 \"");
    assertParseException(6, "Expected hexadecimal digit", "\"\\u000x\"");
  }

  @Test
  public void strings_incompleteStrings_areRejected() {
    assertParseException(1, "Unexpected end of input", "\"");
    assertParseException(4, "Unexpected end of input", "\"foo");
    assertParseException(5, "Unexpected end of input", "\"foo\\");
    assertParseException(6, "Unexpected end of input", "\"foo\\n");
    assertParseException(6, "Unexpected end of input", "\"foo\\u");
    assertParseException(7, "Unexpected end of input", "\"foo\\u0");
    assertParseException(9, "Unexpected end of input", "\"foo\\u000");
    assertParseException(10, "Unexpected end of input", "\"foo\\u0000");
  }

  @Test
  public void numbers_integer() {
    assertEquals(new JsonNumber("0"), parse("0"));
    assertEquals(new JsonNumber("-0"), parse("-0"));
    assertEquals(new JsonNumber("1"), parse("1"));
    assertEquals(new JsonNumber("-1"), parse("-1"));
    assertEquals(new JsonNumber("23"), parse("23"));
    assertEquals(new JsonNumber("-23"), parse("-23"));
    assertEquals(new JsonNumber("1234567890"), parse("1234567890"));
    assertEquals(new JsonNumber("123456789012345678901234567890"),
                 parse("123456789012345678901234567890"));
  }

  @Test
  public void numbers_minusZero() {
    // allowed by JSON, allowed by Java
    JsonValue value = parse("-0");

    assertEquals(0, value.asInt());
    assertEquals(0l, value.asLong());
    assertEquals(0f, value.asFloat(), 0);
    assertEquals(0d, value.asDouble(), 0);
  }

  @Test
  public void numbers_decimal() {
    assertEquals(new JsonNumber("0.23"), parse("0.23"));
    assertEquals(new JsonNumber("-0.23"), parse("-0.23"));
    assertEquals(new JsonNumber("1234567890.12345678901234567890"),
                 parse("1234567890.12345678901234567890"));
  }

  @Test
  public void numbers_withExponent() {
    assertEquals(new JsonNumber("0.1e9"), parse("0.1e9"));
    assertEquals(new JsonNumber("0.1e9"), parse("0.1e9"));
    assertEquals(new JsonNumber("0.1E9"), parse("0.1E9"));
    assertEquals(new JsonNumber("-0.23e9"), parse("-0.23e9"));
    assertEquals(new JsonNumber("0.23e9"), parse("0.23e9"));
    assertEquals(new JsonNumber("0.23e+9"), parse("0.23e+9"));
    assertEquals(new JsonNumber("0.23e-9"), parse("0.23e-9"));
  }

  @Test
  public void numbers_withInvalidFormat() {
    assertParseException(0, "Expected value", "+1");
    assertParseException(0, "Expected value", ".1");
    assertParseException(1, "Unexpected character", "02");
    assertParseException(2, "Unexpected character", "-02");
    assertParseException(1, "Expected digit", "-x");
    assertParseException(2, "Expected digit", "1.x");
    assertParseException(2, "Expected digit", "1ex");
    assertParseException(3, "Unexpected character", "1e1x");
  }

  @Test
  public void numbers_incomplete() {
    assertParseException(1, "Unexpected end of input", "-");
    assertParseException(2, "Unexpected end of input", "1.");
    assertParseException(4, "Unexpected end of input", "1.0e");
    assertParseException(5, "Unexpected end of input", "1.0e-");
  }

  @Test
  public void null_complete() {
    assertEquals(Json.NULL, parse("null"));
  }

  @Test
  public void null_incomplete() {
    assertParseException(1, "Unexpected end of input", "n");
    assertParseException(2, "Unexpected end of input", "nu");
    assertParseException(3, "Unexpected end of input", "nul");
  }

  @Test
  public void null_withIllegalCharacter() {
    assertParseException(1, "Expected 'u'", "nx");
    assertParseException(2, "Expected 'l'", "nux");
    assertParseException(3, "Expected 'l'", "nulx");
    assertParseException(4, "Unexpected character", "nullx");
  }

  @Test
  public void true_complete() {
    assertSame(Json.TRUE, parse("true"));
  }

  @Test
  public void true_incomplete() {
    assertParseException(1, "Unexpected end of input", "t");
    assertParseException(2, "Unexpected end of input", "tr");
    assertParseException(3, "Unexpected end of input", "tru");
  }

  @Test
  public void true_withIllegalCharacter() {
    assertParseException(1, "Expected 'r'", "tx");
    assertParseException(2, "Expected 'u'", "trx");
    assertParseException(3, "Expected 'e'", "trux");
    assertParseException(4, "Unexpected character", "truex");
  }

  @Test
  public void false_complete() {
    assertSame(Json.FALSE, parse("false"));
  }

  @Test
  public void false_incomplete() {
    assertParseException(1, "Unexpected end of input", "f");
    assertParseException(2, "Unexpected end of input", "fa");
    assertParseException(3, "Unexpected end of input", "fal");
    assertParseException(4, "Unexpected end of input", "fals");
  }

  @Test
  public void false_withIllegalCharacter() {
    assertParseException(1, "Expected 'a'", "fx");
    assertParseException(2, "Expected 'l'", "fax");
    assertParseException(3, "Expected 's'", "falx");
    assertParseException(4, "Expected 'e'", "falsx");
    assertParseException(5, "Unexpected character", "falsex");
  }

  private static void assertParseException(int offset, String message, final String json) {
    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        parse(json);
      }
    });
    assertEquals(offset, exception.getOffset());
    assertThat(exception.getMessage(), StringStartsWith.startsWith(message + " at"));
  }

  private static void assertParseException(int offset, int line, int column, final String json) {
    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        parse(json);
      }
    });
    assertEquals("offset", offset, exception.getOffset());
    assertEquals("line", line, exception.getLine());
    assertEquals("column", column, exception.getColumn());
  }

  private static JsonValue parse(String json) {
    try {
      return new JsonParser(json).parse();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}

package com.restfb.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/*
 Copyright (c) 2002 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

/**
 * A JsonTokener takes a source string and extracts characters and tokens from it. It is used by the JsonObject and
 * JsonArray constructors to parse JSON source strings.
 * 
 * @author JSON.org
 * @version 2008-09-18
 */
public class JsonTokener {

  private int index;
  private Reader reader;
  private char lastChar;
  private boolean useLastChar;

  /**
   * Construct a JsonTokener from a string.
   * 
   * @param reader
   *          A reader.
   */
  public JsonTokener(Reader reader) {
    this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
    this.useLastChar = false;
    this.index = 0;
  }

  /**
   * Construct a JsonTokener from a string.
   * 
   * @param s
   *          A source string.
   */
  public JsonTokener(String s) {
    this(new StringReader(s));
  }

  /**
   * Back up one character. This provides a sort of lookahead capability, so that you can test for a digit or letter
   * before attempting to parse the next number or identifier.
   */
  public void back() {
    if (useLastChar || index <= 0) {
      throw new JsonException("Stepping back two steps is not supported");
    }
    index -= 1;
    useLastChar = true;
  }

  /**
   * Get the hex value of a character (base16).
   * 
   * @param c
   *          A character between '0' and '9' or between 'A' and 'F' or between 'a' and 'f'.
   * @return An int between 0 and 15, or -1 if c was not a hex digit.
   */
  public static int dehexchar(char c) {
    if (c >= '0' && c <= '9') {
      return c - '0';
    }
    if (c >= 'A' && c <= 'F') {
      return c - ('A' - 10);
    }
    if (c >= 'a' && c <= 'f') {
      return c - ('a' - 10);
    }
    return -1;
  }

  /**
   * Determine if the source string still contains characters that next() can consume.
   * 
   * @return true if not yet at the end of the source.
   */
  public boolean more() {
    char nextChar = next();
    if (nextChar == 0) {
      return false;
    }
    back();
    return true;
  }

  /**
   * Get the next character in the source string.
   * 
   * @return The next character, or 0 if past the end of the source string.
   */
  public char next() {
    if (this.useLastChar) {
      this.useLastChar = false;
      if (this.lastChar != 0) {
        this.index += 1;
      }
      return this.lastChar;
    }
    int c;
    try {
      c = this.reader.read();
    } catch (IOException exc) {
      throw new JsonException(exc);
    }

    if (c <= 0) { // End of stream
      this.lastChar = 0;
      return 0;
    }
    this.index += 1;
    this.lastChar = (char) c;
    return this.lastChar;
  }

  /**
   * Consume the next character, and check that it matches a specified character.
   * 
   * @param c
   *          The character to match.
   * @return The character.
   * @throws JsonException
   *           if the character does not match.
   */
  public char next(char c) {
    char n = next();
    if (n != c) {
      throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
    }
    return n;
  }

  /**
   * Get the next n characters.
   * 
   * @param n
   *          The number of characters to take.
   * @return A string of n characters.
   * @throws JsonException
   *           Substring bounds error if there are not n characters remaining in the source string.
   */
  public String next(int n) {
    if (n == 0) {
      return "";
    }

    char[] buffer = new char[n];
    int pos = 0;

    if (this.useLastChar) {
      this.useLastChar = false;
      buffer[0] = this.lastChar;
      pos = 1;
    }

    try {
      int len;
      while ((pos < n) && ((len = reader.read(buffer, pos, n - pos)) != -1)) {
        pos += len;
      }
    } catch (IOException exc) {
      throw new JsonException(exc);
    }
    this.index += pos;

    if (pos < n) {
      throw syntaxError("Substring bounds error");
    }

    this.lastChar = buffer[n - 1];
    return new String(buffer);
  }

  /**
   * Get the next char in the string, skipping whitespace.
   * 
   * @throws JsonException
   * @return A character, or 0 if there are no more characters.
   */
  public char nextClean() {
    for (;;) {
      char c = next();
      if (c == 0 || c > ' ') {
        return c;
      }
    }
  }

  /**
   * Return the characters up to the next close quote character. Backslash processing is done. The formal JSON format
   * does not allow strings in single quotes, but an implementation is allowed to accept them.
   * 
   * @param quote
   *          The quoting character, either <code>"</code>&nbsp;<small>(double quote)</small> or <code>'</code>
   *          &nbsp;<small>(single quote)</small>.
   * @return A String.
   * @throws JsonException
   *           Unterminated string.
   */
  public String nextString(char quote) {
    char c;
    StringBuilder sb = new StringBuilder();
    for (;;) {
      c = next();
      switch (c) {
      case 0:
      case '\n':
      case '\r':
        throw syntaxError("Unterminated string");
      case '\\':
        c = next();
        switch (c) {
        case 'b':
          sb.append('\b');
          break;
        case 't':
          sb.append('\t');
          break;
        case 'n':
          sb.append('\n');
          break;
        case 'f':
          sb.append('\f');
          break;
        case 'r':
          sb.append('\r');
          break;
        case 'u':
          sb.append((char) Integer.parseInt(next(4), 16));
          break;
        case '"':
        case '\'':
        case '\\':
        case '/':
          sb.append(c);
          break;
        default:
          throw syntaxError("Illegal escape.");
        }
        break;
      default:
        if (c == quote) {
          return sb.toString();
        }
        sb.append(c);
      }
    }
  }

  /**
   * Get the text up but not including the specified character or the end of line, whichever comes first.
   * 
   * @param d
   *          A delimiter character.
   * @return A string.
   */
  public String nextTo(char d) {
    StringBuilder sb = new StringBuilder();
    for (;;) {
      char c = next();
      if (c == d || c == 0 || c == '\n' || c == '\r') {
        if (c != 0) {
          back();
        }
        return sb.toString().trim();
      }
      sb.append(c);
    }
  }

  /**
   * Get the text up but not including one of the specified delimiter characters or the end of line, whichever comes
   * first.
   * 
   * @param delimiters
   *          A set of delimiter characters.
   * @return A string, trimmed.
   */
  public String nextTo(String delimiters) {
    char c;
    StringBuilder sb = new StringBuilder();
    for (;;) {
      c = next();
      if (delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r') {
        if (c != 0) {
          back();
        }
        return sb.toString().trim();
      }
      sb.append(c);
    }
  }

  /**
   * Get the next value. The value can be a Boolean, Double, Integer, JsonArray, JsonObject, Long, or String, or the
   * JsonObject.NULL object.
   * 
   * @throws JsonException
   *           If syntax error.
   * 
   * @return An object.
   */
  public Object nextValue() {
    char c = nextClean();
    String s;

    switch (c) {
    case '"':
    case '\'':
      return nextString(c);
    case '{':
      back();
      return new JsonObject(this);
    case '[':
    case '(':
      back();
      return new JsonArray(this);
    }

    /*
     * Handle unquoted text. This could be the values true, false, or null, or it can be a number. An implementation
     * (such as this one) is allowed to also accept non-standard forms.
     * 
     * Accumulate characters until we reach the end of the text or a formatting character.
     */

    StringBuilder sb = new StringBuilder();
    while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
      sb.append(c);
      c = next();
    }
    back();

    s = sb.toString().trim();
    if (s.equals("")) {
      throw syntaxError("Missing value");
    }
    return JsonObject.stringToValue(s);
  }

  /**
   * Skip characters until the next character is the requested character. If the requested character is not found, no
   * characters are skipped.
   * 
   * @param to
   *          A character to skip to.
   * @return The requested character, or zero if the requested character is not found.
   */
  public char skipTo(char to) {
    char c;
    try {
      int startIndex = this.index;
      reader.mark(Integer.MAX_VALUE);
      do {
        c = next();
        if (c == 0) {
          reader.reset();
          this.index = startIndex;
          return c;
        }
      } while (c != to);
    } catch (IOException exc) {
      throw new JsonException(exc);
    }

    back();
    return c;
  }

  /**
   * Make a JsonException to signal a syntax error.
   * 
   * @param message
   *          The error message.
   * @return A JsonException object, suitable for throwing
   */
  public JsonException syntaxError(String message) {
    return new JsonException(message + toString());
  }

  /**
   * Make a printable string of this JsonTokener.
   * 
   * @return " at character [this.index]"
   */
  public String toString() {
    return " at character " + index;
  }
}
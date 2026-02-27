package com.restfb.json;

import java.io.IOException;
import java.io.Writer;

public class UnicodeJsonWriter extends JsonWriter {

  private static final char[] QUOT_CHARS = { '\\', '"' };
  private static final char[] BS_CHARS = { '\\', '\\' };
  private static final char[] LF_CHARS = { '\\', 'n' };
  private static final char[] CR_CHARS = { '\\', 'r' };
  private static final char[] TAB_CHARS = { '\\', 't' };

  private static final char[] HEX_DIGITS =
      { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  UnicodeJsonWriter(Writer writer) {
    super(writer);
  }

  @Override
  protected void writeJsonString(String string) throws IOException {
    int length = string.length();
    int start = 0;
    for (int index = 0; index < length; index++) {
      char[] replacement = getReplacementChars(string.charAt(index));
      if (replacement != null) {
        writer.write(string, start, index - start);
        writer.write(replacement);
        start = index + 1;
      }
    }
    writer.write(string, start, length - start);
  }

  private static char[] getReplacementChars(char ch) {

    if (ch == '\\') {
      return BS_CHARS;
    }
    if (ch == '"') {
      return QUOT_CHARS;
    }
    if (ch == '\n') {
      return LF_CHARS;
    }
    if (ch == '\r') {
      return CR_CHARS;
    }
    if (ch == '\t') {
      return TAB_CHARS;
    }
    if (ch < 0x20 || ch > 0x7f) {
      return new char[] { '\\', 'u', HEX_DIGITS[ch >> 12 & 0x000f], HEX_DIGITS[ch >> 8 & 0x000f],
          HEX_DIGITS[ch >> 4 & 0x000f], HEX_DIGITS[ch & 0x000f] };
    } else {
      return null;
    }
  }
}

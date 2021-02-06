package com.restfb.json;

import java.io.Writer;

public class UnicodeJsonWriter extends JsonWriter {

  UnicodeJsonWriter(Writer writer) {
    super(writer);
  }

  @Override
  protected char[] getReplacementChars(char ch) {

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
      return NO_CHARS;
    }
  }
}

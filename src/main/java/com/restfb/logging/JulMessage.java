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
package com.restfb.logging;

/**
 * Java util logging message format helper class.
 * <p>
 * This class is used to allow JUL to use the <code>{}</code> placeholder in the log message. The placeholders are
 * replaced by the given arguments in the order of occurrence. The last object may be a {@code Throwable} and this don't
 * need any placeholder. The amount of placeholder in the message and the argument count must be equal, otherwise you
 * get an exception.
 */
final class JulMessage {

  private static final String PLACEHOLDER = "{}";

  private static final char ESCAPE_SIGN = '\\';

  private JulMessage() {
    throw new IllegalStateException("JulMessage is a utility class");
  }

  /**
   * convert the message and the arguments and convert everything in a {@code MessageTuple}. The {@code MessageTuple}
   * contains the message and the optional throwable.
   * 
   * @param messagePattern
   *          the message with optional placeholders
   * @param args
   *          the arguments for the placeholders and the optional Throwable
   * @return MessageTuple that contains the Throwable and the formatted message
   */
  static MessageTuple convertMessageString(String messagePattern, Object... args) {
    Throwable throwable = null;

    if (args.length > 0 && args[args.length - 1] instanceof Throwable) {
      throwable = (Throwable) args[args.length - 1];
    }

    if (!messagePattern.contains(PLACEHOLDER)) {
      return new MessageTuple(messagePattern, throwable);
    }

    StringBuilder sb = new StringBuilder();

    int placeholderCount = 0;
    int k = 0;
    while (messagePattern.indexOf(PLACEHOLDER, k) != -1) {
      int l = messagePattern.indexOf(PLACEHOLDER, k);
      if (l == 0 || messagePattern.charAt(l - 1) != ESCAPE_SIGN) {
        sb.append(messagePattern, k, l);
        sb.append("%s");
        k = l + 2;
        placeholderCount++;
      } else {
        sb.append(messagePattern, k, l + 3);
        k = l + 3;
      }
    }

    if (k < messagePattern.length()) {
      sb.append(messagePattern.substring(k));
    }

    int argsLength = (throwable != null) ? args.length - 1 : args.length;

    if (argsLength != placeholderCount) {
      throw new IllegalArgumentException("Placeholder count don't matches argument count (placeholders: "
          + placeholderCount + ", arguments: " + argsLength + ")");
    }

    Object[] trimmed = new Object[argsLength];
    System.arraycopy(args, 0, trimmed, 0, argsLength);

    return new MessageTuple(String.format(sb.toString(), trimmed), throwable);
  }

  static class MessageTuple {
    private final String message;

    private final Throwable throwable;

    MessageTuple(String message, Throwable throwable) {
      this.message = message;
      this.throwable = throwable;
    }

    public String getMessage() {
      return message;
    }

    public Throwable getThrowable() {
      return throwable;
    }
  }
}

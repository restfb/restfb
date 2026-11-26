/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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

/**
 * Controls credential exposure in request diagnostics. Does not change the HTTP request sent to the API.
 */
public enum Security {
  /**
   * Default. Redacts known authentication values from request logs, exception metadata and diagnostic request URLs.
   */
  HIGH,

  /**
   * Like {@link #HIGH}, but keeps nonempty {@code access_token} parameters without a pipe visible for manual replay.
   * Pipe-delimited app tokens, header tokens and app secret proofs remain redacted. Use only when diagnostic output is
   * suitably protected: the visible access tokens are credentials.
   */
  MEDIUM,

  /**
   * Restores legacy request diagnostics. Access tokens and other credentials can appear in clear text in logs, stack
   * traces, metadata getters and serialized objects. Use only when all diagnostic output is suitably protected.
   */
  LOW
}

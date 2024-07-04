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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger implementation based on {@code org.slf4j.Logger}.
 * <p>
 * The slf4j configuration should be provided by a external application. The mapping is defined like:
 * <ul>
 * <li>trace maps to slf4j.<i>trace</i></li>
 * <li>debug maps to slf4j.<i>debug</i></li>
 * <li>info maps to slf4j.<i>info</i></li>
 * <li>warn maps to slf4j.<i>warn</i></li>
 * <li>error maps to slf4j.<i>error</i></li>
 * <li>fatal maps to slf4j.<i>fatal</i></li>
 * </ul>
 */
public class SLF4JLogger extends RestFBLogger {

  private final Logger logger;

  public SLF4JLogger(String logName) {
    logger = LoggerFactory.getLogger(logName);
  }

  @Override
  public void trace(String msg, Object... args) {
    logger.trace(msg, args);
  }

  @Override
  public void debug(String msg, Object... args) {
    logger.debug(msg, args);
  }

  @Override
  public void info(String msg, Object... args) {
    logger.info(msg, args);
  }

  @Override
  public void warn(String msg, Object... args) {
    logger.warn(msg, args);
  }

  @Override
  public void error(String msg, Object... args) {
    logger.error(msg, args);
  }

  @Override
  public void fatal(String msg, Object... args) {
    logger.error(msg, args);
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }
}

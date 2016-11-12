/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Logger implementation based on {@code java.util.logging}.
 *
 * The JUL configuration should be provided by a external application. The mapping is defined like:
 * <ul>
 * <li>trace maps to java.util.logging.Level.<i>FINER</i></li>
 * <li>debug maps to java.util.logging.Level.<i>FINE</i></li>
 * <li>info maps to java.util.logging.Level.<i>INFO</i></li>
 * <li>warn maps to java.util.logging.Level.<i>WARNING</i></li>
 * <li>error maps to java.util.logging.Level.<i>SEVERE</i></li>
 * <li>fatal maps to java.util.logging.Level.<i>SEVERE</i></li>
 * </ul>
 */
public class JulLogger extends RestFBLogger {

  private final Logger logger;

  public JulLogger(String logName) {
    logger = Logger.getLogger(logName);
  }

  @Override
  public void trace(Object msg) {
    log(Level.FINER, msg, null);
  }

  @Override
  public void trace(Object msg, Throwable thr) {
    log(Level.FINER, msg, thr);
  }

  @Override
  public void debug(Object msg) {
    log(Level.FINE, msg, null);
  }

  @Override
  public void debug(Object msg, Throwable thr) {
    log(Level.FINE, msg, thr);
  }

  @Override
  public void info(Object msg) {
    log(Level.INFO, msg, null);
  }

  @Override
  public void info(Object msg, Throwable thr) {
    log(Level.INFO, msg, thr);
  }

  @Override
  public void warn(Object msg) {
    log(Level.WARNING, msg, null);
  }

  @Override
  public void warn(Object msg, Throwable thr) {
    log(Level.WARNING, msg, thr);
  }

  @Override
  public void error(Object msg) {
    log(Level.SEVERE, msg, null);
  }

  @Override
  public void error(Object msg, Throwable thr) {
    log(Level.SEVERE, msg, thr);
  }

  @Override
  public void fatal(Object msg) {
    log(Level.SEVERE, msg, null);
  }

  @Override
  public void fatal(Object msg, Throwable thr) {
    log(Level.SEVERE, msg, thr);
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isLoggable(Level.FINE);
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isLoggable(java.util.logging.Level.INFO);
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isLoggable(Level.FINER);
  }

  private void log(Level level, Object msg, Throwable thrown) {
    if (logger.isLoggable(level)) {
      LogRecord logRecord = new LogRecord(level, String.valueOf(msg));
      if (thrown != null) {
        logRecord.setThrown(thrown);
      }
      StackTraceElement[] stacktrace = new Throwable().getStackTrace();
      for (StackTraceElement element : stacktrace) {
        if (!element.getClassName().equals(JulLogger.class.getName())) {
          logRecord.setSourceClassName(element.getClassName());
          logRecord.setSourceMethodName(element.getMethodName());
          break;
        }
      }
      logRecord.setLoggerName(logger.getName());
      logger.log(logRecord);
    }
  }

}

/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import com.restfb.exception.FacebookLoggerException;

import java.lang.reflect.Constructor;

/**
 *
 */
public abstract class RestFBLogger {

  private static Class LOGGER_CLASS = null;

  public static final RestFBLogger HTTP_LOGGER;

  public static final RestFBLogger MAPPER_LOGGER;

  public static final RestFBLogger UTILS_LOGGER;

  public static final RestFBLogger CLIENT_LOGGER;

  public static final RestFBLogger VALUE_FACTORY_LOGGER;

  static {

    Class loggerClass = null;

    String forceJUL = System.getProperty("com.restfb.forceJUL", "false");
    if (!Boolean.parseBoolean(forceJUL)) {
      try {
        RestFBLogger.class.getClassLoader().loadClass("org.slf4j.Logger");
        loggerClass = com.restfb.logging.SLF4JLogger.class;
      } catch (Exception e) {
        loggerClass = com.restfb.logging.JulLogger.class;
      }
    } else {
      loggerClass = com.restfb.logging.JulLogger.class;
    }

    // define our logger class
    LOGGER_CLASS = loggerClass;

    // create the loggers
    HTTP_LOGGER = getLoggerInstance("com.restfb.HTTP");
    MAPPER_LOGGER = getLoggerInstance("com.restfb.JSON_MAPPER");
    UTILS_LOGGER = getLoggerInstance("com.restfb.UTILITY");
    CLIENT_LOGGER = getLoggerInstance("com.restfb.CLIENT");
    VALUE_FACTORY_LOGGER = getLoggerInstance("com.restfb.types.CHANGE_VALUE_FACTORY");
  }

  public static RestFBLogger getLoggerInstance(String logCategory) {
    Object obj;
    Class[] ctrTypes = new Class[] { String.class };
    Object[] ctrArgs = new Object[] { logCategory };
    try {
      Constructor ctor = LOGGER_CLASS.getConstructor(ctrTypes);
      obj = ctor.newInstance(ctrArgs);
    } catch (Exception e) {
      throw new FacebookLoggerException("cannot create logger: " + logCategory);
    }

    return (RestFBLogger) obj;
  }

  public abstract void trace(Object msg);

  public abstract void trace(Object msg, Throwable thr);

  public abstract void debug(Object msg);

  public abstract void debug(Object msg, Throwable thr);

  public abstract void info(Object msg);

  public abstract void info(Object msg, Throwable thr);

  public abstract void warn(Object msg);

  public abstract void warn(Object msg, Throwable thr);

  public abstract void error(Object msg);

  public abstract void error(Object msg, Throwable thr);

  public abstract void fatal(Object msg);

  public abstract void fatal(Object msg, Throwable thr);

  public abstract boolean isDebugEnabled();

  public abstract boolean isInfoEnabled();

  public abstract boolean isTraceEnabled();
}

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
    // define our logger class
    Class loggerClass = com.restfb.logging.JulLogger.class;
    LOGGER_CLASS = loggerClass;

    // create the loggers
    HTTP_LOGGER = getLoggerInstance("com.restfb.HTTP");
    MAPPER_LOGGER = getLoggerInstance("com.restfb.DefaultJsonMapper");
    UTILS_LOGGER = getLoggerInstance("com.restfb.UTILITY");
    CLIENT_LOGGER = getLoggerInstance("com.restfb.DefaultFacebookClient");
    VALUE_FACTORY_LOGGER = getLoggerInstance("com.restfb.types.webhook.ChangeValueFactory");
  }

  public static RestFBLogger getLoggerInstance(String logCategory) {
    Object obj;
    Class[] ctrTypes = new Class[] { String.class };
    Object[] ctrArgs = new Object[] { logCategory };
    try {
      Constructor ctor = LOGGER_CLASS.getConstructor(ctrTypes);
      obj = ctor.newInstance(ctrArgs);
    } catch (Exception e) {
      throw new FacebookLoggerException("logger cannot be created");
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

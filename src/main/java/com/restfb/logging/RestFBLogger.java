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

import java.lang.reflect.Constructor;

import com.restfb.exception.FacebookLoggerException;

/**
 * Abstract class that is the parent of all our logger implementations.
 * <p>
 * Normally RestFB uses java.util.logging for logging messages. But as soon as slf4j is found on the class path RestFB
 * switches to this logger. With the slf4j facade more logger implementations are supported and the quasi standard for
 * java logging is used.
 * <p>
 * In the rare case you have to switch to java.util.logging although slf4j is present on the class path we provide a
 * system property to force java.util.logging to be used.
 * <p>
 * The system property is called <code>com.restfb.forceJUL</code> and can be set to {@code true} if jul should be
 * forced.
 */
public abstract class RestFBLogger {

  private static Class<? extends RestFBLogger> usedLoggerClass = null;

  public static final RestFBLogger HTTP_LOGGER;

  public static final RestFBLogger MAPPER_LOGGER;

  public static final RestFBLogger UTILS_LOGGER;

  public static final RestFBLogger CLIENT_LOGGER;

  public static final RestFBLogger VALUE_FACTORY_LOGGER;

  static {
    Class<? extends RestFBLogger> loggerClass;

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
    usedLoggerClass = loggerClass;

    // create the loggers
    HTTP_LOGGER = getLoggerInstance("com.restfb.HTTP");
    MAPPER_LOGGER = getLoggerInstance("com.restfb.JSON_MAPPER");
    UTILS_LOGGER = getLoggerInstance("com.restfb.UTILITY");
    CLIENT_LOGGER = getLoggerInstance("com.restfb.CLIENT");
    VALUE_FACTORY_LOGGER = getLoggerInstance("com.restfb.types.CHANGE_VALUE_FACTORY");
  }

  /**
   * returns the instance of the logger that belongs to the category.
   * 
   * @param logCategory
   *          the category of the logger
   * @return a instance of the logger
   */
  public static RestFBLogger getLoggerInstance(String logCategory) {
    Object obj;
    Class[] ctrTypes = new Class[] { String.class };
    Object[] ctrArgs = new Object[] { logCategory };
    try {
      Constructor<? extends RestFBLogger> loggerClassConstructor = usedLoggerClass.getConstructor(ctrTypes);
      obj = loggerClassConstructor.newInstance(ctrArgs);
    } catch (Exception e) {
      throw new FacebookLoggerException("cannot create logger: " + logCategory);
    }

    return (RestFBLogger) obj;
  }

  /**
   * Log a message at the TRACE level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void trace(String msg, Object... args);

  /**
   * Log a message at the DEBUG level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void debug(String msg, Object... args);

  /**
   * Log a message at the INFO level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void info(String msg, Object... args);

  /**
   * Log a message at the WARN level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void warn(String msg, Object... args);

  /**
   * Log a message at the ERROR level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void error(String msg, Object... args);

  /**
   * Log a message at the FATAL level according to the specified format and arguments.
   *
   * @param msg
   *          the log message
   * @param args
   *          optional arguments, the last argument may be an exception
   */
  public abstract void fatal(String msg, Object... args);

  /**
   * Is the logger instance enabled for the DEBUG level?
   * 
   * @return {@code true} if it is enabled, {@code false} otherwise
   */
  public abstract boolean isDebugEnabled();

  /**
   * Is the logger instance enabled for the INFO level?
   * 
   * @return {@code true} if it is enabled, {@code false} otherwise
   */
  public abstract boolean isInfoEnabled();

  /**
   * Is the logger instance enabled for the TRACE level?
   * 
   * @return {@code true} if it is enabled, {@code false} otherwise
   */
  public abstract boolean isTraceEnabled();
}

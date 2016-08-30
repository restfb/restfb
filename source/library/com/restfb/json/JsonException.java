package com.restfb.json;

/**
 * The JsonException is thrown by the JSON.org classes then things are amiss.
 * 
 * @author JSON.org
 * @version 2008-09-18
 */
public class JsonException extends RuntimeException {
  private Exception cause;

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a JsonException with an explanatory message.
   * 
   * @param message
   *          Detail about the reason for the exception.
   */
  public JsonException(String message) {
    super(message);
  }

  public JsonException(Exception t) {
    super(t.getMessage());
    this.cause = t;
  }

  @Override
  public Exception getCause() {
    return this.cause;
  }
}
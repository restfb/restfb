package com.restfb.exception;

import com.restfb.json.JsonObject;

/**
 * Exception for errors that occur when interacting with the Instagram Graph API using the Instagram Login.
 */
public class InstagramApiException extends FacebookGraphException {

  private static final long serialVersionUID = 1L;

  public InstagramApiException(String errorType, String errorMessage, Integer errorCode, Integer errorSubcode,
      Integer httpStatusCode, boolean isTransient, JsonObject rawError) {
    super(errorType, errorMessage, errorCode, errorSubcode, httpStatusCode, null, null, isTransient, rawError);
  }
}

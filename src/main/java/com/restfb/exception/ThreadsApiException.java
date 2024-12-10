package com.restfb.exception;

import com.restfb.json.JsonObject;

public class ThreadsApiException extends FacebookGraphException {

  public ThreadsApiException(String errorType, String errorMessage, Integer errorCode, Integer errorSubcode,
      Integer httpStatusCode, boolean isTransient, JsonObject rawError) {
    super(errorType, errorMessage, errorCode, errorSubcode, httpStatusCode, null, null,
      isTransient, rawError);
  }
}

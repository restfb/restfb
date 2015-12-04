/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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
package com.restfb.exception.generator;

import com.restfb.exception.*;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

public class DefaultLegacyFacebookExceptionGenerator implements LegacyFacebookExceptionGenerator {

  /**
   * Knows how to map Old REST API exceptions to formal Java exception types.
   */
  protected FacebookExceptionMapper legacyFacebookExceptionMapper;

  /**
   * Legacy API error response 'error_code' attribute name.
   */
  protected static final String LEGACY_ERROR_CODE_ATTRIBUTE_NAME = "error_code";

  /**
   * Legacy API error response 'error_msg' attribute name.
   */
  protected static final String LEGACY_ERROR_MSG_ATTRIBUTE_NAME = "error_msg";

  public DefaultLegacyFacebookExceptionGenerator() {
    legacyFacebookExceptionMapper = createLegacyFacebookExceptionMapper();
  }

  /**
   * Specifies how we map Old REST API exception types/messages to real Java exceptions.
   * <p>
   * Uses an instance of {@link DefaultLegacyFacebookExceptionMapper} by default.
   *
   * @return An instance of the exception mapper we should use.
   * @since 1.6.3
   */
  protected FacebookExceptionMapper createLegacyFacebookExceptionMapper() {
    return new DefaultLegacyFacebookExceptionMapper();
  }

  @Override
  public void throwLegacyFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      JsonObject errorObject = silentlyCreateObjectFromString(json);

      if (errorObject == null || !errorObject.has(LEGACY_ERROR_CODE_ATTRIBUTE_NAME)) {
        return;
      }

      throw legacyFacebookExceptionMapper.exceptionForTypeAndMessage(
        errorObject.getInt(LEGACY_ERROR_CODE_ATTRIBUTE_NAME), null, httpStatusCode, null,
        errorObject.getString(LEGACY_ERROR_MSG_ATTRIBUTE_NAME), null, null, errorObject);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      // nothing to do here
    }
  }

  /**
   * checks if a string may be a json and contains a error string somewhere, this is used for speedup the error parsing
   *
   * @param json
   */
  protected void skipResponseStatusExceptionParsing(String json) throws ResponseErrorJsonParsingException {
    // If this is not an object, it's not an error response.
    if (!json.startsWith("{")) {
      throw new ResponseErrorJsonParsingException();
    }

    int subStrEnd = Math.min(50, json.length());
    if (!json.substring(0, subStrEnd).contains("\"error\"")) {
      throw new ResponseErrorJsonParsingException();
    }
  }

  /**
   * create a {@see JsonObject} from String and swallow possible {@see JsonException}
   *
   * @param json
   *          the string representation of the json
   * @return the JsonObject, may be <code>null</code>
   */
  protected JsonObject silentlyCreateObjectFromString(String json) {
    JsonObject errorObject = null;

    // We need to swallow exceptions here because it's possible to get a legit
    // Facebook response that contains illegal JSON (e.g.
    // users.getLoggedInUser returning 1240077) - we're only interested in
    // whether or not there's an error_code field present.
    try {
      errorObject = new JsonObject(json);
    } catch (JsonException e) {
      // do nothing here
    }

    return errorObject;
  }

  /**
   * A canned implementation of {@code FacebookExceptionMapper} that maps Old REST API exceptions.
   *
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.3
   */
  protected static class DefaultLegacyFacebookExceptionMapper implements FacebookExceptionMapper {
    /**
     * Invalid OAuth 2.0 Access Token error code.
     * <p>
     * See http://www.takwing.idv.hk/tech/fb_dev/faq/general/gen_10.html
     */
    protected static final int API_EC_PARAM_ACCESS_TOKEN = 190;

    /**
     * @see com.restfb.exception.FacebookExceptionMapper#exceptionForTypeAndMessage(Integer, Integer, Integer, String,
     *      String, String, String, JsonObject)
     */
    @Override
    public FacebookException exceptionForTypeAndMessage(Integer errorCode, Integer errorSubcode, Integer httpStatusCode,
        String type, String message, String userTitle, String userMessage, JsonObject rawError) {
      if (errorCode == API_EC_PARAM_ACCESS_TOKEN) {
        return new FacebookOAuthException(String.valueOf(errorCode), message, errorCode, errorSubcode, httpStatusCode,
          userTitle, userMessage, rawError);
      }

      // Don't recognize this exception type? Just go with the standard
      // FacebookResponseStatusException.
      return new FacebookResponseStatusException(errorCode, message, rawError);
    }
  }
}

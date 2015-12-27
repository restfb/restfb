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
package com.restfb.exception.generator;

import static com.restfb.util.StringUtils.toInteger;

import com.restfb.exception.*;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.json.ParseException;

public class DefaultFacebookExceptionGenerator extends DefaultLegacyFacebookExceptionGenerator
    implements FacebookExceptionGenerator {

  /**
   * Knows how to map Graph API exceptions to formal Java exception types.
   */
  protected FacebookExceptionMapper graphFacebookExceptionMapper;

  /**
   * API error response 'error' attribute name.
   */
  protected static final String ERROR_ATTRIBUTE_NAME = "error";

  /**
   * API error response 'type' attribute name.
   */
  protected static final String ERROR_TYPE_ATTRIBUTE_NAME = "type";

  /**
   * API error response 'error_user_title' attribute name.
   */
  protected static final String ERROR_USER_TITLE_ATTRIBUTE_NAME = "error_user_title";

  /**
   * API error response 'error_user_msg' attribute name.
   */
  protected static final String ERROR_USER_MSG_ATTRIBUTE_NAME = "error_user_msg";

  /**
   * API error response 'message' attribute name.
   */
  protected static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "message";

  /**
   * API error response 'code' attribute name.
   */
  protected static final String ERROR_CODE_ATTRIBUTE_NAME = "code";

  /**
   * API error response 'error_subcode' attribute name.
   */
  protected static final String ERROR_SUBCODE_ATTRIBUTE_NAME = "error_subcode";

  /**
   * Batch API error response 'error' attribute name.
   */
  protected static final String BATCH_ERROR_ATTRIBUTE_NAME = "error";

  /**
   * Batch API error response 'error_description' attribute name.
   */
  protected static final String BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME = "error_description";

  public DefaultFacebookExceptionGenerator() {
    super();
    graphFacebookExceptionMapper = createGraphFacebookExceptionMapper();
  }

  @Override
  public void throwFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      // If we have a legacy exception, throw it.
      throwLegacyFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

      // If we have a batch API exception, throw it.
      throwBatchFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

      JsonObject errorObject = Json.parse(json).asObject();

      if (errorObject.get(ERROR_ATTRIBUTE_NAME) == null) {
        return;
      }

      JsonObject innerErrorObject = errorObject.get(ERROR_ATTRIBUTE_NAME).asObject();

      // If there's an Integer error code, pluck it out.
      Integer errorCode = innerErrorObject.get(ERROR_CODE_ATTRIBUTE_NAME) != null
          ? toInteger(innerErrorObject.get(ERROR_CODE_ATTRIBUTE_NAME).toString()) : null;
      Integer errorSubcode = innerErrorObject.get(ERROR_SUBCODE_ATTRIBUTE_NAME) != null
          ? toInteger(innerErrorObject.get(ERROR_SUBCODE_ATTRIBUTE_NAME).toString()) : null;

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(errorCode, errorSubcode, httpStatusCode,
        innerErrorObject.getString(ERROR_TYPE_ATTRIBUTE_NAME, null),
        innerErrorObject.get(ERROR_MESSAGE_ATTRIBUTE_NAME).asString(),
        innerErrorObject.getString(ERROR_USER_TITLE_ATTRIBUTE_NAME, null),
        innerErrorObject.getString(ERROR_USER_MSG_ATTRIBUTE_NAME, null), errorObject);
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      // do nothing here
    }
  }

  @Override
  public void throwBatchFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      JsonObject errorObject = silentlyCreateObjectFromString(json);

      if (errorObject == null || errorObject.get(BATCH_ERROR_ATTRIBUTE_NAME) == null
          || errorObject.get(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME) == null)
        return;

      throw legacyFacebookExceptionMapper.exceptionForTypeAndMessage(errorObject.getInt(BATCH_ERROR_ATTRIBUTE_NAME, 0),
        null, httpStatusCode, null, errorObject.getString(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME, null), null, null,
        errorObject);
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      // do nothing here
    }
  }

  /**
   * Specifies how we map Graph API exception types/messages to real Java exceptions.
   * <p>
   * Uses an instance of {@link DefaultGraphFacebookExceptionMapper} by default.
   *
   * @return An instance of the exception mapper we should use.
   * @since 1.6
   */
  protected FacebookExceptionMapper createGraphFacebookExceptionMapper() {
    return new DefaultGraphFacebookExceptionMapper();
  }

  /**
   * A canned implementation of {@code FacebookExceptionMapper} that maps Graph API exceptions.
   * <p>
   * Thanks to BatchFB's Jeff Schnitzer for doing some of the legwork to find these exception type names.
   *
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.3
   */
  protected static class DefaultGraphFacebookExceptionMapper implements FacebookExceptionMapper {
    /**
     * @see com.restfb.exception.FacebookExceptionMapper#exceptionForTypeAndMessage(Integer, Integer, Integer, String,
     *      String, String, String, JsonObject)
     */
    @Override
    public FacebookException exceptionForTypeAndMessage(Integer errorCode, Integer errorSubcode, Integer httpStatusCode,
        String type, String message, String errorUserTitle, String errorUserMessage, JsonObject rawError) {
      if ("OAuthException".equals(type) || "OAuthAccessTokenException".equals(type)) {
        return new FacebookOAuthException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
          errorUserMessage, rawError);
      }

      if ("QueryParseException".equals(type)) {
        return new FacebookQueryParseException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
          errorUserMessage, rawError);
      }

      // Don't recognize this exception type? Just go with the standard
      // FacebookGraphException.
      return new FacebookGraphException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
        errorUserMessage, rawError);
    }
  }
}

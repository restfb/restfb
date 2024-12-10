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
package com.restfb.exception.generator;

import static com.restfb.util.StringUtils.toInteger;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.exception.*;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.json.ParseException;

public class DefaultFacebookExceptionGenerator implements FacebookExceptionGenerator {

  /**
   * Knows how to map Graph API exceptions to formal Java exception types.
   */
  protected FacebookExceptionMapper graphFacebookExceptionMapper;

  private static final Pattern ERROR_PATTERN = Pattern.compile("\"error[_a-z]*\"\\s*:");

  public DefaultFacebookExceptionGenerator() {
    super();
    graphFacebookExceptionMapper = createGraphFacebookExceptionMapper();
  }

  @Override
  public void throwFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      throwLoginOauthExceprtionIfNecessary(json, httpStatusCode);

      // If we have a batch API exception, throw it.
      throwBatchFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

      JsonObject errorObject = Json.parse(json).asObject();

      if (!errorObject.contains(ERROR_ATTRIBUTE_NAME)) {
        return;
      }

      ExceptionInformation container = createFacebookResponseTypeAndMessageContainer(errorObject, httpStatusCode);

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(container);
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      // do nothing here
    }
  }

  private void throwLoginOauthExceprtionIfNecessary(String json, Integer httpStatusCode) {
    JsonObject errorObject = silentlyCreateObjectFromString(json);

    if (errorObject == null || errorObject.contains(BATCH_ERROR_ATTRIBUTE_NAME)) {
      return;
    }

    String errorType = errorObject.getString("error_type", null);
    Integer errorCode = errorObject.getInt("code", 0);
    String errorMessage = errorObject.getString("error_message", null);

    ExceptionInformation container = new ExceptionInformation(errorCode, null, httpStatusCode, errorType, errorMessage,
      null, null, false, errorObject);
    throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(container);
  }

  protected ExceptionInformation createFacebookResponseTypeAndMessageContainer(JsonObject errorObject,
      Integer httpStatusCode) {
    JsonObject innerErrorObject = errorObject.get(ERROR_ATTRIBUTE_NAME).asObject();

    // If there's an Integer error code, pluck it out.
    Integer errorCode = Optional.ofNullable(innerErrorObject.get(ERROR_CODE_ATTRIBUTE_NAME))
      .map(obj -> toInteger(obj.toString())).orElse(null);
    Integer errorSubcode = Optional.ofNullable(innerErrorObject.get(ERROR_SUBCODE_ATTRIBUTE_NAME))
      .map(obj -> toInteger(obj.toString())).orElse(null);

    return new ExceptionInformation(errorCode, errorSubcode, httpStatusCode,
      innerErrorObject.getString(ERROR_TYPE_ATTRIBUTE_NAME, null),
      innerErrorObject.get(ERROR_MESSAGE_ATTRIBUTE_NAME).asString(),
      innerErrorObject.getString(ERROR_USER_TITLE_ATTRIBUTE_NAME, null),
      innerErrorObject.getString(ERROR_USER_MSG_ATTRIBUTE_NAME, null),
      innerErrorObject.getBoolean(ERROR_IS_TRANSIENT_NAME, false), errorObject);
  }

  @Override
  public void throwBatchFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      JsonObject errorObject = silentlyCreateObjectFromString(json);

      if (errorObject == null || errorObject.contains(BATCH_ERROR_ATTRIBUTE_NAME)
          || errorObject.contains(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME)
          // not a batch response, if data key is present
          || errorObject.contains("data"))
        return;

      ExceptionInformation container = new ExceptionInformation(errorObject.getInt(BATCH_ERROR_ATTRIBUTE_NAME, 0),
        httpStatusCode, errorObject.getString(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME, null), errorObject);

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(container);
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
    Matcher matcher = ERROR_PATTERN.matcher(json.substring(0, subStrEnd));
    if (!matcher.find()) {
      throw new ResponseErrorJsonParsingException();
    }
  }

  /**
   * create a {@link JsonObject} from String and swallow possible JsonException
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
      errorObject = Json.parse(json).asObject();
    } catch (ParseException e) {
      // do nothing here
    }

    return errorObject;
  }

  /**
   * A canned implementation of {@link FacebookExceptionMapper} that maps Graph API exceptions.
   * <p>
   * Thanks to BatchFB's Jeff Schnitzer for doing some of the legwork to find these exception type names.
   *
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.3
   */
  protected static class DefaultGraphFacebookExceptionMapper implements FacebookExceptionMapper {

    @Override
    public FacebookException exceptionForTypeAndMessage(ExceptionInformation container) {
      if ("OAuthException".equals(container.getType()) || "OAuthAccessTokenException".equals(container.getType())) {
        return new FacebookOAuthException(container.getType(), container.getMessage(), container.getErrorCode(),
          container.getErrorSubcode(), container.getHttpStatusCode(), container.getUserTitle(),
          container.getUserMessage(), container.getIsTransient(), container.getRawError());
      }

      if ("QueryParseException".equals(container.getType())) {
        return new FacebookQueryParseException(container.getType(), container.getMessage(), container.getErrorCode(),
          container.getErrorSubcode(), container.getHttpStatusCode(), container.getUserTitle(),
          container.getUserMessage(), container.getIsTransient(), container.getRawError());
      }

      if ("THApiException".equals(container.getType())) {
        return new ThreadsApiException(container.getType(), container.getMessage(), container.getErrorCode(),
          container.getErrorSubcode(), container.getHttpStatusCode(), container.getIsTransient(),
          container.getRawError());
      }

      // Don't recognize this exception type? Just go with the standard
      // FacebookGraphException.
      return new FacebookGraphException(container.getType(), container.getMessage(), container.getErrorCode(),
        container.getErrorSubcode(), container.getHttpStatusCode(), container.getUserTitle(),
        container.getUserMessage(), container.getIsTransient(), container.getRawError());
    }
  }
}

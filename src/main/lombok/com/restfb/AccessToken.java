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
package com.restfb;

import static com.restfb.util.UrlUtils.extractParametersFromQueryString;
import static java.lang.String.format;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.restfb.util.ReflectionUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an access token/expiration date pair.
 * <p>
 * Facebook returns these types when performing access token-related operations - see
 * {@link FacebookClient#convertSessionKeysToAccessTokens(String, String, String...)},
 * {@link FacebookClient#obtainAppAccessToken(String, String)}, and
 * {@link FacebookClient#obtainExtendedAccessToken(String, String, String)} for details.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class AccessToken {

    /**
     * The access token's value.
     *
     * @return The access token's value.
     */
    @Getter
    @Facebook("access_token")
    private String accessToken;

    @Facebook("expires_in")
    private Long rawExpires;

    /**
     * The date on which the access token expires.
     *
     * @return The date on which the access token expires.
     */
    @Getter
    private Date expires;

    /**
     * The token type of this access token provided by Facebook
     *
     * @return the access token type
     */
    @Getter
    @Facebook("token_type")
    private String tokenType;

    @Getter
    @Setter
    @Facebook("user_id")
    private String userId;

    private FacebookClient client;

    public void setClient(FacebookClient client) {
        this.client = client;
    }

    public FacebookClient getClient() {
        return Optional.ofNullable(client).orElse(null);
    }

    /**
     * Given a query string of the form {@code access_token=XXX} or {@code access_token=XXX&expires=YYY}, return an
     * {@code AccessToken} instance.
     * <p>
     * The {@code queryString} is required to contain an {@code access_token} parameter with a non-{@code null} value.
     * The {@code expires} value is optional and should be the number of seconds since the epoch. If the {@code expires}
     * value cannot be parsed, the returned {@code AccessToken} will have a {@code null} {@code expires} value.
     *
     * @param queryString The Facebook query string out of which to parse an {@code AccessToken} instance.
     * @return An {@code AccessToken} instance which corresponds to the given {@code queryString}.
     * @throws IllegalArgumentException If no {@code access_token} parameter is present in the query string.
     * @since 1.6.10
     */
    public static AccessToken fromQueryString(String queryString) {
        // Query string can be of the form 'access_token=XXX' or
        // 'access_token=XXX&expires=YYY'
        Map<String, List<String>> urlParameters = extractParametersFromQueryString(queryString);

        String extendedAccessToken = null;
        String tokenType = null;

        if (urlParameters.containsKey("access_token")) {
            extendedAccessToken = urlParameters.get("access_token").get(0);
        }

        if (urlParameters.containsKey("token_type")) {
            tokenType = urlParameters.get("token_type").get(0);
        }

        if (extendedAccessToken == null) {
            throw new IllegalArgumentException(format(
                    "Was expecting a query string of the form 'access_token=XXX' or 'access_token=XXX&expires=YYY'. Instead, the query string was '%s'",
                    queryString));
        }

        Date expires = null;

        // If an expires or expires_in value was provided and it's a valid long, great - use it.
        // Otherwise ignore it.
        String rawExpires = null;

        if (urlParameters.containsKey("expires")) {
            rawExpires = urlParameters.get("expires").get(0);
        }

        if (urlParameters.containsKey("expires_in")) {
            rawExpires = urlParameters.get("expires_in").get(0);
        }

        if (rawExpires != null && rawExpires.trim().matches("\\d+")) {
            expires = new Date(new Date().getTime() + Long.parseLong(rawExpires) * 1000L);
        }

        AccessToken accessToken = new AccessToken();
        accessToken.accessToken = extendedAccessToken;
        accessToken.expires = expires;
        accessToken.tokenType = tokenType;
        return accessToken;
    }

    @Override
    public int hashCode() {
        return ReflectionUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return ReflectionUtils.equals(this, that);
    }

    @Override
    public String toString() {
        return ReflectionUtils.toString(this);
    }

    @JsonMapper.JsonMappingCompleted
    void convertExpires() {
        if (rawExpires != null) {
            expires = new Date(new Date().getTime() + 1000L * rawExpires);
        }
    }
}

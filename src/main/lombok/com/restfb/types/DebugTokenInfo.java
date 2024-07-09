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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Represents the result of a {@link FacebookClient#debugToken(String)} inquiry.
 * </p>
 * See <a href="https://developers.facebook.com/docs/howtos/login/debugging-access-tokens/">Debug access tokens</a>
 *
 * @author Broc Seib
 */
public class DebugTokenInfo extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * The ID of the application this access token is for.
     *
     * @return The id of the application.
     */
    @Getter
    @Setter
    @Facebook("app_id")
    private String appId;

    /**
     * Name of the application this access token is for.
     *
     * @return The name of the application.
     */
    @Getter
    @Setter
    @Facebook
    private String application;

    /**
     * Timestamp when this access token expires.
     */
    @Facebook("expires_at")
    private Date expiresAt;

    /**
     * Timestamp when app's access to user data expires.
     *
     * @return The date when app's access to user data expires.
     */
    @Getter
    @Setter
    @Facebook("data_access_expires_at")
    private Date dataAccessExpiresAt;

    /**
     * The date on which the access token was issued.
     *
     * @return The date on which the access token was issued.
     */
    @Getter
    @Setter
    @Facebook("issued_at")
    private Date issuedAt;

    /**
     * Whether the token is valid.
     *
     * @return Whether the token is valid.
     */
    @Getter
    @Setter
    @Facebook("is_valid")
    private boolean isValid;

    /**
     * The ID of the user this access token is for.
     *
     * @return The user id.
     */
    @Getter
    @Setter
    @Facebook("user_id")
    private String userId;

    /**
     * For impersonated access tokens, the ID of the page this token contains.
     *
     * @return the profile id
     */
    @Getter
    @Setter
    @Facebook("profile_id")
    private String profileId;

    /**
     * General metadata associated with the access token. Can contain data like 'sso', 'auth_type', 'auth_nonce'
     *
     * @return General metadata associated with the access token
     */
    @Getter
    @Setter
    @Facebook
    private JsonObject metadata;

    /**
     * Any error that a request to the graph api would return due to the access token.
     */
    @Facebook
    private DebugTokenError error;

    /**
     * List of permissions that the user has granted for the app in this access token.
     */
    @Facebook
    private List<String> scopes = new ArrayList<>();

    /**
     * List of granular permissions that the user has granted for this app in this access token.
     */
    @Facebook("granular_scopes")
    private List<GranularScope> granularScopes = new ArrayList<>();

    @Getter
    @Setter
    @Facebook
    private String type;

    /**
     * The date on which the access token expires.
     *
     * @return The date on which the access token expires.
     */
    public Date getExpiresAt() {

        if (expiresAt != null && expiresAt.getTime() == 0L) {
            return null;
        }

        return expiresAt;
    }

    /**
     * List of scopes the access token 'contains'
     *
     * @return list of scopes
     */
    public List<String> getScopes() {
        return unmodifiableList(scopes);
    }

    /**
     * List of granular scopes the access token 'contains'
     *
     * @return list of granular scopes
     */
    public List<GranularScope> getGranularScopes() {
        return unmodifiableList(granularScopes);
    }

    /**
     * All Error data associated with access token debug.
     *
     * @return debug token error
     */
    public DebugTokenError getDebugTokenError() {
        return error;
    }

}

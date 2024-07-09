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
package com.restfb.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * A collection of data-encoding utility methods.
 * 
 * @author Josef Gierbl
 * @author Mikael Grev
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.13
 */
public final class EncodingUtils {

  /**
   * Prevents instantiation.
   */
  private EncodingUtils() {}

  private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

  /**
   * Decodes a base64-encoded string, padding out if necessary.
   * 
   * @param base64
   *          The base64-encoded string to decode.
   * @return A decoded version of {@code base64}.
   * @throws NullPointerException
   *           If {@code base64} is {@code null}.
   */
  public static byte[] decodeBase64(String base64) {
    return Base64.getDecoder().decode(Optional.ofNullable(base64).map(EncodingUtils::padBase64).orElseThrow(() -> new NullPointerException("Parameter 'base64' cannot be null.")));
  }

  private static String padBase64(String base64) {
    String padding = "";
    int remainder = base64.length() % 4;

    if (remainder > 0) {
      padding = IntStream.range(0, 4 - remainder).mapToObj(i -> "=").collect(Collectors.joining());
    }

    return base64 + padding;
  }

  /**
   * Encodes a hex {@code byte[]} from given {@code byte[]}.
   * <p>
   * This function is equivalent to Apache commons-codec binary {@code new Hex().encode(byte[])}
   * 
   * @param data
   *          The data to encode as hex.
   * @return Hex-encoded {@code byte[]}
   * @throws NullPointerException
   *           If {@code data} is {@code null}.
   */
  public static byte[] encodeHex(final byte[] data) {
    Objects.requireNonNull(data, "Parameter 'data' cannot be null.");
    char[] out = new char[data.length << 1];
    for (int j = 0; j < data.length; j++) {
      int v = data[j] & 0xFF;
      out[j * 2] = HEX_ARRAY[v >>> 4];
      out[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(out).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Generates an appsecret_proof for facebook.
   * <p>
   * See https://developers.facebook.com/docs/graph-api/securing-requests for more info
   * 
   * @param appSecret
   *          The facebook application secret
   * @param accessToken
   *          The facebook access token
   * @return A Hex encoded SHA256 Hash as a String
   */
  public static String encodeAppSecretProof(String appSecret, String accessToken) {
    try {
      byte[] key = appSecret.getBytes(StandardCharsets.UTF_8);
      SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      byte[] raw = mac.doFinal(accessToken.getBytes());
      byte[] hex = encodeHex(raw);
      return new String(hex, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new IllegalStateException("Creation of appsecret_proof has failed", e);
    }
  }
}
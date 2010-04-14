/*
 * Copyright (c) 2010 Mark Allen.
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit tests that exercise {@link JsonMapper} implementations, specifically the
 * "convert Java to JSON" functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperToJsonTests extends AbstractJsonMapperTests {
  /**
   * Can we handle null?
   */
  @Test
  public void nullObject() throws FacebookJsonMappingException {
    String json = createJsonMapper().toJson(null);
    Assert.assertTrue("null".equals(json));
  }

  /**
   * Can we handle the empty list?
   */
  @Test
  public void emptyList() throws FacebookJsonMappingException {
    String json = createJsonMapper().toJson(new ArrayList<Object>());
    Assert.assertTrue("[]".equals(json));
  }

  /**
   * Can we handle the empty object?
   */
  @Test
  public void emptyObject() throws FacebookJsonMappingException {
    String json = createJsonMapper().toJson(new Object());
    Assert.assertTrue("{}".equals(json));
  }

  /**
   * Can we handle primitives?
   */
  @Test
  public void primitives() throws FacebookJsonMappingException {
    Assert.assertTrue("\"Testing\""
      .equals(createJsonMapper().toJson("Testing")));
    Assert.assertTrue("true".equals(createJsonMapper().toJson(true)));
    Assert.assertTrue("1".equals(createJsonMapper().toJson(1)));
    Assert.assertTrue("1".equals(createJsonMapper().toJson(1L)));
    Assert.assertTrue("1.0".equals(createJsonMapper().toJson(1F)));
    Assert.assertTrue("1.0".equals(createJsonMapper().toJson(1D)));
    Assert.assertTrue("1"
      .equals(createJsonMapper().toJson(new BigInteger("1"))));
    Assert.assertTrue("1.0".equals(createJsonMapper().toJson(
      new BigDecimal("1"))));
  }

  /**
   * Can we handle a basic Javabean?
   */
  @Test
  public void basicJavabean() throws FacebookJsonMappingException {
    BasicUser basicUser = new BasicUser();
    basicUser.uid = 12345L;
    basicUser.name = "Fred";
    String json = createJsonMapper().toJson(basicUser);
    Assert.assertTrue("{\"uid\":12345,\"name\":\"Fred\"}".equals(json));
  }

  /**
   * Can we handle a more complex Javabean?
   */
  @Test
  public void complexJavabean() throws FacebookJsonMappingException {
    UserWithPhotos userWithPhotos = new UserWithPhotos();
    userWithPhotos.uid = 12345L;
    userWithPhotos.name = null;
    userWithPhotos.photos = new ArrayList<Photo>();
    userWithPhotos.photos.add(new Photo());
    Photo photo = new Photo();
    photo.photoId = 5678L;
    photo.location = "Las Vegas";
    userWithPhotos.photos.add(photo);

    String json = createJsonMapper().toJson(userWithPhotos);

    Assert
      .assertTrue("{\"uid\":12345,\"photos\":[{\"id\":null,\"location\":null},{\"id\":5678,\"location\":\"Las Vegas\"}],\"name\":null}"
        .equals(json));
  }

  static class BasicUser {
    @Facebook
    Long uid;

    @Facebook
    String name;

    Byte ignored;
  }

  static class Photo {
    @Facebook("id")
    Long photoId;

    @Facebook
    String location;
  }

  static class UserWithPhotos extends BasicUser {
    @Facebook(contains = Photo.class)
    List<Photo> photos;
  }
}
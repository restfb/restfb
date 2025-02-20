package com.restfb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccessTokenTest extends AbstractJsonMapperTests {

  @Test
  void tokenWithPermissions() {
    AccessToken accessToken =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/accesstoken"), AccessToken.class);
    assertNotNull(accessToken);
    assertNotNull(accessToken.getAccessToken());
    assertEquals("1234567890", accessToken.getUserId());
    assertEquals(4, accessToken.getPermissions().size());
    assertEquals("instagram_business_basic,instagram_business_manage_messages,instagram_business_manage_comments,instagram_business_content_publish", accessToken.getPermissionsAsString());
  }
}

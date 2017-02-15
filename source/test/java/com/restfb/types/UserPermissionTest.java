package com.restfb.types;

import com.restfb.AbstractJsonMapperTests;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserPermissionTest extends AbstractJsonMapperTests {
  @Test
  public void checkV2_8() {
    UserPermission examplePermission = createJsonMapper().toJavaObject(
        jsonFromClasspath("v2_8/user-permission"),
        UserPermission.class
    );
    assertEquals("user_managed_groups", examplePermission.getPermission());
    assertEquals("granted", examplePermission.getStatus());
  }
}

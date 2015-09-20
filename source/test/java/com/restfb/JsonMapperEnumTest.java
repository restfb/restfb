package com.restfb;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JsonMapperEnumTest {

  @Test
  public void createWithEnum() {
    String simpleJson = "{ id: 12345, test_enum: \"FOO\"}";
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertEquals("12345", testType.id);
    assertEquals(EnumTestEnum.FOO, testType.testEnum);
    assertEquals("FOO", testType.testEnumString);
  }

  @Test
  public void createWithNonExistingEnumValue() {
    String simpleJson = "{ id: 12345, test_enum: \"BAZ\"}";
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertEquals("12345", testType.id);
    assertEquals(null, testType.testEnum);
    assertEquals("BAZ", testType.testEnumString);
  }
}

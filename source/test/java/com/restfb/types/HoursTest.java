package com.restfb.types;

import com.restfb.AbstractJsonMapperTests;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class HoursTest extends AbstractJsonMapperTests {

  @Test
  public void checkMon1Open() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("11:00", page.getHours().getMonday1open());
  }

  @Test
  public void checkMon1Close() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("18:30", page.getHours().getMonday1close());
  }

  @Test
  public void checkSun1Open() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("12:00", page.getHours().getSunday1open());
  }

  @Test
  public void checkSun1Close() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("17:00", page.getHours().getSunday1close());
  }

  @Test
  public void checkSun2Open() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("19:00", page.getHours().getSunday2open());
  }

  @Test
  public void checkSun2Close() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-hours"), Page.class);
    assertEquals("20:00", page.getHours().getSunday2close());
  }


}

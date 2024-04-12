package com.restfb.types.threads;

import com.restfb.AbstractJsonMapperTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TdQuotaTest extends AbstractJsonMapperTests {

  @Test
  void checkBasicQuota() {
    TdQuota threadsQuota = createJsonMapper().toJavaObject(jsonFromClasspath("threads/basic-quota"), TdQuota.class);
    assertNotNull(threadsQuota);
    assertEquals(1L, threadsQuota.getReplyQuotaUsage());
    assertNotNull(threadsQuota.getReplyConfig());
    assertEquals(86400L, threadsQuota.getReplyConfig().getQuotaDuration());
    assertEquals(1000L, threadsQuota.getReplyConfig().getQuotaTotal());
  }
}

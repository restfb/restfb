/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.whatsapp.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.types.whatsapp.platform.group.GroupInfo;
import com.restfb.types.whatsapp.platform.group.GroupJoinRequest;
import com.restfb.types.whatsapp.platform.group.GroupParticipant;

class GroupApiJsonTest extends AbstractJsonMapperTests {

  @Test
  void checkGroupInfo() {
    GroupInfo groupInfo = createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/group-info"), GroupInfo.class);
    assertNotNull(groupInfo);
    assertEquals("whatsapp", groupInfo.getMessagingProduct());
    assertEquals("120363418770915618@g.us", groupInfo.getId());
    assertEquals("Release Planning", groupInfo.getSubject());
    assertEquals(2, groupInfo.getParticipants().size());

    GroupParticipant firstParticipant = groupInfo.getParticipants().get(0);
    assertEquals("16505551234", firstParticipant.getWaId());
    assertEquals("US.13491208655302741918", firstParticipant.getUserId());
    assertEquals("US.ENT.11815799212886844830", firstParticipant.getParentUserId());
    assertEquals("@pablomorales", firstParticipant.getUsername());

    GroupParticipant secondParticipant = groupInfo.getParticipants().get(1);
    assertNull(secondParticipant.getWaId());
    assertEquals("US.99887766554433221100", secondParticipant.getUserId());
    assertNull(secondParticipant.getParentUserId());
    assertEquals("@realsheenanelson", secondParticipant.getUsername());
  }

  @Test
  void checkGroupJoinRequests() {
    Connection<GroupJoinRequest> joinRequests =
        new Connection<>(new DefaultFacebookClient(Version.LATEST), jsonFromClasspath("whatsapp/group-join-requests"),
            GroupJoinRequest.class);
    assertNotNull(joinRequests);
    assertEquals(2, joinRequests.getData().size());
    assertEquals("before-cursor", joinRequests.getBeforeCursor());
    assertEquals("after-cursor", joinRequests.getAfterCursor());

    GroupJoinRequest firstRequest = joinRequests.getData().get(0);
    assertEquals("join-request-1", firstRequest.getJoinRequestId());
    assertEquals("16505551234", firstRequest.getWaId());
    assertEquals("US.13491208655302741918", firstRequest.getUserId());
    assertEquals("US.ENT.11815799212886844830", firstRequest.getParentUserId());
    assertEquals("@pablomorales", firstRequest.getUsername());
    assertNotNull(firstRequest.getCreationTimestamp());

    GroupJoinRequest secondRequest = joinRequests.getData().get(1);
    assertEquals("join-request-2", secondRequest.getJoinRequestId());
    assertNull(secondRequest.getWaId());
    assertEquals("US.99887766554433221100", secondRequest.getUserId());
    assertNull(secondRequest.getParentUserId());
    assertEquals("@realsheenanelson", secondRequest.getUsername());
    assertNotNull(secondRequest.getCreationTimestamp());
  }
}

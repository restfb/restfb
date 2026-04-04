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
package com.restfb.types;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.whatsapp.GroupParticipantsUpdateValue;
import com.restfb.types.whatsapp.platform.group.GroupParticipant;

class WebhookWhatsappGroupsTest extends AbstractJsonMapperTests {

  @Test
  void groupParticipantsUpdate() {
    GroupParticipantsUpdateValue change =
        getWHObjectFromJson("webhook-group-participants-update", GroupParticipantsUpdateValue.class);
    assertThat(change.getMessagingProduct()).isEqualTo("whatsapp");
    assertThat(change.getMetadata()).isNotNull();
    assertThat(change.getGroups()).hasSize(5);

    GroupParticipantsUpdateValue.GroupEvent businessRemove = change.getGroups().get(0);
    assertThat(businessRemove.getGroupId()).isEqualTo("120363418770915618@g.us");
    assertThat(businessRemove.getType()).isEqualTo("group_participants_remove");
    assertThat(businessRemove.getRequestId()).isEqualTo("request-business-remove");
    assertThat(businessRemove.getInitiatedBy()).isEqualTo("business");
    assertThat(businessRemove.getRemovedParticipants()).hasSize(1);
    GroupParticipant removedByBusiness = businessRemove.getRemovedParticipants().get(0);
    assertThat(removedByBusiness.getInput()).isEqualTo("US.13491208655302741918");
    assertThat(removedByBusiness.getWaId()).isNull();

    GroupParticipantsUpdateValue.GroupEvent participantRemove = change.getGroups().get(1);
    assertThat(participantRemove.getInitiatedBy()).isEqualTo("participant");
    assertThat(participantRemove.getRemovedParticipants()).hasSize(1);
    GroupParticipant selfRemovedParticipant = participantRemove.getRemovedParticipants().get(0);
    assertThat(selfRemovedParticipant.getWaId()).isEqualTo("16505551234");
    assertThat(selfRemovedParticipant.getUserId()).isEqualTo("US.13491208655302741918");
    assertThat(selfRemovedParticipant.getParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(selfRemovedParticipant.getUsername()).isEqualTo("@pablomorales");

    GroupParticipantsUpdateValue.GroupEvent participantAdd = change.getGroups().get(2);
    assertThat(participantAdd.getType()).isEqualTo("group_participants_add");
    assertThat(participantAdd.getReason()).isEqualTo("invite_link");
    assertThat(participantAdd.getAddedParticipants()).hasSize(1);
    GroupParticipant addedParticipant = participantAdd.getAddedParticipants().get(0);
    assertThat(addedParticipant.getWaId()).isEqualTo("16505550000");
    assertThat(addedParticipant.getUserId()).isEqualTo("US.99887766554433221100");
    assertThat(addedParticipant.getParentUserId()).isEqualTo("US.ENT.00998877665544332211");
    assertThat(addedParticipant.getUsername()).isEqualTo("@realsheenanelson");

    GroupParticipantsUpdateValue.GroupEvent joinRequestCreated = change.getGroups().get(3);
    assertThat(joinRequestCreated.getType()).isEqualTo("group_join_request_created");
    assertThat(joinRequestCreated.getJoinRequestId()).isEqualTo("join-request-1");
    assertThat(joinRequestCreated.getWaId()).isEqualTo("16505551111");
    assertThat(joinRequestCreated.getUserId()).isEqualTo("US.11112222333344445555");
    assertThat(joinRequestCreated.getParentUserId()).isEqualTo("US.ENT.55554444333322221111");
    assertThat(joinRequestCreated.getUsername()).isEqualTo("@joinrequester");

    GroupParticipantsUpdateValue.GroupEvent joinRequestRevoked = change.getGroups().get(4);
    assertThat(joinRequestRevoked.getType()).isEqualTo("group_join_request_revoked");
    assertThat(joinRequestRevoked.getJoinRequestId()).isEqualTo("join-request-2");
    assertThat(joinRequestRevoked.getWaId()).isEqualTo("16505552222");
    assertThat(joinRequestRevoked.getUserId()).isEqualTo("US.22223333444455556666");
    assertThat(joinRequestRevoked.getParentUserId()).isEqualTo("US.ENT.66665555444433332222");
    assertThat(joinRequestRevoked.getUsername()).isEqualTo("@revokedrequest");
  }

  private <T extends ChangeValue> T getWHObjectFromJson(String jsonName, Class<T> clazz) {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/" + jsonName), WebhookObject.class);
    assertThat(webhookObject.isWhatsAppBusinessAccount()).isTrue();
    assertThat(webhookObject.getEntryList()).hasSize(1);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    return entry.getChanges().get(0).getValue().convertChangeValue(clazz);
  }
}

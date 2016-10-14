/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

import java.util.Date;

public class PostTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_1() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-story"), Post.class);
    assertEquals("Tester shared a link.", examplePost.getStory());
    Post.Privacy priv = examplePost.getPrivacy();
    assertEquals("ALL_FRIENDS", priv.getValue());
  }

  @Test
  public void checkV2_1_emptyArrayPost() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-emptyarray"), Post.class);
    assertNotNull(examplePost.getProperties().get(0));
    assertNull(examplePost.getProperties().get(0).getName());
    assertNull(examplePost.getProperties().get(0).getHref());
    assertNull(examplePost.getProperties().get(0).getText());

    assertNotNull(examplePost.getProperties().get(1));
    assertNotNull(examplePost.getProperties().get(1).getName());
    assertNotNull(examplePost.getProperties().get(1).getHref());
    assertNotNull(examplePost.getProperties().get(1).getText());
  }

  @Test
  public void checkV2_1_noMessageTags() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-messagetags"), Post.class);
    assertEquals(0, examplePost.getMessageTags().size());
  }

  @Test
  public void checkV2_1_groupPost() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-group"), Post.class);

    assertNotNull(examplePost.getTo());
    assertEquals(1, examplePost.getTo().size());
  }

  @Test
  public void checkV2_1_Likes() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-messagetags"), Post.class);
    assertEquals(1L, examplePost.getLikes().getData().size());
  }

  @Test
  public void checkV2_1_LikesWithTotalCount() {
    Post examplePost =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-with-likes-totalcount"), Post.class);
    assertEquals(33L, examplePost.getLikes().getTotalCount().longValue());
  }

  @Test
  public void checkV2_1_PortogueseText() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/post-portoguese"), Post.class);
    String message = "Esse carro \u00e9 maravilhoso.Deus n\u00e3o nos desampara.Obrigada Senhor.";
    assertEquals(message, examplePost.getMessage());
  }

  @Test
  public void checkV2_3_AdminCreator() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-admin-creator"), Post.class);
    assertNotNull(examplePost.getAdminCreator());
    assertEquals("Graph API Explorer", examplePost.getAdminCreator().getName());
    assertEquals("145634995501895", examplePost.getAdminCreator().getId());
  }

  @Test
  public void checkV2_3_FeedTargeting() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-feedtargeting"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertEquals(21, examplePost.getFeedTargeting().getAgeMin().intValue());
    assertEquals(65, examplePost.getFeedTargeting().getAgeMax().intValue());
    assertEquals(5, examplePost.getFeedTargeting().getLocales().get(0).intValue());
  }

  @Test
  public void checkV2_3_FeedTargeting_Issue262() {
    Post examplePost =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-feedtargeting-issue262"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertNotNull(examplePost.getFeedTargeting().getCities());
    assertEquals("Newark", examplePost.getFeedTargeting().getCities().get(0).getName());
    assertEquals("2484432", examplePost.getFeedTargeting().getCities().get(0).getId());
  }

  @Test
  public void checkV2_3_Targeting() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-targeting"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getTargeting());
    assertNotNull(examplePost.getTargeting().getCities());
    assertEquals("Berlin", examplePost.getTargeting().getCities().get(0).getName());
    assertEquals("542609", examplePost.getTargeting().getCities().get(0).getId());
  }

  @Test
  public void checkV2_3_FeedTargeting_Region() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-feedtargeting-region"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertNotNull(examplePost.getFeedTargeting().getRegions());
    assertEquals("California", examplePost.getFeedTargeting().getRegions().get(0).getName());
    assertEquals("6", examplePost.getFeedTargeting().getRegions().get(0).getId());
  }

  @Test
  public void checkV2_3_IsHidden() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-ishidden"), Post.class);
    assertNotNull(examplePost);
    assertTrue(examplePost.getIsHidden());
  }

  @Test
  public void checkV2_3_FullPicture() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-fullpicture"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFullPicture());
  }

  @Test
  public void checkV2_3_Attachments() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-attachments"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getAttachments());
    assertNotNull(examplePost.getAttachments().getData());
    assertEquals(1, examplePost.getAttachments().getData().size());
    assertNotNull(examplePost.getAttachments().getData().get(0));
    assertNotNull(examplePost.getAttachments().getData().get(0).getSubAttachments());
    assertNotNull(examplePost.getAttachments().getData().get(0).getSubAttachments().getData());
    assertEquals(3, examplePost.getAttachments().getData().get(0).getSubAttachments().getData().size());
  }

  @Test
  public void checkV2_4_messageTags() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/message-tags"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getMessageTags());
    assertFalse(examplePost.getMessageTags().isEmpty());
    Post.MessageTag exampleTag = examplePost.getMessageTags().get(0);
    assertEquals("88388366982", exampleTag.getId());
    assertEquals("Público", exampleTag.getName());
    assertEquals("page", exampleTag.getType());
    assertEquals(7, exampleTag.getLength().intValue());
    assertEquals(94, exampleTag.getOffset().intValue());
  }

  @Test
  public void checkV2_5_messageTags() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message-tags"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getMessageTags());
    assertFalse(examplePost.getMessageTags().isEmpty());
    Post.MessageTag exampleTag = examplePost.getMessageTags().get(0);
    assertEquals("88388366982", exampleTag.getId());
    assertEquals("Público", exampleTag.getName());
    assertEquals("page", exampleTag.getType());
    assertEquals(7, exampleTag.getLength().intValue());
    assertEquals(94, exampleTag.getOffset().intValue());
  }

  @Test
  public void checkV2_5_emptyMessageTags() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/empty-message-tags"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getMessageTags());
    assertTrue(examplePost.getMessageTags().isEmpty());
  }

  @Test
  public void checkV2_6_storyTags() {
    Post examplePost =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/post-with-storyAndMessageTags"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getMessageTags());
    assertNotNull(examplePost.getStoryTags());
    assertFalse(examplePost.getMessageTags().isEmpty());
    assertFalse(examplePost.getStoryTags().isEmpty());
    assertEquals(1, examplePost.getMessageTags().size());
    assertEquals(2, examplePost.getStoryTags().size());
  }

  @Test
  public void checkV2_5_likesSummary_canHasLike() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/post-likes-summary"), Post.class);
    assertNotNull(examplePost);
    Likes likesWithSummary = examplePost.getLikes();
    assertNotNull(likesWithSummary);
    assertEquals(7L, likesWithSummary.getTotalCount().longValue());
    assertTrue(likesWithSummary.getCanLike());
    assertFalse(likesWithSummary.getHasLiked());
  }

  @Test
  public void checkV2_3_likesSummary_canHasLike() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/post-likes-summary"), Post.class);
    assertNotNull(examplePost);
    Likes likesWithSummary = examplePost.getLikes();
    assertNotNull(likesWithSummary);
    assertEquals(7L, likesWithSummary.getTotalCount().longValue());
    assertNull(likesWithSummary.getCanLike());
    assertNull(likesWithSummary.getHasLiked());
  }

  @Test
  public void checkV2_5_scheduled() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/post-scheduled"), Post.class);
    assertNotNull(examplePost);
    assertEquals(1459416660000L, examplePost.getScheduledPublishTime().getTime());
    assertEquals("inactive", examplePost.getPromotionStatus());
    assertFalse(examplePost.getIsExpired());
    assertFalse(examplePost.getIsPopular());
    assertFalse(examplePost.getIsInstagramEligible());
    assertEquals("https://www.facebook.com/permalink.php?story_fbid=653434235363&id=123456789",
      examplePost.getPermalinkUrl());
    assertEquals("no timeline unit for this post", examplePost.getTimelineVisibility());
  }

  @Test
  public void canCommentComments_V2_5() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/comments-with-summary"), Post.class);
    Comments comments = examplePost.getComments();
    assertNotNull(comments);
    assertEquals(1L, comments.getTotalCount().longValue());
    assertTrue(comments.getCanComment());
    Comment c = comments.getData().get(0);
    assertEquals("Whooo, great Picture", c.getMessage());
  }

  @Test
  public void checkV2_5_parentField() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/post-parent"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getParentId());
    assertEquals("380528198800882_493275620859472", examplePost.getParentId());
  }

  @Test
  public void checkV2_6_reactions() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/post-with-reactions"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getReactions());
    Reactions reactions = examplePost.getReactions();
    assertEquals("NONE", reactions.getViewerReaction());
    assertEquals(1L, reactions.getTotalCount().intValue());
    assertNotNull(reactions.getData());
    Reactions.ReactionItem item = reactions.getData().get(0);
    assertEquals("LIKE", item.getType());
    assertEquals("46725238725", item.getId());
    assertEquals("Some User", item.getName());
    assertEquals(1L, examplePost.getReactionsCount().longValue());
  }

  @Test
  public void checkV2_8_feedTargeting() {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/post-with-feedtargeting"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertNotNull(examplePost.getTargeting());
    assertNotNull(examplePost.getTargeting().getGeoLocations());
    assertEquals(examplePost.getTargeting().getCities(), examplePost.getTargeting().getGeoLocations().getCities());
    assertEquals(examplePost.getTargeting().getCountries(),
      examplePost.getTargeting().getGeoLocations().getCountries());
  }

  @Test
  public void checkV2_8_feedTargetingScheduleUntil() {
    Post examplePost =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/post-with-feedtargeting-schedule"), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertNotNull(examplePost.getFeedTargeting().getRelevantUntilTs());
    Date ts = examplePost.getFeedTargeting().getRelevantUntilTs();
    assertEquals(1457620560000L, ts.getTime());
  }

  @Test
  public void checkV2_5_feedTargetingWithRegions() {
    verifyFeedTargeting("v2_5/post-with-feedtargeting-regions");
  }

  @Test
  public void checkV2_8_feedTargetingWithRegions() {
    verifyFeedTargeting("v2_8/post-with-feedtargeting-regions");
  }

  private void verifyFeedTargeting(String exampleFile) {
    Post examplePost = createJsonMapper().toJavaObject(jsonFromClasspath(exampleFile), Post.class);
    assertNotNull(examplePost);
    assertNotNull(examplePost.getFeedTargeting());
    assertEquals(Integer.valueOf(21), examplePost.getFeedTargeting().getAgeMin());
    assertEquals(Integer.valueOf(65), examplePost.getFeedTargeting().getAgeMax());
    assertNotNull(examplePost.getFeedTargeting().getGenders());
    assertFalse(examplePost.getFeedTargeting().getGenders().isEmpty());
    assertEquals(Integer.valueOf(1), examplePost.getFeedTargeting().getGenders().get(0));
    assertNotNull(examplePost.getFeedTargeting().getGeoLocations());
    assertNotNull(examplePost.getFeedTargeting().getGeoLocations().getRegions());
    assertFalse(examplePost.getFeedTargeting().getGeoLocations().getRegions().isEmpty());
    assertEquals("3886", examplePost.getFeedTargeting().getGeoLocations().getRegions().get(0).getId());
    assertEquals("3886", examplePost.getFeedTargeting().getGeoLocations().getRegions().get(0).getKey());
    assertEquals("2468631", examplePost.getFeedTargeting().getGeoLocations().getCities().get(0).getId());
    assertEquals("2468631", examplePost.getFeedTargeting().getGeoLocations().getCities().get(0).getKey());
    assertEquals("CA", examplePost.getFeedTargeting().getGeoLocations().getCountries().get(0));

    assertNotNull(examplePost.getFeedTargeting().getInterests());
    assertFalse(examplePost.getFeedTargeting().getInterests().isEmpty());
    assertEquals("6003012461997", examplePost.getFeedTargeting().getInterests().get(0));

    assertNotNull(examplePost.getFeedTargeting().getLocales());
    assertFalse(examplePost.getFeedTargeting().getLocales().isEmpty());
    assertEquals(Integer.valueOf(23), examplePost.getFeedTargeting().getLocales().get(0));
  }
}

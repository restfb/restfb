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
package com.restfb.webhook;

import com.restfb.types.webhook.*;
import com.restfb.types.webhook.instagram.InstagramCommentsValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.instagram.InstagramStoryInsightsValue;

/**
 * abstract class as base for custom webhook change listener, with this abstract class it is possible to implement only
 * a subset of the need methods and ignore the other ones.
 */
public abstract class AbstractWebhookChangeListener implements WebhookChangeListener {

  @Override
  public void feedCommentValue(FeedCommentValue feedCommentValue) {}

  @Override
  public void feedPhotoAddValue(FeedPhotoAddValue feedPhotoAddValue) {}

  @Override
  public void feedPhotoRemoveValue(FeedPhotoRemoveValue feedPhotoRemoveValue) {}

  @Override
  public void feedVideoValue(FeedVideoValue feedVideoValue) {}

  @Override
  public void feedVideoRemoveValue(FeedVideoRemoveValue feedVideoRemoveValue) {}

  @Override
  public void feedStatusValue(FeedStatusValue feedStatusValue) {}

  @Override
  public void feedAlbumAddValue(FeedAlbumAddValue feedAlbumAddValue) {}

  @Override
  public void feedLikeValue(FeedLikeValue feedLikeValue) {}

  @Override
  public void feedEventValue(FeedEventValue feedEventValue) {}

  @Override
  public void feedPostValue(FeedPostValue feedPostValue) {}

  @Override
  public void feedVideoBlockMute(FeedVideoBlockMute feedVideoBlockMute) {}

  @Override
  public void feedReactionValue(FeedReactionValue feedReactionValue) {}

  @Override
  public void feedShareValue(FeedShareValue feedShareValue) {}

  @Override
  public void feedAlbumEditedValue(FeedAlbumEditedValue feedAlbumEditedValue) {}

  @Override
  public void instagramCommentsValue(InstagramCommentsValue instagramCommentsValue) {}

  @Override
  public void instagramMentionsValue(InstagramMentionsValue instagramMentionsValue) {}

  @Override
  public void instagramStoryInsightsValue(InstagramStoryInsightsValue instagramStoryInsightsValue) {}

  @Override
  public void pageLeadgen(PageLeadgen pageLeadgen) {}

  @Override
  public void permissionChangeValue(PermissionChangeValue permissionChangeValue) {}

  @Override
  public void mentionPostAddValue(MentionPostAddValue mentionPostAddValue) {}

  @Override
  public void userPageValue(UserPageValue userPageValue) {}

  @Override
  public void ratingsReactionValue(RatingsReactionValue ratingsReactionValue) {}

  @Override
  public void ratingsLikeValue(RatingsLikeValue ratingsLikeValue) {}

  @Override
  public void ratingsCommentValue(RatingsCommentValue ratingsCommentValue) {}

  @Override
  public void ratingsRatingValue(RatingsRatingValue ratingsRatingValue) {}
}

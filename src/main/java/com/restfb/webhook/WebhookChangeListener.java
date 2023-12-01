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
 * interface to provide methods a listener can implement to handle the different change values
 */
public interface WebhookChangeListener {

  void feedCommentValue(FeedCommentValue feedCommentValue);

  void feedPhotoAddValue(FeedPhotoAddValue feedPhotoAddValue);

  void feedPhotoRemoveValue(FeedPhotoRemoveValue feedPhotoRemoveValue);

  void feedVideoValue(FeedVideoValue feedVideoValue);

  void feedVideoRemoveValue(FeedVideoRemoveValue feedVideoRemoveValue);

  void feedStatusValue(FeedStatusValue feedStatusValue);

  void feedAlbumAddValue(FeedAlbumAddValue feedAlbumAddValue);

  void feedLikeValue(FeedLikeValue feedLikeValue);

  void feedEventValue(FeedEventValue feedEventValue);

  void feedPostValue(FeedPostValue feedPostValue);

  void feedVideoBlockMute(FeedVideoBlockMute feedVideoBlockMute);

  void feedReactionValue(FeedReactionValue feedReactionValue);

  void feedShareValue(FeedShareValue feedShareValue);

  void feedAlbumEditedValue(FeedAlbumEditedValue feedAlbumEditedValue);

  void instagramCommentsValue(InstagramCommentsValue instagramCommentsValue);

  void instagramMentionsValue(InstagramMentionsValue instagramMentionsValue);

  void instagramStoryInsightsValue(InstagramStoryInsightsValue instagramStoryInsightsValue);

  void pageLeadgen(PageLeadgen pageLeadgen);

  void permissionChangeValue(PermissionChangeValue permissionChangeValue);

  void mentionPostAddValue(MentionPostAddValue mentionPostAddValue);

  void userPageValue(UserPageValue userPageValue);

  void ratingsReactionValue(RatingsReactionValue ratingsReactionValue);

  void ratingsLikeValue(RatingsLikeValue ratingsLikeValue);

  void ratingsCommentValue(RatingsCommentValue ratingsCommentValue);

  void ratingsRatingValue(RatingsRatingValue ratingsRatingValue);
}

package com.restfb.types.webhook;

import com.restfb.Facebook;
import com.restfb.types.webhook.base.AbstractFeedPostValue;
import lombok.Getter;
import lombok.Setter;

/**
 * Change value describing a reaction to a post.
 */
public class FeedReactionValue extends AbstractFeedPostValue {

  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

}

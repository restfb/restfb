package com.restfb.types.threads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/threads/reply-moderation">rate limits type</a>
 *
 * Check the "Rate limits" section
 */
@Setter
@Getter
public class TdQuota extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Facebook("reply_quota_usage")
  private Long replyQuotaUsage;

  @Facebook("reply_config")
  private TdReplyConfig replyConfig;

  @Setter
  @Getter
  public static class TdReplyConfig extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Facebook("quota_total")
    private Long quotaTotal;

    @Facebook("quota_duration")
    private Long quotaDuration;
  }
}

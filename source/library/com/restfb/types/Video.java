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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/video">Video Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Video extends NamedFacebookType {

  /**
   * An object containing the name and ID of the user who posted the video.
   * 
   * @return An object containing the name and ID of the user who posted the video.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType from;

  /**
   * The video title / caption.
   * 
   * @return The video title / caption.
   * @deprecated FB seems to have removed this field.
   */
  @Getter
  @Setter
  @Facebook
  @Deprecated
  private String message;

  /**
   * The comments for this video.
   *
   * @return The comments for this video.
   */
  @Getter
  @Setter
  @Facebook
  private Comments comments;

  /**
   * The long-form HTML description of the video.
   * 
   * @return The long-form HTML description of the video.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * URL to the permalink page of the video
   *
   * @return URL to the permalink page of the video
   */
  @Getter
  @Setter
  @Facebook("permalink_url")
  private String permalinkUrl;

  /**
   * The video title or caption
   *
   * @RestFB.GraphApi.Since 2.5
   * @return the video title or caption
   */
  @Getter
  @Setter
  @Facebook("title")
  private String title;

  /**
   * Specifies if the video is eligible for crossposting.
   *
   * @RestFB.GraphApi.Since 2.6
   * @return Specifies if the video is eligible for crossposting
   */
  @Getter
  @Setter
  @Facebook("is_crossposting_eligible")
  private Boolean isCrosspostingEligible;

  /**
   * Whether the video is eligible to be promoted on Instagram
   *
   * @return Whether the video is eligible to be promoted on Instagram
   */
  @Getter
  @Setter
  @Facebook("is_instagram_eligible")
  private Boolean isInstagramEligible;

  /**
   * Whether the video is exclusively used for copyright monitoring.
   *
   * @return Whether the video is exclusively used for copyright monitoring
   */
  @Getter
  @Setter
  @Facebook("is_reference_only")
  private Boolean isReferenceOnly;

  /**
   * The reactions for this video.
   *
   * @return The reactions for this video.
   */
  @Getter
  @Setter
  @Facebook
  private Reactions reactions;

  /**
   * Whether the video is embeddable.
   *
   * @RestFB.GraphApi.Since 2.4
   * @return Whether the video is embeddable.
   */
  @Getter
  @Setter
  @Facebook
  private Boolean embeddable;

  @Facebook("content_tags")
  private List<String> contentTags = new ArrayList<String>();

  /**
   * If this object has a place, the event associated with the place.
   * 
   * @return the event associated with the place
   */
  @Getter
  @Setter
  @Facebook
  private Event event;

  /**
   * Whether or not the video is highlighted in Video Channel.
   *
   * @RestFB.GraphApi.Since 2.7
   * @return Whether or not the video is highlighted in Video Channel.
   */
  @Getter
  @Setter
  @Facebook("feed_type")
  private String feedType;

  @Facebook
  private List<VideoFormat> format = new ArrayList<VideoFormat>();

  /**
   * People who like this.
   * 
   * you need to add the field to the fields query parameter to get the likes list otherwise null is returned
   * 
   * @return People who like this
   */
  @Getter
  @Setter
  @Facebook
  private Likes likes;

  /**
   * Location associated with the video, if any.
   * 
   * @return Location associated with the video, if any.
   */
  @Getter
  @Setter
  @Facebook
  private Place place;

  /**
   * The content category of this video.
   * 
   * @return The content category of this video.
   */
  @Getter
  @Setter
  @Facebook("content_category")
  private String contentCategory;

  /**
   * A URL for the thumbnail picture of the video.
   * 
   * @return A URL for the thumbnail picture of the video.
   */
  @Getter
  @Setter
  @Facebook
  private String picture;

  /**
   * An icon URL which represents the video.
   * 
   * @return An icon URL which represents the video.
   */
  @Getter
  @Setter
  @Facebook
  private String icon;

  /**
   * A URL to the raw, playable video file.
   * 
   * @return A URL to the raw, playable video file.
   * @since 1.6.5
   */
  @Getter
  @Setter
  @Facebook
  private String source;

  /**
   * HTML that may be used to embed the video on another website.
   * 
   * @return HTML that may be used to embed the video on another website.
   */
  @Getter
  @Setter
  @Facebook("embed_html")
  private String embedHtml;

  /**
   * The length of the video, in seconds.
   * 
   * @return The length of the video, in seconds.
   */
  @Getter
  @Setter
  @Facebook
  private Double length;

  /**
   * Number of unique people who watched the video broadcast when it was live.
   *
   * @RestFB.GraphApi.Since 2.6
   * @return Number of unique people who watched the video broadcast when it was live.
   */
  @Getter
  @Setter
  @Facebook("live_audience_count")
  private Integer liveAudienceCount;

  /**
   * The live status of the video.
   *
   * Possible values contain LIVE, LIVE_STOPPED, VOD
   *
   * @RestFB.GraphApi.Since 2.6
   * @return The live status of the video
   */
  @Getter
  @Setter
  @Facebook("live_status")
  private String liveStatus;

  /**
   * Privacy setting for the video.
   * 
   * @return Privacy setting for the video.
   */
  @Getter
  @Setter
  @Facebook
  private Privacy privacy;

  /**
   * Object describing the status attributes of a video.
   * 
   * @return Object describing the status attributes of a video.
   */
  @Getter
  @Setter
  @Facebook
  private VideoStatus status;

  /**
   * Whether a post about this video is published.
   * 
   * This field is only accessible in Graph API 2.3 or later.
   *
   * @RestFB.GraphApi.Since 2.3
   * @return whether a post about this video is published.
   * @since 1.10.0
   */
  @Getter
  @Setter
  @Facebook
  private Boolean published;

  /**
   * Back dated time
   *
   * @since 1.10.0
   */
  @Getter
  @Setter
  private Date backdatedTime;

  /**
   * String that represents the back dated time granularity
   *
   * @since 1.10.0
   */
  @Getter
  @Setter
  @Facebook("backdated_time_granularity")
  private String backdatedTimeGranularity;

  @Facebook("backdated_time")
  private String rawBackdatedTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  @Facebook("scheduled_publish_time")
  private String rawScheduledPublishTime;

  /**
   * The time the video was initially published.
   * 
   * @return The time the video was initially published.
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * The last time the video or its caption were updated.
   * 
   * @return The last time the video or its caption were updated.
   */
  @Getter
  @Setter
  private Date updatedTime;

  /**
   * The time that the video is scheduled to expire.
   * 
   * This field is only accessible in Graph API 2.3 or later.
   * 
   * @return The time that the video is scheduled to expire.
   * @since 1.10.0
   */
  @Getter
  @Setter
  private Date scheduledPublishTime;

  @Facebook
  private List<NamedFacebookType> tags = new ArrayList<NamedFacebookType>();

  private static final long serialVersionUID = 1L;

  /**
   * The different formats of the video.
   * 
   * @return The different formats of the video.
   */
  public List<VideoFormat> getFormat() {
    return unmodifiableList(format);
  }

  public boolean addFormat(VideoFormat videoFormat) {
    return format.add(videoFormat);
  }

  public boolean removeFormat(VideoFormat videoFormat) {
    return format.remove(videoFormat);
  }

  /**
   * Tags for the video.
   * 
   * @return Tags for the video.
   * @since 1.6.5
   */
  public List<NamedFacebookType> getTags() {
    return unmodifiableList(tags);
  }

  public boolean addTag(NamedFacebookType tag) {
    return tags.add(tag);
  }

  public boolean removeTag(NamedFacebookType tag) {
    return tags.remove(tag);
  }

  /**
   * Tags that describe the contents of the video.
   *
   * @return Tags that describe the contents of the video.
   */
  public List<String> getContentTags() {
    return unmodifiableList(contentTags);
  }

  public boolean addContentTag(String contentTag) {
    return contentTags.add(contentTag);
  }

  public boolean removeContentTag(String contentTag) {
    return contentTags.remove(contentTag);
  }

  @Deprecated
  public boolean addComment(Comment comment) {
    if (getComments() != null) {
      return getComments().addData(comment);
    }
    return false;
  }

  @Deprecated
  public boolean removeComment(Comment comment) {
    if (getComments() != null) {
      return getComments().removeData(comment);
    }
    return false;
  }

  /**
   * The number of likes on this video.
   * 
   * you have to fetch the video id with <code>?fields=likes.summary(true)</code> in order to speed up the likes count
   * generation, you may use <code>?fields=likes.limit(1).summary(true)</code>, so only 1 like is fetched, but the
   * complete summary
   *
   * @return The number of likes on this video.
   */
  public Long getLikesCount() {
    if (getLikes() != null) {
      return getLikes().getTotalCount();
    }

    return 0L;
  }

  /**
   * The number of comments of this video.
   * 
   * you have to fetch the video id with <code>?fields=comments.summary(true)</code> in order to speed up the comments
   * count generation, you may use <code>?fields=comments.limit(1).summary(true)</code>, so only 1 comment is fetched,
   * but the complete summary
   *
   * @return The number of comments of this video.
   */
  public Long getCommentsCount() {
    if (getComments() != null) {
      return getComments().getTotalCount();
    }
    return 0L;
  }

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
    backdatedTime = toDateFromLongFormat(rawBackdatedTime);
    scheduledPublishTime = toDateFromLongFormat(rawScheduledPublishTime);
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-thumbnail/">Video Thumbnail
   * Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class Thumbnail extends AbstractFacebookType {

    /**
     * The name of the thumbnail
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * The height of the thumbnail
     */
    @Getter
    @Setter
    @Facebook
    private Integer height;

    /**
     * The width of the thumbnail
     */
    @Getter
    @Setter
    @Facebook
    private Integer width;

    /**
     * The scale of the thumbnail
     */
    @Getter
    @Setter
    @Facebook
    private Float scale;

    /**
     * The uri of the thumbnail
     */
    @Getter
    @Setter
    @Facebook
    private String uri;

    /**
     * Whether this is the preferred thumbnail for the video
     */
    @Getter
    @Setter
    @Facebook("is_preferred")
    private Boolean isPreferred;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-format/">Video Format Graph
   * API type</a>.
   */
  public static class VideoFormat extends AbstractFacebookType {

    /**
     * HTML to embed the video in this format.
     * 
     * @return HTML to embed the video in this format.
     */
    @Getter
    @Setter
    @Facebook("embed_html")
    private String embedHtml;

    /**
     * The filter applied to this video format.
     * 
     * @return The filter applied to this video format.
     */
    @Getter
    @Setter
    @Facebook
    private String filter;

    /**
     * The thumbnail for the video in this format.
     * 
     * @return The thumbnail for the video in this format.
     */
    @Getter
    @Setter
    @Facebook
    private String picture;

    /**
     * The width of the video in this format.
     * 
     * @return The width of the video in this format.
     */
    @Getter
    @Setter
    @Facebook
    private Integer width;

    /**
     * The height of the video in this format.
     * 
     * @return The height of the video in this format.
     */
    @Getter
    @Setter
    @Facebook
    private Integer height;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-status/">Video Status Graph
   * API type</a>.
   */
  public static class VideoStatus extends AbstractFacebookType {

    /**
     * Status of a video.
     * 
     * Either "ready" (uploaded, encoded, thumbnails extracted), "processing" (not ready yet) or "error" (processing
     * failed).
     * 
     * @return Status of a video
     */
    @Getter
    @Setter
    @Facebook("video_status")
    private String videoStatus;

    /**
     * Video processing progress in percent [int 0 to 100].
     * 
     * @return Video processing progress in percent [int 0 to 100].
     */
    @Getter
    @Setter
    @Facebook("processing_progress")
    private Integer processingProgress;
  }

}
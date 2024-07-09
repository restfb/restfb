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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Connection;
import com.restfb.Facebook;
import com.restfb.annotation.GraphAPI;
import com.restfb.types.features.HasComments;
import com.restfb.types.features.HasCreatedTime;
import com.restfb.types.features.HasMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/reference/api/video">Video Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Video extends NamedFacebookType implements HasComments, HasCreatedTime, HasMessage {

  /**
   * An object containing the name and ID of the user who posted the video.
   * 
   * @return An object containing the name and ID of the user who posted the video.
   */
  @Getter
  @Setter
  @Facebook
  private From from;

  /**
   * The video title / caption.
   * 
   * @return The video title / caption.
   * @deprecated FB seems to have removed this field.
   */
  @Getter(onMethod_ = { @Override })
  @Setter
  @Facebook
  @Deprecated
  private String message;

  /**
   * The comments for this video.
   *
   * @return The comments for this video.
   */
  @Getter(onMethod_ = { @Override })
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
   * @return the video title or caption
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.5"))
  @Setter
  @Facebook("title")
  @GraphAPI(since = "2.5")
  private String title;

  @Getter
  @Setter
  @Facebook("is_crosspost_video")
  private Boolean isCrosspostVideo;

  /**
   * Specifies if the video is eligible for crossposting.
   *
   * @return Specifies if the video is eligible for crossposting
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.6"))
  @Setter
  @Facebook("is_crossposting_eligible")
  @GraphAPI(since = "2.6")
  private Boolean isCrosspostingEligible;

  /**
   * Whether this video is episode or not.
   *
   * @return Whether this video is episode or not.
   */
  @Getter
  @Setter
  @Facebook("is_episode")
  private Boolean isEpisode;

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
   * The music video copyright of this video
   *
   * because of a missing object description in the graph reference, we use a Map here
   */
  @Getter
  @Setter
  @Facebook("music_video_copyright")
  private MusicVideoCopyright musicVideoCopyright;

  /**
   * The public view count of the video post, not aggregated with any other crossposts of the video.
   */
  @Getter
  @Setter
  @Facebook("post_views")
  private Integer postViews;

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
   * @return Whether the video is embeddable.
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.4"))
  @Setter
  @Facebook
  @GraphAPI(since = "2.4")
  private Boolean embeddable;

  @Facebook("content_tags")
  private List<String> contentTags = new ArrayList<>();

  /**
   * If this object has a place, the event associated with the place.
   * 
   * @return the event associated with the place
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.3"))
  @Setter
  @Facebook
  @GraphAPI(since = "2.3")
  private Event event;

  /**
   * Whether or not the video is highlighted in Video Channel.
   *
   * @return Whether or not the video is highlighted in Video Channel.
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.7"))
  @Setter
  @Facebook("feed_type")
  @GraphAPI(since = "2.7")
  private String feedType;

  /**
   * The publishers asset management code for this video.
   */
  @Getter
  @Setter
  @Facebook("universal_video_id")
  private String universalVideoId;

  /**
   * The public view count of the video.
   */
  @Getter
  @Setter
  @Facebook
  private Integer views;

  @Facebook
  private List<VideoFormat> format = new ArrayList<>();

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
  @Getter(onMethod_ = @GraphAPI(since = "2.4"))
  @Setter
  @Facebook("content_category")
  @GraphAPI(since = "2.4")
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
   * @return Number of unique people who watched the video broadcast when it was live.
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.6"))
  @Setter
  @Facebook("live_audience_count")
  @GraphAPI(since = "2.6")
  private Integer liveAudienceCount;

  /**
   * The live status of the video.
   *
   * Possible values contain LIVE, LIVE_STOPPED, VOD
   *
   * @return The live status of the video
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.6"))
  @Setter
  @Facebook("live_status")
  @GraphAPI(since = "2.6")
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
   * The status of the Premiere Watch Party, if any
   */
  @Getter
  @Setter
  @Facebook("premiere_living_room_status")
  private String premiereLivingRoomStatus;

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
   * @return whether a post about this video is published.
   * @since 1.10.0
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.3"))
  @Setter
  @Facebook
  @GraphAPI(since = "2.3")
  private Boolean published;

  /**
   * Back dated time
   *
   * @since 1.10.0
   */
  @Getter
  @Setter
  @Facebook("backdated_time")
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

  /**
   * The time the video was initially published.
   * 
   * @return The time the video was initially published.
   */
  @Getter(onMethod_ = { @Override })
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  /**
   * The last time the video or its caption were updated.
   * 
   * @return The last time the video or its caption were updated.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  /**
   * The time that the video is scheduled to be published.
   * 
   * This field is only accessible in Graph API 2.3 or later.
   * 
   * @return The time that the video is scheduled to be published.
   * @since 1.10.0
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.3"))
  @Setter
  @Facebook("scheduled_publish_time")
  @GraphAPI(since = "2.3")
  private Date scheduledPublishTime;

  @Facebook
  private List<VideoCaption> captions = new ArrayList<>();

  @Facebook
  private List<NamedFacebookType> tags = new ArrayList<>();

  @Facebook("ad_breaks")
  private List<Integer> adBreaks = new ArrayList<>();

  @Facebook("custom_labels")
  private List<String> customLabels = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("video_insights")
  private Connection<Insight> videoInsights;

  @Getter
  @Setter
  @Facebook
  private Connection<Thumbnail> thumbnails;

  private static final long serialVersionUID = 1L;

  public List<VideoCaption> getCaptions() {
    return unmodifiableList(captions);
  }

  public boolean addCaption(VideoCaption caption) {
    return captions.add(caption);
  }

  public boolean removeCaption(VideoCaption caption) {
    return captions.remove(caption);
  }

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

  /**
   * Time offsets of ad breaks in milliseconds. Ad breaks are short ads that play within a video.
   *
   * @return Time offsets of ad breaks in milliseconds. Ad breaks are short ads that play within a video.
   */
  public List<Integer> getAdBreaks() {
    return unmodifiableList(adBreaks);
  }

  public boolean addAdBreak(Integer adBreak) {
    return adBreaks.add(adBreak);
  }

  public boolean removeAdBreak(Integer adBreak) {
    return adBreaks.remove(adBreak);
  }

  /**
   * Labels used to describe the video.
   *
   * Unlike content tags, custom labels are not published and only appear in insights data.
   *
   * @return Labels used to describe the video.
   */
  public List<String> getCustomLabels() {
    return unmodifiableList(customLabels);
  }

  public boolean addCustomLabel(String customLabel) {
    return customLabels.add(customLabel);
  }

  public boolean removeCustomLabel(String customLabel) {
    return customLabels.remove(customLabel);
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

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-thumbnail/">Video Thumbnail
   * Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class Thumbnail extends NamedFacebookType {

    private static final long serialVersionUID = 1L;

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

    private static final long serialVersionUID = 1L;

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

    private static final long serialVersionUID = 1L;

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

    /**
     * This structure contains information about progress through the uploading phase.
     * The bytes_transferred field can be used in conjunction with the upload endpoint
     * to resume an interrupted upload.
     */
    @Getter
    @Setter
    @Facebook("uploading_phase")
    private VideoPhase uploadingVideoPhase;

    /**
     * This structure contains information about progress through the processing phase.
     * This phase encompasses generating alternate media encodings, thumbnails, and other
     * assets necessary for publishing.
     */
    @Getter
    @Setter
    @Facebook("processing_phase")
    private VideoPhase processingVideoPhase;

    /**
     * This structure contains information about progress through the publishing phase.
     * This phase encompasses adding the video to the page, and if scheduled, will describe
     * when the video is intended to be published.
     */
    @Getter
    @Setter
    @Facebook("publishing_phase")
    private VideoPhase publishingVideoPhase;
  }

  public static class VideoPhase extends AbstractFacebookType {
    private static final long serialVersionUID = 1L;
    /**
     * Status of a videoPahse.
     *
     * not_started, in_progress, complete, error
     *
     * @return Status of a videoPhase
     */
    @Getter
    @Setter
    @Facebook("status")
    private String status;

    @Getter
    @Setter
    @Facebook("bytes_transfered")
    private Long bytesTransfered;

    @Getter
    @Setter
    @Facebook("publish_status")
    private String publishStatus;

    @Getter
    @Setter
    @Facebook("publish_time")
    private Date publishTime;

    @Getter
    @Setter
    @Facebook("errors")
    private List<VideoPhaseError> errors;

  }

  public static class VideoPhaseError extends AbstractFacebookType {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook("code")
    private Long code; // error code

    @Getter
    @Setter
    @Facebook("message")
    private String message; // error message

  }

}

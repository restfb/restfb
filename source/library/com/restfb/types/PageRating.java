package com.restfb.types;

import com.restfb.Facebook;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/v1.0/page/ratings">Cover Graph API type</a>.
 *
 * @author Anand Hemmige
 * @since 1.6.14
 */
public class PageRating extends FacebookType {

    @Facebook("created_time")
    private String createdTime;

    @Facebook("reviewer")
    private NamedFacebookType from;

    @Facebook("open_graph_story")
    private Post story;

    @Facebook("has_rating")
    private boolean hasRating;

    @Facebook("has_review")
    private boolean hasReview;
    @Facebook
    private int rating;

    @Facebook("review_text")
    private String review;


    public String getCreatedTime() {
        return createdTime;
    }

    public NamedFacebookType getFrom() {
        return from;
    }

    public Post getStory() {
        return story;
    }

    public boolean isHasRating() {
        return hasRating;
    }

    public boolean isHasReview() {
        return hasReview;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}

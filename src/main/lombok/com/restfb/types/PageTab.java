package com.restfb.types;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/tab/">Page Tab type</a>
 */
public class PageTab extends NamedFacebookType {

	private static final long serialVersionUID = 1L;

	/**
	 * The app that is in the tab
	 */
	@Getter
	@Setter
	@Facebook
	private Application application;

	/**
	 * URL for an image to be used as a custom icon
	 */
	@Getter
	@Setter
	@Facebook("custom_image_url")
	private String customImageUrl;

	/**
	 * Name to be used for the tab
	 */
	@Getter
	@Setter
	@Facebook("custom_name")
	private String customName;

	/**
	 * The location of the tab thumbnail image
	 */
	@Getter
	@Setter
	@Facebook("image_url")
	private String imageUrl;

	/**
	 * A flag to identify whether the tab is a custom landing tab for users who are not connected to this Page
	 */
	@Getter
	@Setter
	@Facebook("is_non_connection_landing_tab")
	private Boolean isNonConnectionLandingTab;

	/**
	 * A flag to identify whether the tab can be removed from the Page
	 */
	@Getter
	@Setter
	@Facebook("is_permanent")
	private Boolean isPermanent;

	/**
	 * A link directly to this Page tab
	 */
	@Getter
	@Setter
	@Facebook
	private String link;

	/**
	 * Where this tab is located in the list of tabs
	 */
	@Getter
	@Setter
	@Facebook
	private Integer position;

}

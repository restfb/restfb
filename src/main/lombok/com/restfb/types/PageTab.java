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

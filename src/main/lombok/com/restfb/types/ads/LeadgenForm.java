/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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
package com.restfb.types.ads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.Page;

import lombok.Getter;
import lombok.Setter;

public class LeadgenForm extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("cusomized_tcpa_content")
  private String cusomizedTcpaContent;

  @Getter
  @Setter
  @Facebook("expired_leads_count")
  private Long expiredLeadsCount;

  /**
   * The caption of the follow up action text on the final screen of the form.
   *
   * -- GETTER --
   *
   * @return The caption of the follow up action text on the final screen of the form.
   */
  @Getter
  @Setter
  @Facebook("follow_up_action_text")
  private String followUpActionText;

  /**
   * The destination of the follow up action text on the final screen of the form.
   *
   * -- GETTER --
   *
   * @return The destination of the follow up action text on the final screen of the form.
   */
  @Getter
  @Setter
  @Facebook("follow_up_action_url")
  private String followUpActionUrl;

  @Getter
  @Setter
  @Facebook("is_continued_flow")
  private Boolean isContinuedFlow;

  @Getter
  @Setter
  @Facebook("leadgen_export_csv_url")
  private String leadgenExportCsvUrl;

  @Getter
  @Setter
  @Facebook("leads_count")
  private Long leadsCount;

  @Getter
  @Setter
  @Facebook
  private String locale;

  @Getter
  @Setter
  @Facebook
  private Page page;

  /**
   * The ID of the page to which this form belongs.
   *
   * -- GETTER --
   *
   * @return The ID of the page to which this form belongs.
   */
  @Getter
  @Setter
  @Facebook("page_id")
  private String pageId;

  /**
   * The supplied privacy policy URL.
   *
   * -- GETTER --
   *
   * @return The supplied privacy policy URL.
   */
  @Getter
  @Setter
  @Facebook("privacy_policy_url")
  private String privacyPolicyUrl;

  @Getter
  @Setter
  @Facebook
  private List<LeadGenQualifier> qualifiers = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("tcpa_compliance")
  private Boolean tcpaCompliance;

}

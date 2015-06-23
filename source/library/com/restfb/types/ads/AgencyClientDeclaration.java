/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-account/agency-client-declaration/">Agency Client Declaration type</a>.
 */
class AgencyClientDeclaration {

  @Getter
  @Setter
  @Facebook("agency_representing_client")
  Integer agencyRepresentingClient;

  @Getter
  @Setter
  @Facebook("client_based_in_france")
  Integer clientBasedInFrance;

  @Getter
  @Setter
  @Facebook("client_city")
  String clientCity;

  @Getter
  @Setter
  @Facebook("client_country_code")
  String clientCountryCode;

  @Getter
  @Setter
  @Facebook("client_email_address")
  String clientEmailAddress;

  @Getter
  @Setter
  @Facebook("client_name")
  String clientName;

  @Getter
  @Setter
  @Facebook("client_postal_code")
  String clientPostalCode;

  @Getter
  @Setter
  @Facebook("client_province")
  String clientProvince;

  @Getter
  @Setter
  @Facebook("client_street")
  String clientStreet;

  @Getter
  @Setter
  @Facebook("client_street2")
  String clientStreet2;

  @Getter
  @Setter
  @Facebook("has_written_mandate_from_advertiser")
  Integer hasWrittenMandateFromAdvertiser;

  @Getter
  @Setter
  @Facebook("is_client_paying_invoices")
  Integer isClientPayingInvoices;
}

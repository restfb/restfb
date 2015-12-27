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
package com.restfb.types.ads;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-account/agency-client-declaration/">Agency
 * Client Declaration type</a>.
 */
class AgencyClientDeclaration {

  /**
   * Whether this account is for an agency representing a client.
   *
   * @return Whether this account is for an agency representing a client
   */
  @Getter
  @Setter
  @Facebook("agency_representing_client")
  Integer agencyRepresentingClient;

  /**
   * Whether the client is based in France.
   *
   * @return Whether the client is based in France
   */
  @Getter
  @Setter
  @Facebook("client_based_in_france")
  Integer clientBasedInFrance;

  /**
   * Client's city.
   *
   * @return Client's city
   */
  @Getter
  @Setter
  @Facebook("client_city")
  String clientCity;

  /**
   * Client's country code.
   *
   * @return Client's country code
   */
  @Getter
  @Setter
  @Facebook("client_country_code")
  String clientCountryCode;

  /**
   * Client's email address.
   *
   * @return Client's email address
   */
  @Getter
  @Setter
  @Facebook("client_email_address")
  String clientEmailAddress;

  /**
   * Name of the client.
   *
   * @return Name of the client
   */
  @Getter
  @Setter
  @Facebook("client_name")
  String clientName;

  /**
   * Client's postal code.
   *
   * @return Client's postal code
   */
  @Getter
  @Setter
  @Facebook("client_postal_code")
  String clientPostalCode;

  /**
   * Client's province.
   *
   * @return Client's province
   */
  @Getter
  @Setter
  @Facebook("client_province")
  String clientProvince;

  /**
   * First line of client's street address.
   *
   * @return First line of client's street address
   */
  @Getter
  @Setter
  @Facebook("client_street")
  String clientStreet;

  /**
   * Second line of client's street address.
   *
   * @return Second line of client's street address
   */
  @Getter
  @Setter
  @Facebook("client_street2")
  String clientStreet2;

  /**
   * Whether the agency has a written mandate to advertise on behalf of this client.
   *
   * @return Whether the agency has a written mandate to advertise on behalf of this client
   */
  @Getter
  @Setter
  @Facebook("has_written_mandate_from_advertiser")
  Integer hasWrittenMandateFromAdvertiser;

  /**
   * Whether the client is paying via invoice.
   *
   * @return Whether the client is paying via invoice
   */
  @Getter
  @Setter
  @Facebook("is_client_paying_invoices")
  Integer isClientPayingInvoices;
}

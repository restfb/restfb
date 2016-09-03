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

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/payment">Payment Graph API type</a>.
 */
public class Payment extends FacebookType {

  /**
   * The consumer's first and last name along with their user id. May be omitted in some cases.
   */
  @Getter
  @Setter
  @Facebook
  private User user;

  /**
   * The quantity of the product contained in the order
   */
  @Getter
  @Setter
  @Facebook
  private Integer quantity;

  /**
   * The unique, optional app-created identifier passed into the JS function (255 character maximum)
   */
  @Getter
  @Setter
  @Facebook("request_id")
  private String requestId;

  /**
   * The app associated with this payment
   */
  @Getter
  @Setter
  @Facebook
  private Application application;

  @Facebook
  private List<Action> actions = new ArrayList<Action>();

  @Facebook
  private List<Item> items = new ArrayList<Item>();

  /**
   * Buyer's ISO Country Code, for tax purposes.
   */
  @Getter
  @Setter
  @Facebook
  private String country;

  /**
   * The tax parameter specifies if a payment is subject to VAT and, if so, how the tax was paid.
   * <p>
   * This can take on the following values:
   * </p>
   * <ul>
   * <li>{@code not_taxed}: There's no VAT on this payment because it came from outside the European Union.</li>
   * <li>{@code already_paid}: The tax on this payment has already been paid by the user's mobile carrier or other
   * upstream party</li>
   * <li>{@code tax_remitted}: Facebook paid the VAT on this payment on your behalf. The tax amount will be deducted
   * from your payout.</li>
   * </ul>
   */
  @Getter
  @Setter
  @Facebook
  private String tax;

  /**
   * The country determined by Facebook for tax calculation of this purchase, given as an ISO 3166-1 alpha-2 country
   * code. Note: This field is for your information only and it should not be used for any recalculation or
   * reconciliation purposes. If tax is equal to not_taxed or already_paid this field will be omitted.
   */
  @Getter
  @Setter
  @Facebook("tax_country")
  private String taxCountry;

  /**
   * The time the payment was originally created.
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Exchange rate used to calculate payout amount which is remitted in USD.
   */
  @Getter
  @Setter
  @Facebook("payout_foreign_exchange_rate")
  private Double payoutForeignExchangeRate;

  @Facebook
  private List<Dispute> disputes = new ArrayList<Dispute>();

  /**
   * Optional parameter that shows up when a payment is made by a payment tester listed in the app's dashboard. This
   * represents a transaction that has not been charged to the consumer's payment instrument
   */
  @Getter
  @Setter
  @Facebook
  private Boolean test;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }

  /**
   * The items associated with the payment.
   */
  public static class Item extends AbstractFacebookType {

    /**
     * The type of this item.
     */
    @Getter
    @Setter
    @Facebook
    private String type;

    /**
     * The product URL of this item.
     */
    @Getter
    @Setter
    @Facebook
    private String product;

    /**
     * The number of this item purchased.
     */
    @Getter
    @Setter
    @Facebook
    private Integer quantity;

  }

  /**
   * The list of different action types that have occurred in this payment.
   */
  public static class Action extends AbstractFacebookType {

    /**
     * The type of this particular action. type can be:
     * <ul>
     * <li>{@code charge}: This designates the type of action that was taken on this payment was a charge. If the charge
     * has a status of completed, then you should fulfill the order.</li>
     * <li>{@code refund}: The refund type designates the payment has been refunded and the item sold to the consumer
     * should be retracted if possible as you will no longer be paid out for this transaction.</li>
     * <li>{@code chargeback}: A chargeback is initiated by a consumer with their bank disputing the payment in
     * question. When a chargeback occurs, you should retract the in-game item from the costumer where possible as you
     * will not be paid out for this order.</li>
     * <li>{@code chargeback_reversal}: A chargeback_reversal occurs when a chargeback is retroactively reversed. In
     * this case, you should reinstate the consumer's in-game item if possible as you will now be paid out for this
     * payment.</li>
     * <li>{@code decline}: A decline occurs when a funding source used to create a bundled transaction is declined at
     * the moment of processing the payment. You should retract the in-game item from the consumer where possible as you
     * will not be paid out for this order. More information on bundled transactions and declines can be found in
     * Handling Disputes and Refunds.</li>
     * </ul>
     */
    @Getter
    @Setter
    @Facebook
    private String type;

    /**
     * The status for this particular action. status can be:
     * <ul>
     * <li>{@code initiated}: An initiated payment designates the payment was only initiated but not yet fully
     * completed. You should not fulfill an order of this type with the consumer and you should not receive a real time
     * update for a payment with this status. You will however get a real time update when the corresponding transaction
     * is completed. Developers may see initiated state for certain payment methods which requires long time to settle,
     * for example, certain methods require the costumer to fill-in a form online, print out a receipt and go to the
     * bank offline to pay.</li>
     * <li>{@code completed}: A completed status means the action was successfully completed. If the type was a charge
     * you will want to fulfill the order to the consumer at this time. Similarly, if the action type was a refund you
     * will want to retract the item from the consumer.</li>
     * <li>{@code failed}: This designates a failed action for the underlying type. This value can be present for both a
     * charge action type as well as a refund action type.</li>
     * </ul>
     */
    @Getter
    @Setter
    @Facebook
    private String status;

    /**
     * The amount of money covered by this action.
     */
    @Getter
    @Setter
    @Facebook
    private String amount;

    /**
     * The currency of the above amount in this action.
     */
    @Getter
    @Setter
    @Facebook
    private String currency;

    /**
     * When this action occurred.
     */
    @Getter
    @Setter
    private Date timeCreated;

    @Facebook("time_created")
    private String rawTimeCreated;

    /**
     * When this action was last updated.
     */
    @Getter
    @Setter
    private Date timeUpdated;

    @Facebook("time_updated")
    private String rawTimeUpdated;

    /**
     * The amount reduced from your payout for any taxes remitted by Facebook. If {@code tax} is equal to
     * {@code not_taxed} or {@code already_paid} this field will be omitted.
     */
    @Getter
    @Setter
    @Facebook("tax_amount")
    private String taxAmount;

    @JsonMapper.JsonMappingCompleted
    void convertTime() {
      timeUpdated = toDateFromLongFormat(rawTimeUpdated);
      timeCreated = toDateFromLongFormat(rawTimeCreated);
    }
  }

  /**
   * Contains the information related to a dispute, including the user_comment and user_email which is provided by the
   * consumer when the dispute is initiated. Additionally contains the current status of the dispute, the time the
   * dispute was created an an resolution reason, if available.
   */
  public static class Dispute extends AbstractFacebookType {

    /**
     * Comment provided by the consumer when the dispute is initiated.
     */
    @Getter
    @Setter
    @Facebook("user_comment")
    private String userComment;

    /**
     * Email provided by the consumer when the dispute is initiated.
     */
    @Getter
    @Setter
    @Facebook("user_email")
    private String userEmail;

    /**
     * The time the dispute was created.
     */
    @Getter
    @Setter
    private Date timeCreated;

    @Facebook("time_created")
    private String rawTimeCreated;

    /**
     * Current status of the dispute.
     */
    @Getter
    @Setter
    @Facebook
    private String status;

    /**
     * The reason the developer or Facebook gave to resolve the dispute, after it has been resolved.
     */
    @Getter
    @Setter
    @Facebook
    private String reason;

    @JsonMapper.JsonMappingCompleted
    void convertTime() {
      timeCreated = toDateFromLongFormat(rawTimeCreated);
    }
  }

  /**
   * The items associated with the payment.
   * 
   * @return list of items
   */
  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public boolean addItem(Item item) {
    return items.add(item);
  }

  public boolean removeItem(Item item) {
    return items.remove(item);
  }

  /**
   * The list of different action types that have occurred in this payment.
   * 
   * @return list of actions
   */
  public List<Action> getActions() {
    return Collections.unmodifiableList(actions);
  }

  public boolean addAction(Action action) {
    return actions.add(action);
  }

  public boolean removeAction(Action action) {
    return actions.remove(action);
  }

  /**
   * Contains the information related to a dispute, including the {@code user_comment} and {@code user_email} which is provided by the
   * consumer when the dispute is initiated. Additionally contains the current status of the dispute, the time the
   * dispute was created an an resolution reason, if available.
   * 
   * @return list of disputes
   */
  public List<Dispute> getDisputes() {
    return Collections.unmodifiableList(disputes);
  }

  public boolean addDispute(Dispute dispute) {
    return disputes.add(dispute);
  }

  public boolean removeDispute(Dispute dispute) {
    return disputes.remove(dispute);
  }

}

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
package com.restfb.types.send;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class ReceiptTemplatePayload extends TemplatePayload {

    @Facebook("recipient_name")
    private String recipientName;

    @Facebook("order_number")
    private String orderNumber;

    @Facebook
    private String currency;

    @Facebook("payment_method")
    private String paymentMethod;

    @Setter
    @Facebook("order_url")
    private String orderUrl;

    @Setter
    @Facebook
    private Long timestamp;

    @Facebook
    private List<ReceiptElement> elements;

    @Setter
    @Facebook
    private ReceiptAddress address;

    @Facebook
    private ReceiptSummary summary;

    @Facebook
    private List<ReceiptAdjustment> adjustments;

    public ReceiptTemplatePayload(String recipientName, String orderNumber, String currency, String paymentMethod, ReceiptSummary summary) {
        this.recipientName = recipientName;
        this.orderNumber = orderNumber;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.summary = summary;
        setTemplateType("receipt");
    }

    public boolean addElement(ReceiptElement element) {
        if (elements == null) {
            elements = new ArrayList<ReceiptElement>();
        }

        return elements.add(element);
    }

    public boolean addAdjustment(ReceiptAdjustment adjustment) {
        if (adjustments == null) {
            adjustments = new ArrayList<ReceiptAdjustment>();
        }

        return adjustments.add(adjustment);
    }
}

package ru.marina.tshop.orders;

import java.util.List;

public class SubmitOrderRequest {
    private String address;
    private String deliveryMethodId;
    private String paymentMethodId;
    private List<CreateLineItem> lineItemList;

    private PaymentInformation paymentInformation;

    public SubmitOrderRequest() {
    }

    public SubmitOrderRequest(final String address,
                              final String deliveryMethodId,
                              final String paymentMethodId,
                              final List<CreateLineItem> lineItemList,
                              final PaymentInformation paymentInformation) {
        this.address = address;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.lineItemList = lineItemList;
        this.paymentInformation = paymentInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(final String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(final String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public List<CreateLineItem> getLineItemList() {
        return lineItemList;
    }

    public void setLineItemList(final List<CreateLineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }

    public PaymentInformation getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(final PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }
}
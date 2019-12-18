package ru.marina.tshop.orders.paymentgateway;

public class PaymentResult {
    private String paymentReference;

    public PaymentResult() {
    }

    public PaymentResult(final String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(final String paymentReference) {
        this.paymentReference = paymentReference;
    }
}

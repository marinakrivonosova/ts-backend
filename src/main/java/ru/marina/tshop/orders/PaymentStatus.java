package ru.marina.tshop.orders;

import java.util.Objects;

public class PaymentStatus {
    private String paymentStatusId;
    private String paymentStatus;

    public PaymentStatus(final String paymentStatusId, final String paymentStatus) {
        this.paymentStatusId = paymentStatusId;
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus() {
    }

    public String getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(final String paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return paymentStatusId.equals(that.paymentStatusId) &&
                paymentStatus.equals(that.paymentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentStatusId, paymentStatus);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "paymentStatusId='" + paymentStatusId + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

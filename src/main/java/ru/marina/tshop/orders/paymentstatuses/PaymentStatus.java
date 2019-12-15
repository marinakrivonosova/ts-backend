package ru.marina.tshop.orders.paymentstatuses;

import java.util.Objects;

public class PaymentStatus {
    private String id;
    private String paymentStatus;

    public PaymentStatus(final String paymentStatusId, final String paymentStatus) {
        this.id = paymentStatusId;
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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
        return id.equals(that.id) &&
                paymentStatus.equals(that.paymentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentStatus);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "paymentStatusId='" + id + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

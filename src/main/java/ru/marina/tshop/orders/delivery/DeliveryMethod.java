package ru.marina.tshop.orders.delivery;

import java.util.Objects;

public class DeliveryMethod {
    private String id;
    private String deliveryMethod;

    public DeliveryMethod(final String id, final String deliveryMethod) {
        this.id = id;
        this.deliveryMethod = deliveryMethod;
    }

    public DeliveryMethod() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(final String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMethod that = (DeliveryMethod) o;
        return id.equals(that.id) &&
                deliveryMethod.equals(that.deliveryMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryMethod);
    }

    @Override
    public String toString() {
        return "DeliveryMethod{" +
                "deliveryMethodId='" + id + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                '}';
    }
}
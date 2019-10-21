package entity;

import java.util.Objects;

public class DeliveryMethod {
    private String deliveryMethodId;
    private String deliveryMethod;

    public DeliveryMethod(String deliveryMethodId, String deliveryMethod) {
        this.deliveryMethodId = deliveryMethodId;
        this.deliveryMethod = deliveryMethod;
    }

    public DeliveryMethod(){
    }

    public String getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMethod that = (DeliveryMethod) o;
        return deliveryMethodId.equals(that.deliveryMethodId) &&
                deliveryMethod.equals(that.deliveryMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryMethodId, deliveryMethod);
    }
}

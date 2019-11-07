package orders;

import java.util.Objects;

public class PaymentMethod {
    private String id;
    private String paymentMethod;

    public PaymentMethod(final String id, final String paymentMethod) {
        this.id = id;
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return id.equals(that.id) &&
                paymentMethod.equals(that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentMethod);
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId='" + id + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
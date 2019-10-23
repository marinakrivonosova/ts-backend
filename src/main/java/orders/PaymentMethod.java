package orders;

import java.util.Objects;

public class PaymentMethod {
    private String paymentMethodId;
    private String paymentMethod;

    public PaymentMethod(final String paymentMethodId, final String paymentMethod) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethod = paymentMethod;
    }
     public  PaymentMethod(){}

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(final String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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
        return paymentMethodId.equals(that.paymentMethodId) &&
                paymentMethod.equals(that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodId, paymentMethod);
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "payment_method_id='" + paymentMethodId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}

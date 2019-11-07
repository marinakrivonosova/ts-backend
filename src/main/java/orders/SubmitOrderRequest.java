package orders;

import java.util.List;

public class SubmitOrderRequest {
    private List<String> productIds;
    private String address;
    private String deliveryMethodId;
    private String paymentMethodId;
    private List<CreateLineItem> lineItemList;

    public SubmitOrderRequest() {
    }

    public SubmitOrderRequest(final List<String> productIds, final String address, final String deliveryMethodId, final String paymentMethodId, final List<CreateLineItem> lineItemList) {
        this.productIds = productIds;
        this.address = address;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.lineItemList = lineItemList;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(final List<String> productIds) {
        this.productIds = productIds;
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
}
package ru.marina.tshop.orders.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(final PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/payment-methods")
    public List<PaymentMethod> listPaymentMethods() {
        return paymentMethodService.listPaymentMethods();
    }

    @GetMapping("/payment-methods/{paymentMethodId}")
    public PaymentMethod getPaymentMethod(@PathVariable("paymentMethodId") final String paymentMethodId) {
        return paymentMethodService.getPaymentMethod(paymentMethodId);
    }
}
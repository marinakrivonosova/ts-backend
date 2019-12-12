package ru.marina.tshop.orders.paymentstatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentStatusController {
    private final PaymentStatusService paymentStatusService;

    @Autowired
    public PaymentStatusController(final PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @GetMapping("/payment-statuses")
    public List<PaymentStatus> listPaymentStatus() {
        return paymentStatusService.listPaymentStatuses();
    }

    @GetMapping("/payment-statuses/{paymentStatusId}")
    public PaymentStatus getPaymentStatus(@PathVariable("paymentStatusId") final String paymentStatusId) {
        return paymentStatusService.getPaymentStatus(paymentStatusId);
    }
}

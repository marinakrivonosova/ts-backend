package ru.marina.tshop.orders.paymentstatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentStatusService {
    private final PaymentStatusDao paymentStatusDao;

    @Autowired
    public PaymentStatusService(final PaymentStatusDao paymentStatusDao) {
        this.paymentStatusDao = paymentStatusDao;
    }
    @Transactional(readOnly = true)
    public List<PaymentStatus> listPaymentStatuses() {
        return paymentStatusDao.listPaymentStatuses();
    }

    @Transactional(readOnly = true)
    public PaymentStatus getPaymentStatus(final String paymentStatusId) {
        return paymentStatusDao.getPaymentStatus(paymentStatusId);
    }
}

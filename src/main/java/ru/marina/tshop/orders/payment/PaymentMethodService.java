package ru.marina.tshop.orders.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentMethodService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodService.class);

    private final PaymentMethodDao paymentMethodDao;

    @Autowired
    public PaymentMethodService(final PaymentMethodDao paymentMethodDao) {
        this.paymentMethodDao = paymentMethodDao;
    }

    @Transactional(readOnly = true)
    public List<PaymentMethod> listPaymentMethods() {
        return paymentMethodDao.listPaymentMethods();
    }

    @Transactional(readOnly = true)
    public PaymentMethod getPaymentMethod(final String paymentMethodId) {
        return paymentMethodDao.getPaymentMethod(paymentMethodId);
    }
}

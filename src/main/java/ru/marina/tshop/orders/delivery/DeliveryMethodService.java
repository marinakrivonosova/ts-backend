package ru.marina.tshop.orders.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryMethodService {
    private final DeliveryMethodDao deliveryMethodDao;

    @Autowired
    public DeliveryMethodService(final DeliveryMethodDao deliveryMethodDao) {
        this.deliveryMethodDao = deliveryMethodDao;
    }

    @Transactional(readOnly = true)
    public List<DeliveryMethod> listDeliveryMethods() {
        return deliveryMethodDao.listDeliveryMethods();
    }

    @Transactional(readOnly = true)
    public DeliveryMethod getDeliveryMethod(final String deliveryMethodId) {
        return deliveryMethodDao.getDeliveryMethod(deliveryMethodId);
    }
}

package ru.marina.tshop.orders.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;

    @Autowired
    public DeliveryMethodController(DeliveryMethodService deliveryMethodService) {
        this.deliveryMethodService = deliveryMethodService;
    }

    @GetMapping("/delivery-methods")
    public List<DeliveryMethod> listDeliveryMethods() {
        return deliveryMethodService.listDeliveryMethods();
    }

    @GetMapping("/delivery-methods/{deliveryMethodId}")
    public DeliveryMethod getDeliveryMethod(@PathVariable("deliveryMethodId") final String deliveryMethodId) {
        return deliveryMethodService.getDeliveryMethod(deliveryMethodId);
    }
}

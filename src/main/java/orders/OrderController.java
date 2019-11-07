package orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitOrder(@RequestBody final SubmitOrderRequest request) {
        //TODO fill when know
        final String userId = "";
        orderService.addOrder(userId, request.getLineItemList(), request.getAddress(), request.getDeliveryMethodId(), request.getPaymentMethodId());
    }

    @GetMapping("/payment-methods")
    public List<PaymentMethod> getPaymentMethods() {
        return orderService.getPaymentMethods();
    }

    @GetMapping("/orders")
    public List<Order> listOrdersByStatus(@RequestParam("status") final String statusId) {
        return orderService.listOrdersByStatus(statusId);
    }

    @PutMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@PathVariable("orderId") final String orderId, @RequestBody final UpdateOrderRequest request) {
        // TODO implement
    }
}
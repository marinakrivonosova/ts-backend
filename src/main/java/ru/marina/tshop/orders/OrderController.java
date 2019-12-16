package ru.marina.tshop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.orderstatuses.OrderStatus;
import ru.marina.tshop.users.UserService;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;


    @Autowired
    public OrderController(final OrderService orderService, final UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public String submitOrder(@RequestBody final SubmitOrderRequest request) {
        final String userId = userService.getCurrentUserId();
        return orderService.addOrder(userId, request.getLineItemList(), request.getAddress(), request.getDeliveryMethodId(), request.getPaymentMethodId());
    }

    @GetMapping("/order-statuses")
    public List<OrderStatus> getOrderStatuses() {
        return orderService.getOrderStatuses();
    }

    @GetMapping("/orders")
    public List<Order> listOrdersByStatus(@RequestParam("status") final String statusId) {
        return orderService.listOrdersByStatus(statusId, userService.getCurrentUserId());
    }

    @PutMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@PathVariable("orderId") final String orderId, @RequestBody final UpdateOrderRequest request) {
        orderService.updateOrder(orderId, request.getOrderStatusId());
    }

    @GetMapping("/orders/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}/line-items")
    public List<LineItem> listLineItems(@PathVariable("orderId") final String orderId) {
        return orderService.listLineItems(orderId);
    }
    @GetMapping("/orders/all/{orderId}")
    public Order getOrder(@PathVariable("orderId") final String orderId){
        return orderService.getOrder(orderId);
    }
}
package ru.marina.tshop.orders;

import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.lineitems.LineItemDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marina.tshop.orders.orderstatuses.OrderStatus;
import ru.marina.tshop.orders.orderstatuses.OrderStatusDao;
import ru.marina.tshop.products.Product;
import ru.marina.tshop.products.ProductDao;
import ru.marina.tshop.utils.IdGenerator;

import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderDao orderDao;
    private final IdGenerator idGenerator;
    private final LineItemDao lineItemDao;
    private final ProductDao productDao;
    private final Configuration configuration;
    private final OrderStatusDao orderStatusDao;

    @Autowired
    public OrderService(final OrderDao orderDao, final IdGenerator idGenerator, final LineItemDao lineItemDao, final ProductDao productDao, final Configuration configuration, final OrderStatusDao orderStatusDao) {
        this.orderDao = orderDao;
        this.idGenerator = idGenerator;
        this.lineItemDao = lineItemDao;
        this.productDao = productDao;
        this.configuration = configuration;
        this.orderStatusDao = orderStatusDao;
    }

    public String addOrder(final String userId, final List<CreateLineItem> createLineItemList, final String address, final String deliveryMethodId, final String paymentMethodId) {
        if (createLineItemList == null || createLineItemList.isEmpty()) {
            throw new IllegalArgumentException("CreateLineItem parameter cannot be null or empty.");
        }
        final String orderId = idGenerator.generateId();
        orderDao.addOrder(new Order(
                orderId,
                userId,
                address,
                orderStatusDao.getOrderStatusId(configuration.getInitialOrderStatus()),
                deliveryMethodId,
                paymentMethodId,
                configuration.getNotPaidPaymentStatusId()));

        for (final CreateLineItem createLineItem : createLineItemList) {
            final Product product = productDao.getProduct(createLineItem.getProductId());

            if (createLineItem.getCount() > product.getCount()) {
                throw new IllegalArgumentException("Not enough of product '" + createLineItem.getProductId() + "' in stock");
            }
            final String lineItemId = idGenerator.generateId();
            lineItemDao.addLineItem(new LineItem(
                    lineItemId,
                    orderId,
                    createLineItem.getProductId(),
                    createLineItem.getCount(),
                    product.getPrice()));

            product.setCount(product.getCount() - createLineItem.getCount());
            productDao.updateProduct(product);
        }
        return orderId;
    }

    public List<OrderStatus> getOrderStatuses() {
        return orderStatusDao.listOrderStatuses();
    }

    public List<Order> listOrdersByStatus(final String orderStatusId) {
        return orderDao.listOrdersByStatus(orderStatusId);
    }
}
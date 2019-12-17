package ru.marina.tshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.marina.tshop.orders.lineitems.LineItem;
import ru.marina.tshop.orders.lineitems.LineItemDao;
import ru.marina.tshop.orders.orderstatuses.OrderStatus;
import ru.marina.tshop.orders.orderstatuses.OrderStatusDao;
import ru.marina.tshop.orders.paymentgateway.PaymentGatewayService;
import ru.marina.tshop.products.Product;
import ru.marina.tshop.products.ProductDao;
import ru.marina.tshop.utils.IdGenerator;
import ru.marina.tshop.utils.UniqueSeq;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final IdGenerator idGenerator;
    private final UniqueSeq uniqueSeq;
    private final LineItemDao lineItemDao;
    private final ProductDao productDao;
    private final Configuration configuration;
    private final OrderStatusDao orderStatusDao;
    private final PaymentGatewayService paymentGatewayService;

    @Autowired
    public OrderService(final OrderDao orderDao, final IdGenerator idGenerator, final UniqueSeq uniqueSeq, final LineItemDao lineItemDao, final ProductDao productDao, final Configuration configuration, final OrderStatusDao orderStatusDao, final PaymentGatewayService paymentGatewayService) {
        this.orderDao = orderDao;
        this.uniqueSeq = uniqueSeq;
        this.idGenerator = idGenerator;
        this.lineItemDao = lineItemDao;
        this.productDao = productDao;
        this.configuration = configuration;
        this.orderStatusDao = orderStatusDao;
        this.paymentGatewayService = paymentGatewayService;
    }

    @Transactional
    public String addOrder(final String userId, final List<CreateLineItem> createLineItemList, final String address, final String deliveryMethodId, final String paymentMethodId, final PaymentInformation paymentInformation) {
        if (createLineItemList == null || createLineItemList.isEmpty()) {
            throw new IllegalArgumentException("CreateLineItem parameter cannot be null or empty.");
        }
        final String orderId = idGenerator.generateId();
        final String orderNumber = uniqueSeq.getNext();
        orderDao.addOrder(new Order(
                orderId,
                orderNumber,
                userId,
                address,
                orderStatusDao.getOrderStatusId(configuration.getInitialOrderStatus()),
                deliveryMethodId,
                paymentMethodId,
                configuration.getPaidPaymentStatusId()));

        BigDecimal amount = BigDecimal.ZERO;
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

            amount = amount.add(product.getPrice().multiply(BigDecimal.valueOf(createLineItem.getCount())));
        }

        paymentGatewayService.doPayment(paymentInformation.getCardNumber(), paymentInformation.getCvc(), paymentInformation.getExpirationDate(), paymentInformation.getCardHolder(), amount);

        return orderId;
    }

    public List<OrderStatus> getOrderStatuses() {
        return orderStatusDao.listOrderStatuses();
    }

    @Transactional(readOnly = true)
    public List<Order> listOrdersByStatus(final String orderStatusId, final String userId) {
        return orderDao.listOrdersByStatus(orderStatusId, userId);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Transactional
    public void updateOrder(final String orderId, final String orderStatusId) {
        orderDao.updateOrder(orderId, orderStatusId);
    }

    @Transactional(readOnly = true)
    public List<LineItem> listLineItems(final String orderId) {
        return lineItemDao.listLineItems(orderId);
    }

    @Transactional(readOnly = true)
    public Order getOrder(final String orderId) {
        return orderDao.getOrder(orderId);
    }
}
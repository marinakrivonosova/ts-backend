package ru.marina.tshop.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
public class OrderTransactionTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @Test
    public void addOrder() {
        final int orderCountBeforeTransaction = orderDao.getAllOrders().size();
        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(
                "uId1",
                asList(
                        new CreateLineItem("prId1", 3),
                        new CreateLineItem("prId2", 1),
                        new CreateLineItem("prId3", 1000)),
                "address",
                "dmId1",
                "pmId2"));

        assertEquals(orderCountBeforeTransaction, orderDao.getAllOrders().size());
    }
}
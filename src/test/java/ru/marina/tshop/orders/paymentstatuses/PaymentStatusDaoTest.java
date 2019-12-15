package ru.marina.tshop.orders.paymentstatuses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
public class PaymentStatusDaoTest {
    @Autowired
    private PaymentStatusDao paymentStatusDao;

    @Test
    void listPaymentStatus() {
        final List<PaymentStatus> paymentStatuses = Arrays.asList(
                new PaymentStatus("psId1", "paid"),
                new PaymentStatus("psId2", "not paid"));

        assertEquals(paymentStatuses, paymentStatusDao.listPaymentStatuses());
    }

    @Test
    void getPaymentStatus() {
        assertEquals(new PaymentStatus("psId1", "paid"), paymentStatusDao.getPaymentStatus("psId1"));
        assertEquals(new PaymentStatus("psId2", "not paid"), paymentStatusDao.getPaymentStatus("psId2"));
    }
}

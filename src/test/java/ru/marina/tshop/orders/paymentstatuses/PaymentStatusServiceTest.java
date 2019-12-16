package ru.marina.tshop.orders.paymentstatuses;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PaymentStatusServiceTest {

    private PaymentStatusDao paymentStatusDao = mock(PaymentStatusDao.class);
    private PaymentStatusService paymentStatusService = new PaymentStatusService(paymentStatusDao);

    @Test
    void listPaymentStatus() {
        final List<PaymentStatus> paymentStatuses = Arrays.asList(
                new PaymentStatus("psId1", "paid"),
                new PaymentStatus("psId2", "not paid"));
        when(paymentStatusDao.listPaymentStatuses()).thenReturn(paymentStatuses);

        assertEquals(paymentStatuses, paymentStatusService.listPaymentStatuses());
    }

    @Test
    void getPaymentStatus() {
        when(paymentStatusDao.getPaymentStatus("psId1")).thenReturn(new PaymentStatus("psId1", "paid"));

        assertEquals(new PaymentStatus("psId1", "paid"), paymentStatusService.getPaymentStatus("psId1"));
    }
}

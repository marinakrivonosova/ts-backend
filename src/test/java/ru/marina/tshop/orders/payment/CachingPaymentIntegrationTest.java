package ru.marina.tshop.orders.payment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CachingPaymentIntegrationTest {
    private static PaymentMethodDao dao = mock(PaymentMethodDao.class);

    @Configuration
    @EnableCaching
    static class Config {
        // Simulating your caching configuration
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("paymentMethods", "paymentMethodsAll");
        }

        // A repository mock instead of the real proxy
        @Bean
        PaymentMethodDao myRepo() {
            return dao;
        }
    }

    @Autowired
    private PaymentMethodDao repo;

    @Test
    public void listDeliveryMethods() {
        final List<PaymentMethod> paymentMethods = Collections.singletonList(new PaymentMethod("id1", "cash"));

        when(dao.listPaymentMethods()).thenReturn(paymentMethods);

        assertEquals(paymentMethods, repo.listPaymentMethods());
        assertEquals(paymentMethods, repo.listPaymentMethods());

        verify(dao, times(1)).listPaymentMethods();
    }

    @Test
    public void getDeliveryMethod() {
        final PaymentMethod paymentMethod = new PaymentMethod("id1", "card");

        when(dao.getPaymentMethod(any())).thenReturn(paymentMethod);

        assertEquals(paymentMethod, repo.getPaymentMethod("id1"));
        assertEquals(paymentMethod, repo.getPaymentMethod("id1"));

        verify(dao, times(1)).getPaymentMethod("id1");
    }
}

package ru.marina.tshop.orders.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class CachingIntegrationTest {
    private static DeliveryMethodDao dao = mock(DeliveryMethodDao.class);

    @Configuration
    @EnableCaching
    static class Config {
        // Simulating your caching configuration
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("deliveryMethods", "deliveryMethodsAll");
        }

        // A repository mock instead of the real proxy
        @Bean
        DeliveryMethodDao myRepo() {
            return dao;
        }
    }

    @Autowired
    private DeliveryMethodDao repo;

    @Test
    public void listDeliveryMethods() {
        final List<DeliveryMethod> deliveryMethods = Collections.singletonList(new DeliveryMethod("id1", "post"));

        when(dao.listDeliveryMethods()).thenReturn(deliveryMethods);

        assertEquals(deliveryMethods, repo.listDeliveryMethods());
        assertEquals(deliveryMethods, repo.listDeliveryMethods());

        verify(dao, times(1)).listDeliveryMethods();
    }

    @Test
    public void getDeliveryMethod() {
        final DeliveryMethod deliveryMethod = new DeliveryMethod("id1", "post");

        when(dao.getDeliveryMethod(any())).thenReturn(deliveryMethod);

        assertEquals(deliveryMethod, repo.getDeliveryMethod("id1"));
        assertEquals(deliveryMethod, repo.getDeliveryMethod("id1"));

        verify(dao, times(1)).getDeliveryMethod("id1");
    }
}
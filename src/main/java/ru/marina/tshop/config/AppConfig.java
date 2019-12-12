package ru.marina.tshop.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableCaching
public class AppConfig {
    @Bean
    public net.sf.ehcache.CacheManager ehCache() {
        final CacheConfiguration deliveryMethodsConfig = new CacheConfiguration("deliveryMethods", 10);
        final CacheConfiguration deliveryMethodsAllConfig = new CacheConfiguration("deliveryMethodsAll", 10);
        final CacheConfiguration paymentMethodsConfig = new CacheConfiguration("paymentMethods", 10);
        final CacheConfiguration paymentMethodsAllConfig = new CacheConfiguration("paymentMethodsAll", 10);
        final CacheConfiguration paymentStatusesAllConfig = new CacheConfiguration("paymentStatusesAll", 10);
        final CacheConfiguration paymentStatusesConfig = new CacheConfiguration("paymentStatuses", 10);
        deliveryMethodsConfig.setTimeToLiveSeconds(1000);
        deliveryMethodsAllConfig.setTimeToLiveSeconds(1000);
        paymentMethodsConfig.setTimeToLiveSeconds(1000);
        paymentMethodsAllConfig.setTimeToLiveSeconds(1000);
        paymentStatusesAllConfig.setTimeToLiveSeconds(1000);
        paymentStatusesConfig.setTimeToLiveSeconds(1000);


        final net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        configuration.addCache(deliveryMethodsConfig);
        configuration.addCache(deliveryMethodsAllConfig);
        configuration.addCache(paymentMethodsAllConfig);
        configuration.addCache(paymentMethodsConfig);
        configuration.addCache(paymentStatusesAllConfig);
        configuration.addCache(paymentStatusesConfig);

        return net.sf.ehcache.CacheManager.create(configuration);
    }

    @Bean
    public CacheManager cacheManager(final net.sf.ehcache.CacheManager ehCache) {
        return new EhCacheCacheManager(ehCache);
    }

    @Bean
    public Clock cLock() {
        return Clock.systemDefaultZone();
    }
}

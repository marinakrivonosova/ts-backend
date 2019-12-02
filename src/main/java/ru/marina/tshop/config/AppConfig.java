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
        final CacheConfiguration deliveryMethodsConfiguration = new CacheConfiguration("deliveryMethods", 10);
        deliveryMethodsConfiguration.setTimeToLiveSeconds(10);

        final net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        configuration.addCache(deliveryMethodsConfiguration);

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

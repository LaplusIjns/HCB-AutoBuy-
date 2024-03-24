package immargin.hardware.hcb.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

    @Bean
    @Primary
    CacheManager compositeCacheManager(CacheManager[] cacheManagers) {
        return new CompositeCacheManager(cacheManagers);
    }
    
    /**
     * 每天新商品每天異動 久久更新一次
     */
    @Bean(Constant.CACHE_DAILY)
    CacheManager caffeineCacheManagerDaily() {
        CaffeineCacheManager caffeineCacheManagerDaily = new CaffeineCacheManager();
        caffeineCacheManagerDaily.setCaffeine(Caffeine.newBuilder().expireAfterWrite(Duration.ofDays(60)));
        caffeineCacheManagerDaily.setAllowNullValues(true);
        return caffeineCacheManagerDaily;
    }
    
    /**
     * 單一商品價格趨勢 每兩天需要刷新
     */
    @Bean(Constant.CACHE_PRICE)
    CacheManager caffeineCacheManagerPrice() {
        CaffeineCacheManager caffeineCacheManagerPrice = new CaffeineCacheManager();
        caffeineCacheManagerPrice.setCaffeine(Caffeine.newBuilder().expireAfterWrite(Duration.ofDays(2)));
        caffeineCacheManagerPrice.setAllowNullValues(true);
        return caffeineCacheManagerPrice;
    }
}

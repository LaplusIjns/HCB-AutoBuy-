package immargin.hardware.hcb.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import immargin.hardware.hcb.autobuy.AutobuyMaintableService;
import immargin.hardware.hcb.config.CommonUtils;
import immargin.hardware.hcb.sinya.SinyaMaintableService;

@Service
public class CacheService {
    
    
    private static final Logger log = LoggerFactory.getLogger(CacheService.class);


    @Autowired
    CacheManager caffeineCacheManagerDaily;
    
    @Autowired
    AutobuyMaintableService autobuyMaintableService;
    
    @Autowired
    SinyaMaintableService sinyaMaintableService;
    
    /**
     * 每天下午四點更新當天緩存 避免新資料未更新
     */
    @Scheduled(cron="0 0 16 * * *")
    public void cleanCache() {
        Collection<String> collection = caffeineCacheManagerDaily.getCacheNames();
        collection.parallelStream().map(t -> caffeineCacheManagerDaily.getCache(t) ).forEach(t ->{
            log.info("緩存: {} 開始清理",t.getName());
            t.evictIfPresent(CommonUtils.nowDateString());
        } );
        sinyaMaintableService.SinyaDailyNew(0, CommonUtils.nowDateString());
        autobuyMaintableService.dailyNew(0, CommonUtils.nowDateString());
        
    }
    
//    @Scheduled(cron="0/10 * * * * *")
//    public void cleanCache2() {
//        Collection<String> collection = caffeineCacheManagerDaily.getCacheNames();
//        collection.parallelStream().map(t -> caffeineCacheManagerDaily.getCache(t) ).forEach(t ->{
//            System.out.println(t.get(CommoUtils.DateString(-2)));
//        } );
//    }
}

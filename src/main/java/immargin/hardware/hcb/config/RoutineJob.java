package immargin.hardware.hcb.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.sinya.SinyaMaintableRepository;
import immargin.hardware.hcb.sinya.SinyaMaintableService;

@Component
public class RoutineJob {
    
    
    private static final Logger log = LoggerFactory.getLogger(RoutineJob.class);

    
    @Autowired
    SinyaMaintableService sinyaMaintableService; 
    
    @Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;
    
    /**
     * 最後更新 大於等於 7 天 而且 顯示有貨
     * 改為無貨狀態 
     */
    @Scheduled(cron="0 0 1 * * MON,FRI ")
    public void updateProdavailableToFalse() {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, -7);
        Date sevenDay = calender.getTime();
        FormData formData = new FormData();
        formData.setEndUpdate(sevenDay);
        formData.setExpiredProd(true);
        formData.setPage(1);
        Page<Sinyamaintable>  pageSinyamaintable = sinyaMaintableService.getSinyamaintablePage(formData);
        if(pageSinyamaintable.isEmpty()) {
            log.info("無資料要做更改無貨");
            return;
        }
        List<Sinyamaintable> listSinyamaintable = pageSinyamaintable.getContent();
        List<Sinyamaintable> result = new ArrayList<>();
        log.info("sinya 共有 {} 筆資料須改更改為沒貨",listSinyamaintable.size());
        
        for(int i = 0 ; i < listSinyamaintable.size() ; i++) {
            if(listSinyamaintable.get(i).getProdavailable().intValue()==1) {
                listSinyamaintable.get(i).setProdavailable(0);
                result.add(listSinyamaintable.get(i));
            }
        }
        
        for(int i = 2; i < pageSinyamaintable.getTotalPages();i++) {
            formData.setPage(i);
            listSinyamaintable = sinyaMaintableService.getSinyamaintablePage(formData).getContent();

            for(int j = 0 ; j < listSinyamaintable.size() ; j++) {
                if(listSinyamaintable.get(j).getProdavailable().intValue()==1) {
                    listSinyamaintable.get(j).setProdavailable(0);
                    result.add(listSinyamaintable.get(j));
                }
            }
        }
        List<Sinyamaintable> result2 = sinyaMaintableRepository.saveAllAndFlush(result);
        log.info("sinya 共有 {} 筆資料標記為沒貨",result2.size());
        for (Sinyamaintable sinyamaintable : result2) {
            log.info("sinya 異動產品編號: {} 產品名字: {}",sinyamaintable.getProdId(),sinyamaintable.getProdname());
        }
    }
    
    @Scheduled(cron="0 0 1 */2 * * ")
    public void updateProdavailableToTrue() {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, -2);
        Date twoDay = calender.getTime();
        FormData formData = new FormData();
        formData.setStartUpdate(twoDay);
        formData.setInsideMode(true);
        formData.setPage(1);
        Page<Sinyamaintable>  pageSinyamaintable = sinyaMaintableService.getSinyamaintablePage(formData);
        if(pageSinyamaintable.isEmpty()) {
            log.info("sinya 無資料要做更改為有貨");
            return;
        }
        List<Sinyamaintable> listSinyamaintable = pageSinyamaintable.getContent();
        List<Sinyamaintable> result = new ArrayList<>();
        log.info("sinya 共有 {} 筆資料須改更改為有貨",listSinyamaintable.size());
        
        for(int i = 0 ; i < listSinyamaintable.size() ; i++) {
            if(listSinyamaintable.get(i).getProdavailable().intValue()==0) {
                listSinyamaintable.get(i).setProdavailable(1);
                result.add(listSinyamaintable.get(i));
            }
        }
        
        for(int i = 2; i < pageSinyamaintable.getTotalPages();i++) {
            formData.setPage(i);
            listSinyamaintable = sinyaMaintableService.getSinyamaintablePage(formData).getContent();

            for(int j = 0 ; j < listSinyamaintable.size() ; j++) {
                if(listSinyamaintable.get(j).getProdavailable().intValue()==0) {
                    listSinyamaintable.get(j).setProdavailable(1);
                    result.add(listSinyamaintable.get(j));
                }
            }
        }
        List<Sinyamaintable> result2 = sinyaMaintableRepository.saveAllAndFlush(result);
        log.info("sinya 共有 {} 筆資料標記為有貨",result2.size());
        for (Sinyamaintable sinyamaintable : result2) {
            log.info("sinya 異動產品編號: {} 產品名字: {}",sinyamaintable.getProdId(),sinyamaintable.getProdname());
        }
    }
}

package immargin.hardware.hcb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import immargin.hardware.hcb.DTO.DailyDTO2;
import immargin.hardware.hcb.DTO.LastDTO2;
import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.repository.LastJDBCTemplate;

@Service
public class LastService {
	
	@Autowired
	LastJDBCTemplate lastJDBCTemplate;
	
//	AutoBuy
	public List<LastDTO2> getProd(String prodname){
	    String lastone = prodname.substring(prodname.length()-1);
		return lastJDBCTemplate.getProd("last_".concat(lastone), prodname);
	}
	   @Cacheable(value="getDaily", key="#cacheDateString", cacheManager = Constant.CACHE_DAILY)
	   public List<DailyDTO2> getDaily(Integer index,String cacheDateString){
	        int index2 = index+1;
	        return lastJDBCTemplate.getDaily(index, index2);
	    }
//	Sinya
	public List<LastDTO2> SinyagetProd(String prodname){
        String lastone = prodname.substring(prodname.length()-1);
        return lastJDBCTemplate.getSinyaProd("slast_".concat(lastone), prodname);
    }
	@Cacheable(value="getSinyaDaily", key="#cacheDateString", cacheManager = Constant.CACHE_DAILY)
	public List<DailyDTO2> getSinyaDaily(Integer index,String cacheDateString){
        int index2 = index+1;
        return lastJDBCTemplate.getSinyaDaily(index, index2);
    }
	
}

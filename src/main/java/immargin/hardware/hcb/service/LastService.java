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
import immargin.hardware.hcb.repository.LastJDBCTemplate;

@Service
public class LastService {
	
	@Autowired
	LastJDBCTemplate lastJDBCTemplate;
	
//	AutoBuy
	public List<LastDTO2> getProd(String prodname){
	    String lastone = prodname.substring(prodname.length()-1);
		List<LastDTO2> result = null;
		result = lastJDBCTemplate.getProd("last_".concat(lastone), prodname);
		return result;
	}
	   @Cacheable(value="getDaily", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
	   public List<DailyDTO2> getDaily(Integer index,String cacheDateString,String nowString){
	        int index2 = index+1;
	        List<DailyDTO2> result = null;
	        result = lastJDBCTemplate.getDaily(index, index2);
	        return result;
	    }
//	Sinya
	public List<LastDTO2> SinyagetProd(String prodname){
        String lastone = prodname.substring(prodname.length()-1);
        List<LastDTO2> result = null;
        result = lastJDBCTemplate.getSinyaProd("slast_".concat(lastone), prodname);
        return result;
    }
	@Cacheable(value="getSinyaDaily", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
	public List<DailyDTO2> getSinyaDaily(Integer index,String cacheDateString,String nowString){
        int index2 = index+1;
        List<DailyDTO2> result = null;
        result = lastJDBCTemplate.getSinyaDaily(index, index2);
        return result;
    }
	
}

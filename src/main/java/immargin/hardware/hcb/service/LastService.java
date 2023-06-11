package immargin.hardware.hcb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import immargin.hardware.hcb.DTO.DailyDTO;
import immargin.hardware.hcb.DTO.LastDTO;
import immargin.hardware.hcb.repository.LastRepository;

@Service
public class LastService {
	@Autowired
	private LastRepository lastRepository;
	
//	AutoBuy
	public List<LastDTO> getProd(String prodname){
	    int lastone = Integer.parseInt(prodname.substring(prodname.length()-1));
		List<LastDTO> result = null;
		result = lastRepository.getProd(lastone, prodname);
		return result;
	}
	   @Cacheable(value="getDaily", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
	   public List<DailyDTO> getDaily(Integer index,String cacheDateString,String nowString){
	        int index2 = index+1;
	        List<DailyDTO> result = null;
	        result = lastRepository.getDaily(index, index2);
	        return result;
	    }
//	Sinya
	public List<LastDTO> SinyagetProd(String prodname){
        int lastone = Integer.parseInt(prodname.substring(prodname.length()-1));
        List<LastDTO> result = null;
        result = lastRepository.getSinyaProd(lastone, prodname);
        return result;
    }
	@Cacheable(value="getSinyaDaily", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
	public List<DailyDTO> getSinyaDaily(Integer index,String cacheDateString,String nowString){

        int index2 = index+1;
        List<DailyDTO> result = null;
        result = lastRepository.getSinyaDaily(index, index2);
        return result;
    }
	
}

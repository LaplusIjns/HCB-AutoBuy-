package immargin.hardware.HCB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.DTO.DailyDTO;
import immargin.hardware.HCB.DTO.LastDTO;
import immargin.hardware.HCB.repository.LastRepository;

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
	   public List<DailyDTO> getDaily(Integer index){
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
	public List<DailyDTO> getSinyaDaily(Integer index){
        int index2 = index+1;
        List<DailyDTO> result = null;
        result = lastRepository.getSinyaDaily(index, index2);
        return result;
    }
	
}

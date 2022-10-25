package immargin.hardware.HCB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.DTO.LastDTO;
import immargin.hardware.HCB.repository.LastRepository;

@Service
public class LastService {
	@Autowired
	private LastRepository lastRepository;
	
	public List<LastDTO> getProd(String prodname){
	    int lastone = Integer.parseInt(prodname.substring(prodname.length()-1));
		List<LastDTO> result = null;
		result = lastRepository.getProd(lastone, prodname);
		return result;
	}
	
}

package immargin.hardware.HCB.autobuy;


import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import immargin.hardware.HCB.DTO.MaintableDTO;

import immargin.hardware.HCB.sinya.SinyaMaintableRepository;


@Service
public class AutobuyMaintableService {
	
	
	@Autowired
	private MaintableRepository maintableRepository;
	@Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;
//	AutoBuy
	//搜尋欄
	public List<MaintableDTO> blurSearchMaintable(String prodname,int page,int size){
		List<MaintableDTO> pageResult = null;
		Pageable pageable=PageRequest.of(page, size);
		String[] newStr = prodname.split("\\s+");
		if(newStr.length==1) {
		pageResult =maintableRepository.findByName(prodname,pageable).getContent();
		}else if(newStr.length==2){
		pageResult =maintableRepository.findByName2(newStr[0],newStr[1],pageable).getContent();
		}else if(newStr.length==3){
			pageResult =maintableRepository.findByName3(newStr[0],newStr[1],newStr[2],pageable).getContent();
		}else if(newStr.length==4){
            pageResult =maintableRepository.findByName4(newStr[0],newStr[1],newStr[2],newStr[3],pageable).getContent();
        }else if(newStr.length==5){
            pageResult =maintableRepository.findByName5(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],pageable).getContent();
        }else {
            pageResult =maintableRepository.findByName6(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],newStr[5],pageable).getContent();
        }
		return pageResult;
	}
	
	//搜尋總頁數元素
	public int[] gettotal(String prodname,int page,int size){
        Page<MaintableDTO> pageResult = null;
        int[] abc= new int[2];
        Pageable pageable=PageRequest.of(page, size);
        String[] newStr = prodname.split("\\s+");
        if(newStr.length==1) {
        pageResult =maintableRepository.findByName(prodname,pageable);
        }else if(newStr.length==2){
        pageResult =maintableRepository.findByName2(newStr[0],newStr[1],pageable);
        }else if(newStr.length==3){
            pageResult =maintableRepository.findByName3(newStr[0],newStr[1],newStr[2],pageable);
        }else if(newStr.length==4){
            pageResult =maintableRepository.findByName4(newStr[0],newStr[1],newStr[2],newStr[3],pageable);
        }else if(newStr.length==5){
            pageResult =maintableRepository.findByName5(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],pageable);
        }else {
            pageResult =maintableRepository.findByName6(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],newStr[5],pageable);
        }
        pageResult.getTotalPages();
        abc[0] = pageResult.getTotalPages();
        abc[1] =(int) pageResult.getTotalElements();
        return abc;
    }
	
	public Optional<MaintableDTO> getProdname(String id) {
	    Optional<MaintableDTO> result = null;
	    result = maintableRepository.findMaintableDTOByProd_id(id);
	    return result;
	}
	@Cacheable(value="DailyNew", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
    public List<MaintableDTO> DailyNew(Integer index,String cacheDateString,String nowString){
        List<MaintableDTO> Result = null;
        Result = maintableRepository.DailyNew(index);
        return Result;
    }
	


}

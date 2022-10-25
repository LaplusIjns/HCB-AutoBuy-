package immargin.hardware.HCB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.repository.MaintableRepository;

@Service
public class MaintableService {
	
	
	@Autowired
	private MaintableRepository maintableRepository;
	
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
		System.out.println(maintableRepository.findByName(prodname,pageable).getTotalPages());
		System.out.println(maintableRepository.findByName(prodname,pageable).getTotalElements() );
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

}

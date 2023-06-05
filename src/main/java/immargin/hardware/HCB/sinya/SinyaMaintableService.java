package immargin.hardware.HCB.sinya;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.DTO.FormData;
import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.SinyaFormDTO;
import immargin.hardware.HCB.DTO.TagDTO;
import immargin.hardware.HCB.model.SinyaTagprod;
import immargin.hardware.HCB.model.Sinyamaintable;
import immargin.hardware.HCB.query.SinyaFindByNameSpecification;
import immargin.hardware.HCB.query.SinyaFormSpecification;


@Service
public class SinyaMaintableService {
	

	@Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;
	

	public List<MaintableDTO> SinyablurSearchMaintable(String prodname,int page,int size){
        List<MaintableDTO> pageResult = null;
        Pageable pageable=PageRequest.of(page, size);
        String[] newStr = prodname.split("\\s+");
        if(newStr.length==1) {
        pageResult =sinyaMaintableRepository.SinyafindByName(prodname,pageable).getContent();
        }else if(newStr.length==2){
        pageResult =sinyaMaintableRepository.SinyafindByName2(newStr[0],newStr[1],pageable).getContent();
        }else if(newStr.length==3){
            pageResult =sinyaMaintableRepository.SinyafindByName3(newStr[0],newStr[1],newStr[2],pageable).getContent();
        }else if(newStr.length==4){
            pageResult =sinyaMaintableRepository.SinyafindByName4(newStr[0],newStr[1],newStr[2],newStr[3],pageable).getContent();
        }else if(newStr.length==5){
            pageResult =sinyaMaintableRepository.SinyafindByName5(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],pageable).getContent();
        }else {
            pageResult =sinyaMaintableRepository.SinyafindByName6(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],newStr[5],pageable).getContent();
        }
        return pageResult;
    }

	
//	Sinya
	//搜尋欄
    public Page<Sinyamaintable> getSinyablurSearchMaintable(String prodname){
            Pageable pageable=PageRequest.of(0,20);
            SinyaFindByNameSpecification specification = new SinyaFindByNameSpecification(prodname);
            Page<Sinyamaintable> findall = sinyaMaintableRepository.findAll(specification, pageable);
            return findall;
    }
    
    
    public List<SinyaFormDTO> parseSinyablurSearchMaintable(Page<Sinyamaintable> sinyamaintablePage){
        List<Sinyamaintable> Result = null;
        List<SinyaFormDTO> sinyaFormDTOList = new ArrayList<>();
        Result = sinyamaintablePage.getContent();
//        System.out.println(Result);
        
        for (Sinyamaintable sinyamaintable : Result) {
            SinyaFormDTO sinyaFormDTO = new SinyaFormDTO(sinyamaintable.getProdname(), sinyamaintable.getProdId(), null, null, null);
            sinyaFormDTOList.add(sinyaFormDTO);
        }
        return sinyaFormDTOList;
        
    }
    
    //搜尋總頁數元素
    public int[] Sinyagettotal(String prodname,int page,int size){
        Page<MaintableDTO> pageResult = null;
        int[] abc= new int[2];
        Pageable pageable=PageRequest.of(page, size);
        String[] newStr = prodname.split("\\s+");
        if(newStr.length==1) {
        pageResult =sinyaMaintableRepository.SinyafindByName(prodname,pageable);
        }else if(newStr.length==2){
        pageResult =sinyaMaintableRepository.SinyafindByName2(newStr[0],newStr[1],pageable);
        }else if(newStr.length==3){
            pageResult =sinyaMaintableRepository.SinyafindByName3(newStr[0],newStr[1],newStr[2],pageable);
        }else if(newStr.length==4){
            pageResult =sinyaMaintableRepository.SinyafindByName4(newStr[0],newStr[1],newStr[2],newStr[3],pageable);
        }else if(newStr.length==5){
            pageResult =sinyaMaintableRepository.SinyafindByName5(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],pageable);
        }else {
            pageResult =sinyaMaintableRepository.SinyafindByName6(newStr[0],newStr[1],newStr[2],newStr[3],newStr[4],newStr[5],pageable);
        }
        pageResult.getTotalPages();
        abc[0] = pageResult.getTotalPages();
        abc[1] =(int) pageResult.getTotalElements();
        return abc;
    }
    
    public Optional<MaintableDTO> SinyagetProdname(String id) {
        Optional<MaintableDTO> result = null;
        result = sinyaMaintableRepository.SinyafindMaintableDTOByProd_id(id);
        return result;
    }
    @Cacheable(value="SinyaDailyNew", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
    public List<MaintableDTO> SinyaDailyNew(Integer index,String cacheDateString,String nowString){
        List<MaintableDTO> Result = null;
        Result = sinyaMaintableRepository.SinyaDailyNew(index);
        return Result;
    }
    
    @Cacheable("SinyamaintablePage")
    public Page<Sinyamaintable> getSinyamaintablePage(FormData formData){
        
        //formData.getpage
        Pageable pageable=PageRequest.of(formData.getPage()-1, 20);
        SinyaFormSpecification sinyaSpecification = new SinyaFormSpecification(formData);
//        System.out.println( sinyaSpecification.toString() );
        Page<Sinyamaintable> findall = sinyaMaintableRepository.findAll(sinyaSpecification,pageable);
        return findall;
    }
    
    public List<SinyaFormDTO> parseSinyamaintablePage(Page<Sinyamaintable> sinyamaintablePage) {
        List<Sinyamaintable> Result = null;
        List<SinyaFormDTO> sinyaFormDTOList = new ArrayList<>();
        Result = sinyamaintablePage.getContent();
//        System.out.println(Result);
        
        for (Sinyamaintable sinyamaintable : Result) {
            List<SinyaTagprod> sinyaTagprods = sinyamaintable.getSinyaTagprods();
            List<TagDTO> tagnameDTOList = new ArrayList<>(); 
            for (SinyaTagprod sinyaTagprod : sinyaTagprods) {
               TagDTO tmpParam = new TagDTO(sinyaTagprod.getSinyaTagcompares().getTagID(), sinyaTagprod.getSinyaTagcompares().getTagzhtw());
               tagnameDTOList.add(tmpParam);
            }
            SinyaFormDTO sinyaFormDTO = new SinyaFormDTO(sinyamaintable.getProdname(), sinyamaintable.getProdId(), sinyamaintable.getLastprice(), sinyamaintable.getLastUpdateDate(), tagnameDTOList);
            sinyaFormDTOList.add(sinyaFormDTO);
        }
        return sinyaFormDTOList;
    }
    
    
    
    

}

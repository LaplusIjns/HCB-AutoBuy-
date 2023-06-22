package immargin.hardware.hcb.sinya;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.SinyaFormDTO;
import immargin.hardware.hcb.DTO.TagDTO;
import immargin.hardware.hcb.model.SinyaTagprod;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.query.SinyaFindByNameSpecification;
import immargin.hardware.hcb.query.SinyaFormSpecification;


@Service
public class SinyaMaintableService {
	

	@Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;
	
//	Sinya
	//搜尋欄
    public Page<Sinyamaintable> getSinyablurSearchMaintable(String prodname){
            Pageable pageable=PageRequest.of(0,20);
            SinyaFindByNameSpecification specification = new SinyaFindByNameSpecification(prodname);
            return sinyaMaintableRepository.findAll(specification, pageable);
    }
    
    // 分析 getSinyablurSearchMaintable 結果
    public List<SinyaFormDTO> parseSinyablurSearchMaintable(Page<Sinyamaintable> sinyamaintablePage){
        List<Sinyamaintable> Result = null;
        List<SinyaFormDTO> sinyaFormDTOList = new ArrayList<>();
        Result = sinyamaintablePage.getContent();
        
        for (Sinyamaintable sinyamaintable : Result) {
            SinyaFormDTO sinyaFormDTO = new SinyaFormDTO(sinyamaintable.getProdname(), sinyamaintable.getProdId(), null, null, null);
            sinyaFormDTOList.add(sinyaFormDTO);
        }
        return sinyaFormDTOList;
        
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

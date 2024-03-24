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
import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.model.Sinyamaintable;


@Service
public class SinyaMaintableService {
	

	@Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;
	
//	Sinya
	//搜尋欄
    public Page<Sinyamaintable> getSinyablurSearchMaintable(String prodname){
            Pageable pageable=PageRequest.of(0,20);
//            SinyaFindByNameSpecification specification = new SinyaFindByNameSpecification(prodname);
            return sinyaMaintableRepository.findAll(SinyaMaintableRepository.SinyaFindByNameSpecification(prodname), pageable);
    }
    
    // 分析 getSinyablurSearchMaintable 結果
    public List<SinyaFormDTO> parseSinyablurSearchMaintable(Page<Sinyamaintable> sinyamaintablePage){
        return sinyamaintablePage.getContent().stream().map(t -> new SinyaFormDTO(t.getProdname(), t.getProdId(), null, null, null) ).toList();
        
    }
    

    
    public Optional<MaintableDTO> SinyagetProdname(String id) {
        return sinyaMaintableRepository.SinyafindMaintableDTOByProd_id(id);
    }
    @Cacheable(value="SinyaDailyNew", key="#cacheDateString",cacheManager = Constant.CACHE_DAILY)
    public List<MaintableDTO> SinyaDailyNew(Integer index,String cacheDateString){
        return sinyaMaintableRepository.SinyaDailyNew(index);
    }
    
    public Page<Sinyamaintable> getSinyamaintablePage(FormData formData){
        
        //formData.getpage
        Pageable pageable=PageRequest.of(formData.getPage()-1, 20);
        return sinyaMaintableRepository.findAll(SinyaMaintableRepository.SinyaFormSpecification(formData),pageable);
    }
    
    public List<SinyaFormDTO> parseSinyamaintablePage(Page<Sinyamaintable> sinyamaintablePage) {

        List<Sinyamaintable> result = sinyamaintablePage.getContent();
        return result.stream().map(sinyamaintable ->{
            List<TagDTO> tagnameDTOList = sinyamaintable.getSinyaTagprods().stream()
                    .map(sinyaTagprod -> new TagDTO(sinyaTagprod.getSinyaTagcompares().getTagID(), sinyaTagprod.getSinyaTagcompares().getTagzhtw()) ).toList();
            return new SinyaFormDTO(sinyamaintable.getProdname(), sinyamaintable.getProdId(), sinyamaintable.getLastprice(), sinyamaintable.getLastUpdateDate(), tagnameDTOList);
        } ).toList();
    }
    
    
    
    

}

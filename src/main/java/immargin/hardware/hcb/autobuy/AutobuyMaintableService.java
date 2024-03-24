package immargin.hardware.hcb.autobuy;


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
import immargin.hardware.hcb.model.Maintable;


@Service
public class AutobuyMaintableService {
	
	
	@Autowired
	MaintableRepository maintableRepository;
	
//	AutoBuy
	//搜尋欄
	public Page<Maintable> blurSearchMaintable(String prodname){
	    Pageable pageable=PageRequest.of(0,20);
	    return maintableRepository.findAll(MaintableRepository.AutobuyFindByNameSpecification(prodname),pageable);
	}
	
	//搜尋總頁數元素
	public Page<Maintable> getAutobuyblurSearchMaintable(String prodname){
        Pageable pageable=PageRequest.of(0,20);
        return maintableRepository.findAll(MaintableRepository.AutobuyFindByNameSpecification(prodname), pageable);
}
	
	// 分析 getAutobuyblurSearchMaintable 結果
    public List<SinyaFormDTO> parseAutobuyblurSearchMaintable(Page<Maintable> maintablePage){
        return maintablePage.getContent().stream().map(maintable -> new SinyaFormDTO(maintable.getProdname(), maintable.getProdId(), null, null, null)).toList();
        
    }
    
    public Optional<MaintableDTO> autobuygetProdname(String id) {
        return maintableRepository.findMaintableDTOByProdId(id);
    }
    
   public Page<Maintable> getAutobuymaintablePage(FormData formData){
        Pageable pageable=PageRequest.of(formData.getPage()-1, 20);
        return maintableRepository.findAll(MaintableRepository.AutobuyFormSpecification(formData),pageable);
    }
	
    public List<SinyaFormDTO> parseAutobuymaintablePage(Page<Maintable> autobuymaintablePage) {
        List<Maintable> result = null;
        result = autobuymaintablePage.getContent();
        return result.stream().map(maintable -> {
            List<TagDTO> tagnameDTOList = maintable.getTagprods().stream().map(tagprod -> new TagDTO(tagprod.getTagcompares().getTagID(),tagprod.getTagcompares().getTagzhtw()) ).toList();
            return new SinyaFormDTO(maintable.getProdname(), maintable.getProdId(), maintable.getLastprice(), maintable.getLastUpdateDate(), tagnameDTOList);
        } ).toList();
    }
	
	
	@Cacheable(value="DailyNew", key="#cacheDateString", cacheManager=Constant.CACHE_DAILY )
    public List<MaintableDTO> dailyNew(Integer index,String cacheDateString){
        return maintableRepository.dailyNew(index);
    }
	


}

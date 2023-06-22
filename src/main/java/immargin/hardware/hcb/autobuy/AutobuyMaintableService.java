package immargin.hardware.hcb.autobuy;


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
import immargin.hardware.hcb.model.Maintable;
import immargin.hardware.hcb.model.SinyaTagprod;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.model.Tagprod;
import immargin.hardware.hcb.query.AutobuyFindByNameSpecification;
import immargin.hardware.hcb.query.AutobuyFormSpecification;
import immargin.hardware.hcb.query.SinyaFindByNameSpecification;
import immargin.hardware.hcb.query.SinyaFormSpecification;
import immargin.hardware.hcb.sinya.SinyaMaintableRepository;


@Service
public class AutobuyMaintableService {
	
	
	@Autowired
	private MaintableRepository maintableRepository;
	
//	AutoBuy
	//搜尋欄
	public Page<Maintable> blurSearchMaintable(String prodname){
	    Pageable pageable=PageRequest.of(0,20);
	    AutobuyFindByNameSpecification specification = new AutobuyFindByNameSpecification(prodname);
	    return maintableRepository.findAll(specification,pageable);
	}
	
	//搜尋總頁數元素
	public Page<Maintable> getAutobuyblurSearchMaintable(String prodname){
        Pageable pageable=PageRequest.of(0,20);
        AutobuyFindByNameSpecification specification = new AutobuyFindByNameSpecification(prodname);
        return maintableRepository.findAll(specification, pageable);
}
	
	// 分析 getAutobuyblurSearchMaintable 結果
    public List<SinyaFormDTO> parseAutobuyblurSearchMaintable(Page<Maintable> maintablePage){
        List<Maintable> Result = null;
        List<SinyaFormDTO> sinyaFormDTOList = new ArrayList<>();
        Result = maintablePage.getContent();
        
        for (Maintable maintable : Result) {
            SinyaFormDTO sinyaFormDTO = new SinyaFormDTO(maintable.getProdname(), maintable.getProdId(), null, null, null);
            sinyaFormDTOList.add(sinyaFormDTO);
        }
        return sinyaFormDTOList;
        
    }
    
    public Optional<MaintableDTO> AutobuygetProdname(String id) {
        Optional<MaintableDTO> result = null;
        result = maintableRepository.findMaintableDTOByProdId(id);
        return result;
    }
    
   public Page<Maintable> getAutobuymaintablePage(FormData formData){
        
        //formData.getpage
        Pageable pageable=PageRequest.of(formData.getPage()-1, 20);
        AutobuyFormSpecification sinyaSpecification = new AutobuyFormSpecification(formData);
//        System.out.println( sinyaSpecification.toString() );
        Page<Maintable> findall = maintableRepository.findAll(sinyaSpecification,pageable);
        return findall;
    }
	
    public List<SinyaFormDTO> parseAutobuymaintablePage(Page<Maintable> autobuymaintablePage) {
        List<Maintable> Result = null;
        List<SinyaFormDTO> autobuyFormDTOList = new ArrayList<>();
        Result = autobuymaintablePage.getContent();
        
        for (Maintable sinyamaintable : Result) {
            List<Tagprod> sinyaTagprods = sinyamaintable.getTagprods();
            List<TagDTO> tagnameDTOList = new ArrayList<>(); 
            for (Tagprod sinyaTagprod : sinyaTagprods) {
               TagDTO tmpParam = new TagDTO(sinyaTagprod.getTagcompares().getTagID(), sinyaTagprod.getTagcompares().getTagzhtw());
               tagnameDTOList.add(tmpParam);
            }
            SinyaFormDTO sinyaFormDTO = new SinyaFormDTO(sinyamaintable.getProdname(), sinyamaintable.getProdId(), sinyamaintable.getLastprice(), sinyamaintable.getLastUpdateDate(), tagnameDTOList);
            autobuyFormDTOList.add(sinyaFormDTO);
        }
        return autobuyFormDTOList;
    }
	
	
	@Cacheable(value="DailyNew", key="#cacheDateString", unless=" (#cacheDateString.equals(#nowString)) ")
    public List<MaintableDTO> DailyNew(Integer index,String cacheDateString,String nowString){
        List<MaintableDTO> Result = null;
        Result = maintableRepository.dailyNew(index);
        return Result;
    }
	


}

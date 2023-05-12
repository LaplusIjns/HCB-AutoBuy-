package immargin.hardware.HCB.sinya;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.HTML.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.Config.Constant;
import immargin.hardware.HCB.DTO.FormData;
import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.SinyaFormDTO;
import immargin.hardware.HCB.DTO.TagDTO;
import immargin.hardware.HCB.DTO.TagnameDTO;
import immargin.hardware.HCB.autobuy.MaintableRepository;
import immargin.hardware.HCB.model.Maintable;
import immargin.hardware.HCB.model.SinyaTagcompare;
import immargin.hardware.HCB.model.SinyaTagprod;
import immargin.hardware.HCB.model.Sinyamaintable;
import immargin.hardware.HCB.sinya.SinyaMaintableRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SinyaMaintableService {
	

	@Autowired
    private SinyaMaintableRepository sinyaMaintableRepository;


	
//	Sinya
	//搜尋欄
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
    
    public List<MaintableDTO> SinyaDailyNew(Integer index){
        List<MaintableDTO> Result = null;
        Result = sinyaMaintableRepository.SinyaDailyNew(index);
        return Result;
    }
    
    public Page<Sinyamaintable> getSinyamaintablePage(FormData formData){
        
        //formData.getpage
        Pageable pageable=PageRequest.of(formData.getPage()-1, 20);
        SinyaSpecification sinyaSpecification = new SinyaSpecification(formData);
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
    
    
    
    class SinyaSpecification implements Specification<Sinyamaintable>{
        private FormData formData;
        public SinyaSpecification(FormData formData) {
            super();
            this.formData = formData;
        }
        @Override
        @Nullable
        public Predicate toPredicate(Root<Sinyamaintable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            /*
             *  Boolean expiredProd,filterProd 尚未實裝
             */
            Path<Date> initalDate = root.get("initalDate");
            Path<String> prodId = root.get("prodId");
            Path<Date> lastUpdateDate = root.get("lastUpdateDate");
//            Path<Integer> prodavailable = root.get("prodavailable");
            Path<String> prodname = root.get("prodname");
            Path<Integer> lastprice = root.get("lastprice");
            Predicate result = null;
            List<Predicate> predicateList = new ArrayList<>();
            
            if(formData.getProdName()!=null) {
                String[] newStr = formData.getProdName().split("\\s+");
                for(int i = 0; i<newStr.length;i++) {
                    Predicate predicate = criteriaBuilder.like(prodname,Constant.PERCENT+newStr[i]+Constant.PERCENT);
                    predicateList.add(predicate);    
                }
            }
            if(formData.getMinPrice()!=null) {
                Predicate predicate1 = criteriaBuilder.greaterThanOrEqualTo(lastprice,  Integer.valueOf( formData.getMinPrice() ) );
                predicateList.add(predicate1);
            }
            if(formData.getMaxPrice()!=null) {
                Predicate predicate2 = criteriaBuilder.lessThanOrEqualTo(lastprice, Integer.valueOf( formData.getMaxPrice() ) );
                predicateList.add(predicate2);
            }
            if(formData.getEndUpdate()!=null) {
                Predicate predicate3 = criteriaBuilder.lessThanOrEqualTo(lastUpdateDate, formData.getEndUpdate() );
                predicateList.add(predicate3);
            }
            if(formData.getStartUpdate()!=null) {
                Predicate predicate4 = criteriaBuilder.greaterThanOrEqualTo(lastUpdateDate, formData.getStartUpdate() );
                predicateList.add(predicate4);
            }
            if(formData.getStartDate()!=null) {
                Predicate predicate4 = criteriaBuilder.greaterThanOrEqualTo(initalDate, formData.getStartDate() );
                predicateList.add(predicate4);
            }
            if(formData.getEndDate()!=null) {
                Predicate predicate4 = criteriaBuilder.lessThanOrEqualTo(initalDate, formData.getEndDate() );
                predicateList.add(predicate4);
            }

            Predicate[] predicateArray = new Predicate[predicateList.size()];
            for(int i=0;i<predicateList.size();i++) {
                predicateArray[i] = predicateList.get(i);
            }
            
            result = criteriaBuilder.and(predicateArray);
            return result;
        }
        
    }

}

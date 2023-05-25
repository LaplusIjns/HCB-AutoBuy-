package immargin.hardware.HCB.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import immargin.hardware.HCB.Config.Constant;
import immargin.hardware.HCB.model.Sinyamaintable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SinyaFindByNameSpecification implements Specification<Sinyamaintable> {

    private String prodnames;
    public SinyaFindByNameSpecification(String prodnames) {
        super();
        this.prodnames = prodnames;
    }
    @Override
    @Nullable
    public Predicate toPredicate(Root<Sinyamaintable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // TODO Auto-generated method stub
        Path<String> prodname = root.get("prodname");
        String[] newStr = prodnames.split("\\s+");
        
        List<Predicate> predicateList = new ArrayList<>();
        Predicate result = null;
        
        for(int i = 0; i<newStr.length;i++) {
            Predicate predicate = criteriaBuilder.like(prodname,Constant.PERCENT+newStr[i]+Constant.PERCENT);
            predicateList.add(predicate);    
        }
        
        Predicate[] predicateArray = new Predicate[predicateList.size()];
        for(int i=0;i<predicateList.size();i++) {
            predicateArray[i] = predicateList.get(i);
        }
        
        result = criteriaBuilder.and(predicateArray);
        return result;
        
    }

    
}

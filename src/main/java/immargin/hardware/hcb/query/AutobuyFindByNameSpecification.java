package immargin.hardware.hcb.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.model.Maintable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AutobuyFindByNameSpecification implements Specification<Maintable> {

    private String prodnames;
    public AutobuyFindByNameSpecification(String prodnames) {
        super();
        this.prodnames = prodnames;
    }
    @Override
    @Nullable
    public Predicate toPredicate(Root<Maintable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

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

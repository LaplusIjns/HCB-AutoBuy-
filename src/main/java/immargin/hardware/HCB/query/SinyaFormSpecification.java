package immargin.hardware.HCB.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import immargin.hardware.HCB.Config.Constant;
import immargin.hardware.HCB.DTO.FormData;
import immargin.hardware.HCB.model.Sinyamaintable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SinyaFormSpecification implements Specification<Sinyamaintable> {
    private FormData formData;

    public SinyaFormSpecification(FormData formData) {
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
//    Path<Integer> prodavailable = root.get("prodavailable");
    Path<String> prodname = root.get("prodname");
    Path<Integer> lastprice = root.get("lastprice");
    Predicate result = null;
    List<Predicate> predicateList = new ArrayList<>();
    Order order = null;
    
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
    
//    if( StringUtils.hasLength(formData.getSortStrategy())) {
//        if(formData.getSortStrategy().equals("1")) {
//        }
//    }

    Predicate[] predicateArray = new Predicate[predicateList.size()];
    for(int i=0;i<predicateList.size();i++) {
        predicateArray[i] = predicateList.get(i);
    }
    
    result = criteriaBuilder.and(predicateArray);
    query.orderBy(order);
    
    return result;
}

}

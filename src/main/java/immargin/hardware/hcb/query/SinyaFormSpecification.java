package immargin.hardware.hcb.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.model.Sinyamaintable;
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
    Path<Integer> prodavailable = root.get("prodavailable");
    Predicate result = null;
    List<Predicate> predicateList = new ArrayList<>();
    
    // 移除7天未更新 = true 等同 available = 1
    //             false 等同 不移除全展示 不用作條件判斷
    
    if(formData.getExpiredProd()!=null) {
        if(formData.getExpiredProd().booleanValue()==true) {
            Predicate predicate = criteriaBuilder.equal(prodavailable, 1);
            predicateList.add(predicate);  
        }       
    }
    // 用於當商品標記為無貨改為有貨時更新使用
    if(formData.getInsideMode()!=null) {
        if(formData.getInsideMode().booleanValue()==true) {
            Predicate predicate = criteriaBuilder.equal(prodavailable, 0);
            predicateList.add(predicate);  
        }       
    }
    
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
    
    List<Order> orderlist = new ArrayList<>();
    
    if( formData.getSortStrategy() != null ) {
        for (String string : formData.getSortStrategy()) {
            if(string.equals("a")) {
                Order order = criteriaBuilder.desc(lastprice.as(Integer.class));
                orderlist.add(order);
            }
            if(string.equals("b")) {
                Order order = criteriaBuilder.asc(lastprice.as(Integer.class));
                orderlist.add(order);
            }
            if(string.equals("c")) {
                Order order = criteriaBuilder.desc(lastUpdateDate.as(Date.class));
                orderlist.add(order);
            }
            if(string.equals("d")) {
                Order order = criteriaBuilder.asc(lastUpdateDate.as(Date.class));
                orderlist.add(order);
            }
            if(string.equals("e")) {
                Order order = criteriaBuilder.desc(initalDate.as(Date.class));
                orderlist.add(order);
            }
            if(string.equals("f")) {
                Order order = criteriaBuilder.asc(initalDate.as(Date.class));
                orderlist.add(order);
            }
        }
    }
    query.where(result);
    query.orderBy(orderlist);
    return query.getRestriction();
}

}

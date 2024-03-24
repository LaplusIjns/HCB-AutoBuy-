package immargin.hardware.hcb.sinya;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.model.Sinyamaintable;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

@Repository
public interface SinyaMaintableRepository extends JpaRepository<Sinyamaintable,String>,JpaSpecificationExecutor<Sinyamaintable> {


    @Query(value="SELECT m.prodname,m.prod_id from sinyamaintable m where m.prod_id = ?1" ,nativeQuery = true)
    Optional<MaintableDTO> SinyafindMaintableDTOByProd_id(String Prod_id);
	
    @Query(value="SELECT * FROM `sinyamaintable` m WHERE m.inital_date = SUBDATE(CURRENT_DATE,?1)" ,nativeQuery = true)
    List<MaintableDTO> SinyaDailyNew(Integer index);
    
    public static Specification<Sinyamaintable> SinyaFormSpecification(FormData formData){
        return(root,query,criteriaBuilder)->{
            /*
             *  Boolean expiredProd,filterProd 尚未實裝
             */
            Path<Date> initalDate = root.get("initalDate");
//            Path<String> prodId = root.get("prodId");
            Path<Date> lastUpdateDate = root.get("lastUpdateDate");
//            Path<Integer> prodavailable = root.get("prodavailable");
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
                predicateList.addAll(
                List.of(formData.getProdName().split("\\s+")).stream().map(t -> criteriaBuilder.like(prodname,Constant.PERCENT+t+Constant.PERCENT) ).toList()
                );
            }
            
            if(formData.getMinPrice()!=null) {
                Predicate predicate1 = criteriaBuilder.greaterThanOrEqualTo(lastprice,  formData.getMinPrice() );
                predicateList.add(predicate1);
            }
            if(formData.getMaxPrice()!=null) {
                Predicate predicate2 = criteriaBuilder.lessThanOrEqualTo(lastprice, formData.getMaxPrice() );
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
                Order order;
                for (String string : formData.getSortStrategy()) {
                    switch (string.toLowerCase()) {
                        case "a":
                            order = criteriaBuilder.desc(lastprice.as(Integer.class));
                            orderlist.add(order);
                            break;
                        case "b":
                            order = criteriaBuilder.asc(lastprice.as(Integer.class));
                            orderlist.add(order);
                            break;
                        case "c":
                             order = criteriaBuilder.desc(lastUpdateDate.as(Date.class));
                            orderlist.add(order);
                            break;
                        case "d":
                            order = criteriaBuilder.asc(lastUpdateDate.as(Date.class));
                            orderlist.add(order);
                            break;
                        case "e":
                            order = criteriaBuilder.desc(initalDate.as(Date.class));
                            orderlist.add(order);
                            break;
                        case "f":
                            order = criteriaBuilder.asc(initalDate.as(Date.class));
                            orderlist.add(order);
                            break;
                        default:
                            break;
                    }
                }
            }
            query.where(result);
            query.orderBy(orderlist);
            return query.getRestriction();
        };
    }
    
    public static Specification<Sinyamaintable> SinyaFindByNameSpecification(String prodnames){
        return(root,query,criteriaBuilder)->{
            Path<String> prodname = root.get("prodname");
            return criteriaBuilder.and(List.of(prodnames.split("\\s+")).stream().map(t -> criteriaBuilder.like(prodname,Constant.PERCENT+t+Constant.PERCENT) ).toArray(Predicate[]::new)); 
        };
    }
	
}

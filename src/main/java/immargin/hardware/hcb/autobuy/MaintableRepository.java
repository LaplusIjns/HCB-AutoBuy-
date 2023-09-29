package immargin.hardware.hcb.autobuy;


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
import immargin.hardware.hcb.model.Maintable;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

@Repository
public interface MaintableRepository extends JpaRepository<Maintable,String>,JpaSpecificationExecutor<Maintable> {



	@Query(value="SELECT m.prodname,m.prod_id from maintable m where m.prod_id = ?1" ,nativeQuery = true)
	Optional<MaintableDTO> findMaintableDTOByProdId(String prodId);
	
	@Query(value="SELECT * FROM `maintable` m WHERE m.inital_date = SUBDATE(CURRENT_DATE,?1)" ,nativeQuery = true)
	List<MaintableDTO> dailyNew(Integer index);
	
	public static Specification<Maintable> AutobuyFindByNameSpecification(String prodnames){
	    return (root, query, builder) -> {
	        Path<String> prodname = root.get("prodname");
	        String[] newStr = prodnames.split("\\s+");
	        Predicate[] predicateArray2 = new Predicate[newStr.length];
	        for(int i=0; i<predicateArray2.length; i++) {
	            predicateArray2[i] = builder.like(prodname,  Constant.PERCENT.concat(newStr[i]).concat(Constant.PERCENT));
	        }

	        return builder.and(predicateArray2);	        
	    };
	}
	
	public static Specification<Maintable> AutobuyFormSpecification(FormData formData){
	    return (root, query, builder) -> {
	        /*
	         *  Boolean expiredProd,filterProd 尚未實裝
	         */
	        Path<Date> initalDate = root.get("initalDate");
//	        Path<String> prodId = root.get("prodId");
	        Path<Date> lastUpdateDate = root.get("lastUpdateDate");
//	        Path<Integer> prodavailable = root.get("prodavailable");
	        Path<String> prodname = root.get("prodname");
	        Path<Integer> lastprice = root.get("lastprice");
	        Path<Integer> prodavailable = root.get("prodavailable");
	        Predicate result = null;
	        List<Predicate> predicateList = new ArrayList<>();
	        
	        // 移除7天未更新 = true 等同 available = 1
	        //             false 等同 不移除全展示 不用作條件判斷
	        
	        if(formData.getExpiredProd()!=null) {
	            if(formData.getExpiredProd().booleanValue()==true) {
	                Predicate predicate = builder.equal(prodavailable, 1);
	                predicateList.add(predicate);  
	            }       
	        }
	        // 用於當商品標記為無貨改為有貨時更新使用
	        if(formData.getInsideMode()!=null) {
	            if(formData.getInsideMode().booleanValue()==true) {
	                Predicate predicate = builder.equal(prodavailable, 0);
	                predicateList.add(predicate);  
	            }       
	        }
	        
	        if(formData.getProdName()!=null) {
	            String[] newStr = formData.getProdName().split("\\s+");
	            for(int i = 0; i<newStr.length;i++) {
	                predicateList.add( builder.like(prodname,  Constant.PERCENT.concat(newStr[i]).concat(Constant.PERCENT)) );    
	            }
	        }
	        
	        if(formData.getMinPrice()!=null) {
	            Predicate predicate1 = builder.greaterThanOrEqualTo(lastprice,  Integer.valueOf( formData.getMinPrice() ) );
	            predicateList.add(predicate1);
	        }
	        if(formData.getMaxPrice()!=null) {
	            Predicate predicate2 = builder.lessThanOrEqualTo(lastprice, Integer.valueOf( formData.getMaxPrice() ) );
	            predicateList.add(predicate2);
	        }
	        if(formData.getEndUpdate()!=null) {
	            Predicate predicate3 = builder.lessThanOrEqualTo(lastUpdateDate, formData.getEndUpdate() );
	            predicateList.add(predicate3);
	        }
	        if(formData.getStartUpdate()!=null) {
	            Predicate predicate4 = builder.greaterThanOrEqualTo(lastUpdateDate, formData.getStartUpdate() );
	            predicateList.add(predicate4);
	        }
	        if(formData.getStartDate()!=null) {
	            Predicate predicate4 = builder.greaterThanOrEqualTo(initalDate, formData.getStartDate() );
	            predicateList.add(predicate4);
	        }
	        if(formData.getEndDate()!=null) {
	            Predicate predicate4 = builder.lessThanOrEqualTo(initalDate, formData.getEndDate() );
	            predicateList.add(predicate4);
	        }
	        
	        result = builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
	        List<Order> orderlist = new ArrayList<>();
	        
	        if( formData.getSortStrategy() != null ) {
	            for (String string : formData.getSortStrategy()) {
	                if(string.equals("a")) {
	                    Order order = builder.desc(lastprice.as(Integer.class));
	                    orderlist.add(order);
	                }
	                if(string.equals("b")) {
	                    Order order = builder.asc(lastprice.as(Integer.class));
	                    orderlist.add(order);
	                }
	                if(string.equals("c")) {
	                    Order order = builder.desc(lastUpdateDate.as(Date.class));
	                    orderlist.add(order);
	                }
	                if(string.equals("d")) {
	                    Order order = builder.asc(lastUpdateDate.as(Date.class));
	                    orderlist.add(order);
	                }
	                if(string.equals("e")) {
	                    Order order = builder.desc(initalDate.as(Date.class));
	                    orderlist.add(order);
	                }
	                if(string.equals("f")) {
	                    Order order = builder.asc(initalDate.as(Date.class));
	                    orderlist.add(order);
	                }
	            }
	        }
	        query.where(result);
	        query.orderBy(orderlist);
	        return query.getRestriction();
	    };
	}
}

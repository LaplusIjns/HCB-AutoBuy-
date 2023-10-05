package immargin.hardware.HCB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.thymeleaf.util.ListUtils;

import immargin.hardware.hcb.DTO.DailyDTO2;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.autobuy.AutobuyMaintableService;
import immargin.hardware.hcb.autobuy.MaintableRepository;
import immargin.hardware.hcb.config.CommoUtils;
import immargin.hardware.hcb.model.Blacklist;
import immargin.hardware.hcb.model.Maintable;
//import immargin.hardware.hcb.model.Tag;
import immargin.hardware.hcb.model.Tagprod;
import immargin.hardware.hcb.repository.BlacklistRepository;
import immargin.hardware.hcb.repository.LastJDBCTemplate;
import immargin.hardware.hcb.service.BlacklistService;
import immargin.hardware.hcb.service.LastService;
import immargin.hardware.hcb.service.TagService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@SpringBootTest
class HcbApplicationTests {
    @Autowired
    AutobuyMaintableService maintableService;
    @Autowired
    LastService lastService;
    @Autowired
    TagService tagService;

    @Autowired
    MaintableRepository maintableRepository;

    @Autowired
    BlacklistRepository blacklistRepository;

    @Autowired
    CommoUtils commoUtils;
    
    @Autowired
    BlacklistService blacklistService;
    
    @Autowired
    JdbcTemplate jdbctemplate;
    
    @Autowired
    LastJDBCTemplate lastJDBCTemplate;

    private static final Logger log = LoggerFactory.getLogger(HcbApplicationTests.class);

//	@Test
//	void contextLoads() {
//	}
//	
//	@Test
//	void method1() {
//	    
//	    List<DailyDTO2> x = lastJDBCTemplate.getDaily(3, 4);
//	    for (DailyDTO2 dailyDTO : x) {
//            System.out.println(dailyDTO.getProdname());
//        }
	    
//		maintableService.blurSearchMaintable("asus",0,20);\
//	    try {
//	    String name = "3";
//	    List<Map<String, Object>> X = jdbctemplate.queryForList("""
//	            SELECT t.fk_tag,y.tag_zhtw from tag_prod t INNER JOIN tag_compare y ON y.tag_id = t.fk_tag WHERE t.fk_prod_id = ?1
//	            """,new Object[] {name});
//	    List<Tag> X = jdbctemplate.query("""
//                SELECT t.fk_tag,y.tag_zhtw from tag_prod t INNER JOIN tag_compare y ON y.tag_id = t.fk_tag WHERE t.fk_prod_id = ?
//                """,new BeanPropertyRowMapper<>(Tag.class),new Object[] {"prod_329275"});
//	    
//	    for (Tag tagnameDTO : X) {
//            System.out.println(tagnameDTO.getTagZhtw() );
//            System.out.println(tagnameDTO.getFkTag() );
//            System.out.println("=========");
//        }
	    
//	    for (Map<String, Object> map1 : X) {
//	        map1.forEach((k,v)->{
//	            System.out.println(k+"  "+v);
//	            });
//	        System.out.println("=====");
//        }
	        
//	        List<Map<String, Object>> x = lastJDBCTemplate.getProd("last_".concat(String.valueOf(0)), "prod_336520");
//	        List<Map<String, Object>> x = lastJDBCTemplate.getSinyaDaily(7, 8);
//	        for (Map<String, Object> map : x) {
//                map.forEach( (k,v)->System.out.println(k+"  "+v) );
//                System.out.println("======");
//            }
//	    System.out.println("OK");
//	    }catch (Exception e) {
//	        e.printStackTrace();
//        }
//	    
//	}
//    @Test
//    void method1() {
//        System.out.println(blacklistRepository.count());
//
//        System.out.println(commoUtils.getDayFromTwoDate(new Date(900000000), new Date()));

//      Blacklist tmp = new Blacklist("192.168.1.1", 1, new Date());
//      System.out.println(blacklistRepository.save(tmp));
//      var tmp = blacklistRepository.findById("192.168.1.0");
//      Optional<Blacklist> blacklist =  blacklistRepository.findById("192.168.1.0");
//        Optional<Blacklist> blacklist2 = blacklistRepository.findById("192.168.1.1");
//        blacklist2.ifPresentOrElse(
//                blacklist -> {
//                    if (commoUtils.getDayFromTwoDate(new Date(), blacklist.getUpdateTime()) <= 2) {
//                        blacklist.setCountNumber(blacklist.getCountNumber() + 1);
//                        blacklist.setUpdateTime(new Date());
//                        blacklistService.save(blacklist);
//                    }
//                },
//                () -> {
//                    System.out.println("不存在");
//                    blacklistService.save(new Blacklist(null, 1, new Date()));
//                });

//      blacklist.ofNullable(null).ifPresent(null);
//      System.out.println(tmp.isEmpty());
//      System.out.println(tmp.get());

//    }

//	@Test
//	void method2() {
//		lastService.getProd("prod_332120");
//	}
//	 @Test
//  void method3() {
//      maintableService.SearchProd("Kingston 金士頓 DTLPG3 USB3.0 64G 隨身碟(DTLPG3/64GB)",0,20);
//  }

//	@Test
//	void method4() {
//	    tagService.SearchbyProdname("prod_329257");
//	}

//   @Test
//    void method5() {
//        tagService.SearchbyTagname("cate_8270",0,20);
//    }
//	 @Test
//  void method1() {
//      maintableService.getProdname("prod_320384");
//  }
//	 @Test
//  void method4() {
//      tagService.getTagname("cate_16050");
//  }
//  @Test
//  void method1() {
//      maintableService.test1("prod_320384");
//  }
//  @Test
//  void method1() {
//      List<DailyDTO> result = lastService.getDaily(1);
//      for (DailyDTO dailyDTO : result) {
//          log.info(dailyDTO.getProdname());
//    }
//  }

//    @Test
//    void method7() {
//        Specification<Maintable> specification = new Specification<Maintable>() {
//
//            @Override
//            @Nullable
//            public Predicate toPredicate(Root<Maintable> root, CriteriaQuery<?> query,
//                    CriteriaBuilder criteriaBuilder) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                Path<Date>ca = root.get("inital_date");
//                Path<String> a = root.get("prodId");
//                Path<Date> b = root.get("last_update_date");
//                System.out.println(a);
//                Path<String> b = root.get("");
//                Date tmp2 = new Date(122, 8, 13);
//                System.out.println(tmp2);
//                String tmp = "prod_104287";
//                Predicate equal = criteriaBuilder.equal(a,tmp);
//                System.out.println(format.format(tmp2));
//                Predicate equal2 = criteriaBuilder.lessThanOrEqualTo(b,tmp2);
//                Predicate equal2 = criteriaBuilder.greaterThanOrEqualTo( ca ,tmp2);
//                Predicate equal2 = criteriaBuilder.greaterThanOrEqualTo( ca.as(String.class) ,"2022-08-13");
//                Predicate equal2 = criteriaBuilder.greaterThanOrEqualTo( ca.as(String.class) ,format.format(tmp2));
//                Predicate result = criteriaBuilder.or(equal,equal2);
//                Predicate result = criteriaBuilder.and(equal2);
//                return result;
//            }
//        };
//        Sort sort = Sort.by(Sort.Direction.DESC, "prodId");

//        List<Maintable>  all = maintableRepository.findAll(specification, sort);

//        System.out.println(all);
//        if(!ListUtils.isEmpty(all)) {
//            System.out.println(all.size());
//            for (Maintable maintable : all) {
//                System.out.println(maintable.getProdId());
//                System.out.println(maintable.getInital_date());
//                System.out.println(maintable.getLast_update_date());
//            }
//        }
//        System.out.println(all.get(0).getProdId());
//        System.out.println(all.get(0).getInital_date());
//        System.out.println(all.get(0).getLast_update_date());
    }
//}

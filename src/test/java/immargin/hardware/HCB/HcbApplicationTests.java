package immargin.hardware.HCB;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import immargin.hardware.HCB.model.Maintable;
import immargin.hardware.HCB.repository.LastRepository;
import immargin.hardware.HCB.service.LastService;
import immargin.hardware.HCB.service.MaintableService;
import immargin.hardware.HCB.service.TagService;

@SpringBootTest
class HcbApplicationTests {
	@Autowired
	MaintableService maintableService;
	@Autowired
	LastService lastService;
	@Autowired
	TagService tagService;
//	@Test
//	void contextLoads() {
//	}
//	
//	@Test
//	void method1() {
//		maintableService.blurSearchMaintable("asus",0,20);
//	}
	
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
	 @Test
  void method4() {
      tagService.getTagname("cate_16050");
  }
}

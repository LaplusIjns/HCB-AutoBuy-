package immargin.hardware.hcb.autobuy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import immargin.hardware.hcb.DTO.DailyDTO;
import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.DTO.LastDTO;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.model.Maintable;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.service.LastService;
import immargin.hardware.hcb.service.TagService;

@RestController
@RequestMapping
public class AutobuyRestfulController {
	@Autowired
	AutobuyMaintableService maintableService;
	@Autowired
	LastService lastService;
	@Autowired
	TagService tagService;
	
	
    private static final Logger log = LoggerFactory.getLogger(AutobuyRestfulController.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    //	AutoBuy
	//搜尋欄 模糊搜
	@PostMapping(path = {"/findprod"})
	public ResponseEntity<Object> findProdname(@RequestBody String id) {
	    List<MaintableDTO> result = maintableService.blurSearchMaintable(id, 0, 20);
		if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//搜尋後 分頁 模糊搜2
	@PostMapping(path = {"/findprod2"})
	public ResponseEntity<Object> findProdname2(@RequestBody Maintable bean) {
	    log.info("使用者查詢: {}",bean.getProdname());
	    List<MaintableDTO> result =null;
        result = maintableService.blurSearchMaintable(bean.getProdname(),Integer.valueOf(bean.getPage()), 20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	//產品 總頁數 總元素 變數 分頁
	@PostMapping(path = {"/total"})
    public ResponseEntity<Object> findtotal(@RequestBody Maintable bean) {
        int[] result =null;
        result = maintableService.gettotal(bean.getProdname(), Integer.valueOf(bean.getPage()), 20);
        if(result!=null) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	   //tag 總頁數 總元素 變數 分頁
    @PostMapping(path = {"/tagtotal/{pageparam}"})
    public ResponseEntity<Object> findtagtotal(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        int[] result =null;
        result = tagService.gettagtotal(bean.getProdname(), page, 20);
        if(result!=null) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	
    //產品頁 輸出產品名字
	@PostMapping(path = {"/findprod3/{prodname}"})
    public ResponseEntity<Object> findProdname3(@PathVariable(name="prodname") String id) {
	    Optional<MaintableDTO> result;
        result = maintableService.getProdname(id);
        if(!result.isEmpty()) {
            log.info("使用者點選商品: {}",result.get().getProdname());
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	//產品頁 輸出日期和價格
    @PostMapping(path = {"/findprod4/{prodname}"})
    public ResponseEntity<Object> findProdname4(@PathVariable(name="prodname") String id) {
        List<LastDTO> result=null;
        result = lastService.getProd(id);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//獲得產品的 tag
	@PostMapping(path = { "/tag/{prodname}"} )
	public ResponseEntity<Object> findProdTag(@PathVariable(name="prodname") String id){
	    List<TagnameDTO> result=null;
	    result = tagService.SearchbyProdname(id);
	    if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
	}
	
	// 用tag 搜尋相同產品
    @PostMapping(path = {"/findtag/{pageparam}"})
    public ResponseEntity<Object> findtagname(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        List<MaintableDTO> result =null;
        result = tagService.SearchbyTagname(bean.getProdname(), page,20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
  //tag頁 輸出tag 名字
    @PostMapping(path = {"/findtag2/{prodname}"})
    public ResponseEntity<Object> findTagname2(@PathVariable(name="prodname") String id) {
        Optional<TagnameDTO> result;
        result = tagService.getTagname(id);
        if(!result.isEmpty()) {
            log.info("標籤搜尋: {}",result.get().gettag_zhtw());
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //每日更新價差
    @PostMapping(path = {"/dailyprice/{index}"})
    public ResponseEntity<Object> findDailyprice(@PathVariable Integer index) {
        List<DailyDTO> result=null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = simpleDateFormat.format(cacheDate);
        String nowString = simpleDateFormat.format(new Date());
        
        result = lastService.getDaily(index,cacheDateString,nowString);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
  //每日更新新增商品
    @PostMapping(path = {"/dailynew/{index}"})
    public ResponseEntity<Object> findDailynew(@PathVariable Integer index) {
        List<MaintableDTO> result=null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = simpleDateFormat.format(cacheDate);
        String nowString = simpleDateFormat.format(new Date());
        
        result = maintableService.DailyNew(index,cacheDateString,nowString);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
}

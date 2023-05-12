package immargin.hardware.HCB.sinya;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import immargin.hardware.HCB.DTO.DailyDTO;
import immargin.hardware.HCB.DTO.FormData;
import immargin.hardware.HCB.DTO.LastDTO;
import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.SinyaFormDTO;
import immargin.hardware.HCB.DTO.TagnameDTO;
import immargin.hardware.HCB.autobuy.AutobuyMaintableService;
import immargin.hardware.HCB.model.Maintable;
import immargin.hardware.HCB.model.Sinyamaintable;
import immargin.hardware.HCB.service.LastService;
import immargin.hardware.HCB.service.TagService;

@RestController
@RequestMapping
public class SinyaRestfulController {
	@Autowired
	SinyaMaintableService sinyaMaintableService;
	@Autowired
	LastService lastService;
	@Autowired
	TagService tagService;
	
    private static final Logger log = LoggerFactory.getLogger(SinyaRestfulController.class);
    
    //	Sinya
    //搜尋欄 模糊搜
    @PostMapping(path = {"/Sinyafindprod"})
    public ResponseEntity<?> SinyafindProdname(@RequestBody String id) {
        List<MaintableDTO> result = sinyaMaintableService.SinyablurSearchMaintable(id, 0, 20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //搜尋後 分頁 模糊搜2
    @PostMapping(path = {"/Sinyafindprod2"})
    public ResponseEntity<?> SinyafindProdname2(@RequestBody Maintable bean) {
        log.info("使用者查詢: {}",bean.getProdname());
        List<MaintableDTO> result =null;
        result = sinyaMaintableService.SinyablurSearchMaintable(bean.getProdname(),Integer.valueOf( bean.getPage() ), 20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //產品 總頁數 總元素 變數 分頁
    @PostMapping(path = {"/Sinyatotal/{pageparam}"})
    public ResponseEntity<?> Sinyafindtotal(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        int[] result =null;
        result = sinyaMaintableService.Sinyagettotal(bean.getProdname(), page, 20);
        if(result!=null) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
       //tag 總頁數 總元素 變數 分頁
    @PostMapping(path = {"/Sinyatagtotal/{pageparam}"})
    public ResponseEntity<?> Sinyafindtagtotal(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        int[] result =null;
        result = tagService.Sinyagettagtotal(bean.getProdname(), page, 20);
        if(result!=null) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //產品頁 輸出產品名字
    @PostMapping(path = {"/Sinyafindprod3/{prodname}"})
    public ResponseEntity<?> SinyafindProdname3(@PathVariable(name="prodname") String id) {
        Optional<MaintableDTO> result=null;
        result = sinyaMaintableService.SinyagetProdname(id);
        if(result!=null && result.isPresent()) {
            log.info("使用者點選商品: {}",result.get().getProdname());
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //產品頁 輸出日期和價格
    @PostMapping(path = {"/Sinyafindprod4/{prodname}"})
    public ResponseEntity<?> SinyafindProdname4(@PathVariable(name="prodname") String id) {
        List<LastDTO> result=null;
        result = lastService.SinyagetProd(id);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //獲得產品的 tag
    @PostMapping(path = { "/Sinyatag/{prodname}"} )
    public ResponseEntity<?> SinyafindProdTag(@PathVariable(name="prodname") String id){
        List<TagnameDTO> result=null;
        result = tagService.SinyaSearchbyProdname(id);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 用tag 搜尋相同產品
    @PostMapping(path = {"/Sinyafindtag/{pageparam}"})
    public ResponseEntity<?> Sinyafindtagname(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        List<MaintableDTO> result =null;
        result = tagService.SinyaSearchbyTagname(bean.getProdname(), page,20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
  //tag頁 輸出tag 名字
    @PostMapping(path = {"/Sinyafindtag2/{prodname}"})
    public ResponseEntity<?> SinyafindTagname(@PathVariable(name="prodname") String id) {
        Optional<TagnameDTO> result=null;
        result = tagService.SinyagetTagname(id);
        if(result!=null && result.isPresent()) {
            log.info("標籤搜尋: {}",result.get().gettag_zhtw());
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //每日更新價差
    @PostMapping(path = {"/Sinyadailyprice/{index}"})
    public ResponseEntity<?> findSinyaDailyprice(@PathVariable Integer index) {
        List<DailyDTO> result=null;
        result = lastService.getSinyaDaily(index);
        System.out.println(result.get(0).getfk_prod_id());
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //每日更新新增商品
    @PostMapping(path = {"/Sinyadailynew/{index}"})
    public ResponseEntity<?> findSinyaDailynew(@PathVariable Integer index) {
        List<MaintableDTO> result=null;
        result = sinyaMaintableService.SinyaDailyNew(index);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //表單處理
    @PostMapping(path = {"/SinyaForm"})
    public ResponseEntity<?> SinyaForm(@RequestBody FormData formData) {
        List<SinyaFormDTO> result=null;

        Page<Sinyamaintable> SinyamaintablePage = sinyaMaintableService.getSinyamaintablePage(formData);
        result = sinyaMaintableService.parseSinyamaintablePage(SinyamaintablePage);
        Map<String, Object> result2 = new HashMap<>();
        result2.put("produts", result);
        result2.put("totalnumber", SinyamaintablePage.getTotalElements());
        result2.put("page", SinyamaintablePage.getNumber());
        result2.put("totalpage", SinyamaintablePage.getTotalPages());
        
        System.out.println(result);
        
        // 當前這切片 所在頁數
        System.out.println(SinyamaintablePage.getNumber());
        
        // 當前這切片全部
        System.out.println(SinyamaintablePage.getNumberOfElements());
        
        // 全部頁數
        System.out.println(SinyamaintablePage.getTotalPages());
        
        // 一個slice的大小
        System.out.println(SinyamaintablePage.getSize());
        
        // 全部數量
        System.out.println(SinyamaintablePage.getTotalElements());
        
        
        if(SinyamaintablePage.getContent()!=null && SinyamaintablePage.getContent().size()!=0) {
            return ResponseEntity.ok(result2);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}

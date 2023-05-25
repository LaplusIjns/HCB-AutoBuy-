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
        List<SinyaFormDTO> result=null;

        Page<Sinyamaintable> SinyamaintablePage = sinyaMaintableService.getSinyablurSearchMaintable(id);
        result = sinyaMaintableService.parseSinyamaintablePage(SinyamaintablePage);
        Map<String, Object> result2 = new HashMap<>();
        result2.put("produts", result);
        result2.put("totalnumber", SinyamaintablePage.getTotalElements());
        result2.put("page", SinyamaintablePage.getNumber());
        result2.put("totalpage", SinyamaintablePage.getTotalPages());
        
        
        if(SinyamaintablePage.getContent()!=null && SinyamaintablePage.getContent().size()!=0) {
            return ResponseEntity.ok(result2);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping(path = { "/SinyaProduct/{prodname}"} )
    public ResponseEntity<?> SinyaProduct(@PathVariable(name="prodname") String id){
        
        Map<String, Object> result = new HashMap<>();
        
        Optional<MaintableDTO> prodname=null;
        prodname = sinyaMaintableService.SinyagetProdname(id);
        
        if(prodname.isPresent()) {
            result.put("productname", prodname);
            
            List<LastDTO> priceanddate = lastService.SinyagetProd(id);
            result.put("priceanddate", priceanddate);
            
            List<TagnameDTO> taginfo = tagService.SinyaSearchbyProdname(id);
            result.put("taginfo", taginfo);
            
            
            return ResponseEntity.ok(result);
        }else{
           
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping(path = { "/SinyaTag"} )
    public ResponseEntity<?> SinyaTag(@RequestBody FormData formData){
        
        Map<String, Object> result = new HashMap<>();
        String id = formData.getProdName();
        Integer page = formData.getPage();
        
        Optional<TagnameDTO> prodtagname=null;
        prodtagname = tagService.SinyagetTagname(id);
        
        if(prodtagname.isPresent()) {
            result.put("prodtagname", prodtagname);
            
            Page<MaintableDTO> tagpage = tagService.SinyaTagPage(id, page, 20);
            result.put("totalpage", tagpage.getTotalPages());
            result.put("totalelement", tagpage.getTotalElements());
            
            List<MaintableDTO> tagproduct = tagService.SinyaSearchbyTagname(id, page,20);
            result.put("tagproduct", tagproduct);
            
            log.info("搜尋標籤: {}",prodtagname.get().gettag_zhtw());
            
            return ResponseEntity.ok(result);
        }else{
           
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
        
//        System.out.println(result);
        
        // 當前這切片 所在頁數
//        System.out.println(SinyamaintablePage.getNumber());
        
        // 當前這切片全部
//        System.out.println(SinyamaintablePage.getNumberOfElements());
        
        // 全部頁數
//        System.out.println(SinyamaintablePage.getTotalPages());
        
        // 一個slice的大小
//        System.out.println(SinyamaintablePage.getSize());
        
        // 全部數量
//        System.out.println(SinyamaintablePage.getTotalElements());
        
        
        if(SinyamaintablePage.getContent()!=null && SinyamaintablePage.getContent().size()!=0) {
            return ResponseEntity.ok(result2);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}

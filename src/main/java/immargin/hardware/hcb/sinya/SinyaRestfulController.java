package immargin.hardware.hcb.sinya;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import immargin.hardware.hcb.DTO.DailyDTO;
import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.DTO.LastDTO;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.SinyaFormDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.autobuy.AutobuyMaintableService;
import immargin.hardware.hcb.model.Maintable;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.service.LastService;
import immargin.hardware.hcb.service.TagService;

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
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
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
        
        
        if(!SinyamaintablePage.isEmpty()) {
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
        
        Optional<TagnameDTO> prodtagname;
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
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = simpleDateFormat.format(cacheDate);
        String nowString = simpleDateFormat.format(new Date());
        result = lastService.getSinyaDaily(index,cacheDateString,nowString);
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
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = simpleDateFormat.format(cacheDate);
        String nowString = simpleDateFormat.format(new Date());
        
        result = sinyaMaintableService.SinyaDailyNew(index,cacheDateString,nowString);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //表單處理
    @PostMapping(path = {"/SinyaForm"})
    public ResponseEntity<?> SinyaForm(@RequestBody FormData formData) {
        
        log.info("搜尋商品: {}",formData.getProdName());
        
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
        
        
        if(!SinyamaintablePage.isEmpty()) {
            return ResponseEntity.ok(result2);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}

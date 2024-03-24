package immargin.hardware.hcb.sinya;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import immargin.hardware.hcb.DTO.DailyDTO2;
import immargin.hardware.hcb.DTO.FormData;
import immargin.hardware.hcb.DTO.LastDTO2;
import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.SinyaFormDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.config.CommonUtils;
import immargin.hardware.hcb.model.Sinyamaintable;
import immargin.hardware.hcb.service.LastService;
import immargin.hardware.hcb.service.TagService;

@RestController
@RequestMapping
public class SinyaRestfulController {

    SinyaMaintableService sinyaMaintableService;
    LastService lastService;
    TagService tagService;
    
    public SinyaRestfulController(SinyaMaintableService sinyaMaintableService, LastService lastService,
            TagService tagService) {
        super();
        this.sinyaMaintableService = sinyaMaintableService;
        this.lastService = lastService;
        this.tagService = tagService;
    }

    private static final Logger log = LoggerFactory.getLogger(SinyaRestfulController.class);
    private  static final DateFormatter formatter = new DateFormatter("yyyyMMdd");

    // Sinya
    // 搜尋欄 模糊搜
    @PostMapping(path = { "/Sinyafindprod" })
    public ResponseEntity<Object> sinyafindProdname(@RequestBody String id) {

        Page<Sinyamaintable> sinyamaintablePage = sinyaMaintableService.getSinyablurSearchMaintable(id);

        if (sinyamaintablePage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SinyaFormDTO> result = sinyaMaintableService.parseSinyamaintablePage(sinyamaintablePage);
        Map<String, Object> result2 = CommonUtils.parseListResult(sinyamaintablePage, result);
        return ResponseEntity.ok(result2);
    }

    // 模糊搜後實際搜尋結果
    @PostMapping(path = { "/SinyaProduct/{prodname}" })
    public ResponseEntity<Object> sinyaProduct(@PathVariable(name = "prodname") String id) {

        Optional<MaintableDTO> prodname = sinyaMaintableService.SinyagetProdname(id);

        if (!prodname.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("productname", prodname);
        List<LastDTO2> priceanddate = lastService.SinyagetProd(id);
        result.put("priceanddate", priceanddate);
        List<TagnameDTO> taginfo = tagService.SinyaSearchbyProdname(id);
        result.put("taginfo", taginfo);

        return ResponseEntity.ok(result);
    }

    @PostMapping(path = { "/SinyaTag" })
    public ResponseEntity<Object> sinyaTag(@RequestBody FormData formData) {

        String id = formData.getProdName();
        Optional<TagnameDTO> prodtagname = tagService.SinyagetTagname(id);

        if (!prodtagname.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result = new HashMap<>();
        Integer page = formData.getPage();

        result.put("prodtagname", prodtagname);
        Page<MaintableDTO> tagpage = tagService.SinyaTagPage(id, page, 20);
        result.put("totalpage", tagpage.getTotalPages());
        result.put("totalelement", tagpage.getTotalElements());
        List<MaintableDTO> tagproduct = tagService.SinyaSearchbyTagname(id, page, 20);
        result.put("tagproduct", tagproduct);

        log.info("搜尋標籤: {}", prodtagname.get().gettag_zhtw());

        return ResponseEntity.ok(result);

    }

    // 每日更新價差
    @PostMapping(path = { "/Sinyadailyprice/{index}" })
    public ResponseEntity<Object> findSinyaDailyprice(@PathVariable Integer index) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = formatter.print(cacheDate, Locale.TAIWAN);
        List<DailyDTO2> result = lastService.getSinyaDaily(index, cacheDateString);
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result);
    }

    // 每日更新新增商品
    @PostMapping(path = { "/Sinyadailynew/{index}" })
    public ResponseEntity<Object> findSinyaDailynew(@PathVariable Integer index) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -index);
        Date cacheDate = calendar.getTime();
        String cacheDateString = formatter.print(cacheDate, Locale.TAIWAN);

        List<MaintableDTO> result = sinyaMaintableService.SinyaDailyNew(index, cacheDateString);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);

    }

    /**
     * 表單處理
     * 當前這切片 所在頁數 SinyamaintablePage.getNumber()
     * 當前這切片全部數量 SinyamaintablePage.getNumberOfElements()
     * 全部頁數 SinyamaintablePage.getTotalPages()
     * 一個slice的大小 SinyamaintablePage.getSize()
     * 
     */
    @PostMapping(path = { "/SinyaForm" })
    public ResponseEntity<Object> sinyaForm(@RequestBody FormData formData) {

        log.info("搜尋商品: {}", formData.getProdName());

        Page<Sinyamaintable> sinyamaintablePage = sinyaMaintableService.getSinyamaintablePage(formData);
        List<SinyaFormDTO> result = sinyaMaintableService.parseSinyamaintablePage(sinyamaintablePage);

        if (sinyamaintablePage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result2 = CommonUtils.parseListResult(sinyamaintablePage, result);
        return ResponseEntity.ok(result2);
    }
}

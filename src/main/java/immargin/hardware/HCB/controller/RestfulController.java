package immargin.hardware.HCB.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import immargin.hardware.HCB.DTO.LastDTO;
import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.TagnameDTO;
import immargin.hardware.HCB.model.Maintable;
import immargin.hardware.HCB.service.LastService;
import immargin.hardware.HCB.service.MaintableService;
import immargin.hardware.HCB.service.TagService;

@RestController
@RequestMapping
public class RestfulController {
	@Autowired
	MaintableService maintableService;
	@Autowired
	LastService lastService;
	@Autowired
	TagService tagService;
	
	//搜尋欄 模糊搜
	@PostMapping(path = {"/findprod/{prodname}"})
	public ResponseEntity<?> findProdname(@PathVariable(name="prodname") String id) {
	    List<MaintableDTO> result = maintableService.blurSearchMaintable(id, 0, 20);
		if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//搜尋後 分頁 模糊搜2
	@PostMapping(path = {"/findprod2/{pageparam}"})
	public ResponseEntity<?> findProdname2(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
	    List<MaintableDTO> result =null;
	    System.out.println(page);
        result = maintableService.blurSearchMaintable(bean.getProdname(), page, 20);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	//產品 總頁數 總元素 變數 分頁
	@PostMapping(path = {"/total/{pageparam}"})
    public ResponseEntity<?> findtotal(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        int[] result =null;
        result = maintableService.gettotal(bean.getProdname(), page, 20);
        if(result!=null) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	   //tag 總頁數 總元素 變數 分頁
    @PostMapping(path = {"/tagtotal/{pageparam}"})
    public ResponseEntity<?> findtagtotal(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
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
    public ResponseEntity<?> findProdname3(@PathVariable(name="prodname") String id) {
	    System.out.println(id);
	    Optional<MaintableDTO> result=null;
        result = maintableService.getProdname(id);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
	//產品頁 輸出日期和價格
    @PostMapping(path = {"/findprod4/{prodname}"})
    public ResponseEntity<?> findProdname4(@PathVariable(name="prodname") String id) {
        System.out.println(id);
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
	public ResponseEntity<?> findProdTag(@PathVariable(name="prodname") String id){
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
    public ResponseEntity<?> findtagname(@RequestBody Maintable bean,@PathVariable(name="pageparam") Integer page) {
        System.out.println("helloworld");
        List<MaintableDTO> result =null;
        System.out.println(page);
        System.out.println(bean);
        result = tagService.SearchbyTagname(bean.getProdname(), page,20);
        System.out.println(result);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
  //tag頁 輸出tag 名字
    @PostMapping(path = {"/findtag2/{prodname}"})
    public ResponseEntity<?> findTagname(@PathVariable(name="prodname") String id) {
        System.out.println(id);
        Optional<TagnameDTO> result=null;
        result = tagService.getTagname(id);
        if(result!=null && !result.isEmpty()) {
            return ResponseEntity.ok(result.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
		
}

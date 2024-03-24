package immargin.hardware.hcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutingController {
//	AutoBuy
	@GetMapping({"/"})
	public String home() {
		return "index";
	}
		
	@GetMapping({"/Autobuy"})
    public String home2() {
        return "autobuy/Autobuy";
    }
	
	@GetMapping({"/Product"})
    public String prod() {
        return "autobuy/Product";
    }
		
	@GetMapping({"/tag"})
    public String tag() {
        return "autobuy/tag";
    }
	@GetMapping({"/AutobuyDaily"})
    public String daily() {
        return "autobuy/AutobuyDaily";
    }
	@GetMapping({"/Dailynew"})
    public String dailynew() {
        return "autobuy/Dailynew";
    }
//	Sinya
	@GetMapping({"/Sinya"})
    public String sinyaHome() {
        return "sinya/Sinyaindex";
    }
    
    @GetMapping({"/SinyaProduct"})
    public String sinyaprod() {
        return "sinya/SinyaProduct";
    }
    
    @GetMapping({"/Sinyatag"})
    public String sinyatag() {
        return "sinya/Sinyatag";
    }
    @GetMapping({"/SinyaDaily"})
    public String sinyaDaily() {
        return "sinya/SinyaDaily";
    }
    @GetMapping({"/SinyaDailynew"})
    public String sinyaDailynew() {
        return "sinya/SinyaDailynew";
    }
    
    @GetMapping({"/banip"})
    public String banip() {
        return "banip";
    }
    
}

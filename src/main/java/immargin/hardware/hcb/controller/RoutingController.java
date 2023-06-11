package immargin.hardware.hcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RoutingController {
//	AutoBuy
	@GetMapping({"/"})
	public String home() {
		return "/index";
	}
	
	@GetMapping({"/Autobuy"})
    public String home2() {
        return "/Autobuy";
    }
	
	@GetMapping({"/Product"})
    public String prod() {
        return "/Product";
    }
	
	@GetMapping({"/tag"})
    public String tag() {
        return "/tag";
    }
	@GetMapping({"/AutobuyDaily"})
    public String daily() {
        return "/AutobuyDaily";
    }
	@GetMapping({"/Dailynew"})
    public String dailynew() {
        return "/Dailynew";
    }
//	Sinya
	@GetMapping({"/Sinya"})
    public String sinyaHome() {
        return "/Sinyaindex";
    }
    
    @GetMapping({"/SinyaProduct"})
    public String sinyaprod() {
        return "/SinyaProduct";
    }
    
    @GetMapping({"/Sinyatag"})
    public String sinyatag() {
        return "/Sinyatag";
    }
    @GetMapping({"/SinyaDaily"})
    public String sinyaDaily() {
        return "/SinyaDaily";
    }
    @GetMapping({"/SinyaDailynew"})
    public String sinyaDailynew() {
        return "/SinyaDailynew";
    }
    
}

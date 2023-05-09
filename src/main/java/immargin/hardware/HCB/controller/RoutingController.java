package immargin.hardware.HCB.controller;

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
	public String Home() {
		return "/index";
	}
	
	@GetMapping({"/Autobuy"})
    public String Home2() {
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
    public String SinyaHome() {
        return "/Sinyaindex";
    }
    
    @GetMapping({"/SinyaProduct"})
    public String Sinyaprod() {
        return "/SinyaProduct";
    }
    
    @GetMapping({"/Sinyatag"})
    public String Sinyatag() {
        return "/Sinyatag";
    }
    @GetMapping({"/SinyaDaily"})
    public String SinyaDaily() {
        return "/SinyaDaily";
    }
    @GetMapping({"/SinyaDailynew"})
    public String SinyaDailynew() {
        return "/SinyaDailynew";
    }
    
}

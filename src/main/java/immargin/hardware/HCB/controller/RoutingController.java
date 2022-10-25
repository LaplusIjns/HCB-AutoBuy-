package immargin.hardware.HCB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutingController {
	
	@RequestMapping(path = {"/"})
	public String Home() {
		return "/index";
	}
	
	@RequestMapping(path = {"/Product"})
    public String prod() {
        return "/Product";
    }
	
	@RequestMapping(path = {"/tag"})
    public String tag() {
        return "/tag";
    }
	

}

package immargin.hardware.hcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaviconController {


    @GetMapping("/images/favicon.ico")
    public String  icon() {
//        return "redirect:../favicon.svg";
        return "redirect:/images/favicon.svg";
    }
}

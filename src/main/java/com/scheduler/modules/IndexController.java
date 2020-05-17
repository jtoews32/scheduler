package com.scheduler.modules;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
   	@GetMapping({"/", "/index.html", "index.jsp", "index"})
    public String index(Model model) { //, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", "user");
        return "index";
    }

}

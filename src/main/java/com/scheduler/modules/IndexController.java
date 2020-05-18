package com.scheduler.modules;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/", "/index.html", "index.jsp", "index"})
    public String index(Model model) {
        //model.addAttribute("name", "");
        return "index";
    }

}

package com.scheduler.modules;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/", "/index.html", "index.jsp", "index"})
    public String index() {
        return "index";
    }
}

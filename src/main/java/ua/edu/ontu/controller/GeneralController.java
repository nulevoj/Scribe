package ua.edu.ontu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping({"/", "/home", "/index"})
    public String indexPage() {
        return "/index";
    }

    @GetMapping("/fail")
    public String failPage() {
        return "general/fail";
    }

    @GetMapping("/example")
    public String examplePage() {
        return "general/example";
    }

    @GetMapping("/placeholders")
    public String placeholdersPage() {
        return "general/placeholders";
    }

}

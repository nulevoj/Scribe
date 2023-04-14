package ua.edu.ontu.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @GetMapping("/error")
    public String errorPage() {
        return "redirect:/fail?default";
    }

}

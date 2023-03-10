package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.ontu.service.AccountService;

@Controller
@RequestMapping("/personal-area")
public class PersonalAreaController {

    @Autowired
    private AccountService accountService;


}

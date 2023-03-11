package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;
import ua.edu.ontu.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public String route() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return "redirect:/";
        }
        String email = ((UserDetails) principal).getUsername();

        Person person = accountService.findByEmail(email).getPerson();
        if (person == null) {
            return "account/choose";
        }
        if (person instanceof Student) {
            return "account/student";
        }
        if (person instanceof Employee) {
            return "account/employee";
        }
        return "redirect:/";
    }

    @PostMapping("/choose")
    public String choose() {
        return "redirect:/account";
    }

    @PatchMapping("/student")
    public String patchStudent() {
        return "account/student";
    }

    @PatchMapping("/employee")
    public String patchEmployee() {
        return "account/employee";
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "redirect:/authentication/login?deleted";
    }

}

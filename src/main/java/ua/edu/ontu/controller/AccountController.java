package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;
import ua.edu.ontu.service.AccountService;
import ua.edu.ontu.service.PersonService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @GetMapping()
    public String route(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return "redirect:/";
        }
        String email = ((UserDetails) principal).getUsername();

        Person person = accountService.findByEmail(email).getPerson();
        if (person == null) {
            model.addAttribute("student", new Student());
            model.addAttribute("employee", new Employee());
            return "account/choose";
        }
        if (person instanceof Student) {
            model.addAttribute("student", person);
            return "account/student";
        }
        if (person instanceof Employee) {
            model.addAttribute("employee", person);
            return "account/employee";
        }
        return "redirect:/";
    }

    @PostMapping("/choose")
    public String choose(@RequestParam("person") String type,
                         @ModelAttribute("student") Student student,
                         @ModelAttribute("employee") Employee employee) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return "redirect:/";
        }
        String email = ((UserDetails) principal).getUsername();

        Account account = accountService.findByEmail(email);
        Person person = null;
        if (type.equals("student")) {
            person = student;
        }
        if (type.equals("employee")) {
            person = employee;
        }
        account.setPerson(person);
        person.setAccount(account);
        accountService.save(account);
        return "redirect:/account";
    }

    @PatchMapping("/student")
    public String patchStudent(@ModelAttribute("student") Student student) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return "redirect:/";
        }
        String email = ((UserDetails) principal).getUsername();

        Account account = accountService.findByEmail(email);
        personService.update(account, student);
        accountService.save(account);
        return "account/student";
    }

    @PatchMapping("/employee")
    public String patchStudent(@ModelAttribute("employee") Employee employee) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return "redirect:/";
        }
        String email = ((UserDetails) principal).getUsername();

        Account account = accountService.findByEmail(email);
        personService.update(account, employee);
        accountService.save(account);
        return "account/employee";
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "redirect:/authentication/login?deleted";
    }

}

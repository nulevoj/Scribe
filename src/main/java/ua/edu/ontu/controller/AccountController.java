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

    private String getEmailFromPrincipal() {
        //TODO: use AOP instead
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new RuntimeException("!(principal instanceof UserDetails)");
        }
        return ((UserDetails) principal).getUsername();
    }

    @GetMapping()
    public String route(Model model) {
        String email = getEmailFromPrincipal();
        Person person = accountService.findByEmail(email).getPerson();
        if (person == null) {
            return "account/choose/choose";
        }
        if (person instanceof Employee) {
            model.addAttribute("employee", person);
            return "account/view/employee";
        }
        if (person instanceof Student) {
            model.addAttribute("student", person);
            return "account/view/student";
        }
        return "redirect:/index?unknownError";
    }

    @GetMapping("/choose-employee")
    public String chooseEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        return "account/choose/choose-employee";
    }

    @GetMapping("/choose-student")
    public String chooseStudentPage(Model model) {
        model.addAttribute("student", new Student());
        return "account/choose/choose-student";
    }

    @PostMapping("/choose-employee")
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        String email = getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        account.setPerson(employee);
        employee.setAccount(account);
        accountService.save(account);
        return "redirect:/account";
    }

    @PostMapping("/choose-student")
    public String createEmployee(@ModelAttribute("student") Student student) {
        String email = getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        account.setPerson(student);
        student.setAccount(account);
        accountService.save(account);
        return "redirect:/account";
    }

    @GetMapping("/edit")
    public String editPage(Model model) {
        String email = getEmailFromPrincipal();
        Person person = accountService.findByEmail(email).getPerson();
        if (person == null) {
            return "account/choose/choose";
        }
        if (person instanceof Employee) {
            model.addAttribute("employee", person);
            return "account/edit/employee";
        }
        if (person instanceof Student) {
            model.addAttribute("student", person);
            return "account/edit/student";
        }
        return "redirect:/index?unknownError";
    }

    @PutMapping("/edit-employee")
    public String patchEmployee(@ModelAttribute("employee") Employee employee) {
        String email = getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        personService.update(account, employee);
        accountService.save(account);
        return "account/view/employee";
    }

    @PutMapping("/edit-student")
    public String patchStudent(@ModelAttribute("student") Student student) {
        String email = getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        personService.update(account, student);
        accountService.save(account);
        return "account/view/student";
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "redirect:/authentication/login?deleted";
    }

}

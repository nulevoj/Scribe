package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;
import ua.edu.ontu.service.AccountService;
import ua.edu.ontu.service.PersonService;
import ua.edu.ontu.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @GetMapping()
    public String route(Model model) {
        String email = userDetailsService.getEmailFromPrincipal();
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
        savePerson(employee);
        return "redirect:/account";
    }

    @PostMapping("/choose-student")
    public String createEmployee(@ModelAttribute("student") Student student) {
        savePerson(student);
        return "redirect:/account";
    }

    private Person savePerson(Person person) {
        String email = userDetailsService.getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        return accountService.savePerson(account, person);
    }

    @GetMapping("/edit")
    public String editPage(Model model) {
        String email = userDetailsService.getEmailFromPrincipal();
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
        updatePerson(employee);
        return "account/view/employee";
    }

    @PutMapping("/edit-student")
    public String patchStudent(@ModelAttribute("student") Student student) {
        updatePerson(student);
        return "account/view/student";
    }

    private void updatePerson(Person person) {
        String email = userDetailsService.getEmailFromPrincipal();
        Account account = accountService.findByEmail(email);
        personService.update(account, person);
        accountService.save(account);
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "redirect:/authentication/login?deleted";
    }

}

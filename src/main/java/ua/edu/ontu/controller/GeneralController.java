package ua.edu.ontu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Student;
import ua.edu.ontu.scribe.Vocabulary;

import java.util.LinkedHashSet;
import java.util.Set;

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
    public String placeholdersPage(Model model) {
        Account account = new Account();
        Vocabulary vocabulary = new Vocabulary();

        account.setPerson(new Student());
        vocabulary.putAll(account);

        account.setPerson(new Employee());
        vocabulary.putAll(account);

        Set<String> set = new LinkedHashSet<>();
        vocabulary.getMap().keySet().stream().sorted().forEach(set::add);
        model.addAttribute("set", set);
        return "general/placeholders";
    }

}

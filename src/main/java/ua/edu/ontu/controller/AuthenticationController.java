package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.dto.AccountDto;
import ua.edu.ontu.exception.EmailIsTakenException;
import ua.edu.ontu.service.AccountService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index?alreadyLoggedIn";
        }
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index?alreadyLoggedIn";
        }
        model.addAttribute("account", new AccountDto());
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String registerAccount(@ModelAttribute("account") AccountDto registrationDto) {
        try {
            accountService.save(registrationDto);
            return "redirect:/authentication/login?success";
        } catch (EmailIsTakenException e) {
            e.printStackTrace();
            return "redirect:/authentication/registration?emailTaken";
        }
    }

    @GetMapping("/reset")
    public String resetPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index?alreadyLoggedIn";
        }
        return "authentication/reset";
    }

    @PatchMapping("/reset")
    public String resetPassword(Authentication authentication) {
        return "redirect:/reset?error";
    }

}

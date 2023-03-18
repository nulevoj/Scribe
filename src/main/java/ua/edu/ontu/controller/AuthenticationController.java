package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.ontu.dto.RegistrationDto;
import ua.edu.ontu.service.AccountService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index?alreadyLoggedIn";
        }
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("registration") RegistrationDto registrationDto,
                                   Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index?alreadyLoggedIn";
        }
        model.addAttribute("registration", registrationDto);
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String registerAccount(@ModelAttribute("registration") RegistrationDto registrationDto,
                                  RedirectAttributes redirectAttributes) {
        if (accountService.isExist(registrationDto.getEmail())) {
            redirectAttributes.addFlashAttribute("registration", registrationDto);
            return "redirect:/authentication/registration?emailTaken";
        }
        if (!registrationDto.getPassword().equals(registrationDto.getPasswordConfirmation())) {
            redirectAttributes.addFlashAttribute("registration", registrationDto);
            return "redirect:/authentication/registration?confirmationError";
        }
        accountService.saveDto(registrationDto);
        return "redirect:/authentication/login?success";
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

package com.example.crmSystem.Controller;

import com.example.crmSystem.Service.EmailService;
import com.example.crmSystem.Service.RoleService;
import com.example.crmSystem.Service.UserService;
import com.example.crmSystem.model.Role;
import com.example.crmSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
   private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return login view
    }

    @GetMapping("/")
    public String showPage(){
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Authentication authentication) {

        System.out.println(">> Authenticated principal: " + authentication.getPrincipal());
        System.out.println(">> Granted roles: " + authentication.getAuthorities());



        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println(role);
        if ("ROLE_MANAGER".equals(role)) {
            return "redirect:/manager/home";
        } else if ("ROLE_SALES_PERSON".equals(role)) {
            return "redirect:/salesperson/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignupForm(@ModelAttribute("user") User user) {
        // Save the user, which includes the role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        emailService.sendSimpleMessage(user.getEmail(), "Welcome to Our CRM", "Thank you for registering!");


        return "redirect:/login"; // Redirect to login page after successful signup
    }

}

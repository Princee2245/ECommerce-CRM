package com.example.crmSystem.config;

import com.example.crmSystem.Repository.UserRepository;
import com.example.crmSystem.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        System.out.println("Authentication authorities: " + authentication.getAuthorities());

        String email;

        // ✅ Properly extract email if it's OAuth2
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            email = oauth2Token.getPrincipal().getAttribute("email");
        } else {
            email = authentication.getName(); // fallback
        }

        if (email == null) {
            response.sendRedirect("/login?error=email_not_found");
            return;
        }

        //  Check if user exists
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalUser.isEmpty()) {
            // Create new user and save
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setRole("ROLE_SALES_PERSON"); // Default role
            newUser.setPassword("{bcrypt}OAUTH_USER"); // Placeholder
            userRepository.save(newUser);
        }

        // ✅ Get role from authorities
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_SALES_PERSON");

        switch (role) {
            case "ROLE_MANAGER":
                response.sendRedirect("/manager/home");
                break;
            case "ROLE_SALES_PERSON":
                response.sendRedirect("/sales/home");
                break;
            default:
                response.sendRedirect("/login?error=invalid_role");
        }
    }
}

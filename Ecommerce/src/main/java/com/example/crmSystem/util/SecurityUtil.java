package com.example.crmSystem.util;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    /**
     * Extracts email from either OAuth2 or traditional login.
     */
    public static String extractEmail(Authentication authentication) {
        if (authentication == null) return null;

        // Case 1: OAuth2 login
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            return oauthToken.getPrincipal().getAttribute("email");
        }

        // Case 2: Traditional login with UserDetails
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername(); // Usually email or username
        }

        // Fallback to name
        return authentication.getName();
    }
}

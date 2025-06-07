package com.example.crmSystem.Service;


import com.example.crmSystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");

        // Check if user exists in DB
        User user = userService.findByEmail(email);

        if (user == null) {
            // Create new user with proper role
            user = new User();
            user.setEmail(email);
            user.setPassword("OAUTH2_USER");
            user.setRole("ROLE_SALES_PERSON"); // Set the role

            user = userService.save(user); // Save to database
        }

        // Create authorities list - include both the role and OAuth2 scopes
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole())); // Add ROLE_SALES_PERSON
        authorities.addAll(oauth2User.getAuthorities()); // Add OAuth2 scopes

        // Build user with combined authorities
        Map<String, Object> attributes = oauth2User.getAttributes();
        String nameAttributeKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(
                authorities,
                attributes,
                nameAttributeKey
        );
    }
}
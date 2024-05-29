package etti.comparator.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComparatorController {
    @GetMapping("/check-authentication")
    public Map<String, Object> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            response.put("isAuthenticated", true);
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("USER".equals(authority.getAuthority())) {
                    response.put("role", "USER");
                    break;
                }
            }
        } else {
            response.put("isAuthenticated", false);
        }

        return response;
    }
}

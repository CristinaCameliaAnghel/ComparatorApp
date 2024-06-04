package etti.comparator.controller;

import java.security.Principal;
import java.util.List;

import etti.comparator.model.Service;
import etti.comparator.repositories.ServicesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProviderController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @GetMapping("/provider-page")
    public String providerPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);



        return "provider";
    }
}

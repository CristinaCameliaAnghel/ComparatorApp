package etti.comparator.controller;

import java.security.Principal;

import etti.comparator.dto.ProviderDto;
import etti.comparator.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import etti.comparator.dto.UserDto;
import etti.comparator.services.UserService;

@Controller
public class UserController {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServicesRepository servicesRepository;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfuly!");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user";
    }

    @GetMapping("/registrationasprovider")
    public String getProviderRegistrationPage(@ModelAttribute("provider") ProviderDto providerDto) {
        return "registerasprovider";
    }

    @PostMapping("/registrationasprovider")
    public String saveProvider(@ModelAttribute("provider") ProviderDto providerDto, Model model) {
        userService.saveProvider(providerDto);
        model.addAttribute("message", "Provider Registered Successfully!");
        return "registerasprovider";
    }





    }



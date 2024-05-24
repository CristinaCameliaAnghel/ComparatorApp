package etti.comparator.controller;

import etti.comparator.model.Service;
import etti.comparator.model.Utility;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UtilityRepository;
import etti.comparator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private UtilityRepository utilityRepository;


    @GetMapping("/admin-page")
    public String adminFunctionality(Model model, Principal principal) {
        adminPage(model, principal);
        showServiceList(model);
        showUtilityList(model);
        return "admin";
    }

    private void adminPage(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
    }

    private void showServiceList(Model model) {
        List<Service> services = servicesRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("services", services);
    }

    private void showUtilityList(Model model) {
        List<Utility> utilities = utilityRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("utilities", utilities);
    }

}

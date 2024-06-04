package etti.comparator.controller;

import etti.comparator.model.Service;
import etti.comparator.model.Utility;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
public class AdminController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private UtilityRepository utilityRepository;


    @GetMapping("/admin-page")
    public String adminFunctionality(Model model, Principal principal,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size)  {
        adminPage(model, principal);
        showServiceList(model, page, size);
        showUtilityList(model, page, size);
        return "admin";
    }

    private void adminPage(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
    }

    private void showServiceList(Model model, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Service> servicePage = servicesRepository.findAll(pageable);
        model.addAttribute("services", servicePage.getContent());
        model.addAttribute("totalPages", servicePage.getTotalPages());
        model.addAttribute("currentPage", page);
    }


    private void showUtilityList(Model model, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Utility> utilityPage = utilityRepository.findAll(pageable);
        model.addAttribute("utilities", utilityPage.getContent());
        model.addAttribute("totalPages", utilityPage.getTotalPages());
        model.addAttribute("currentPage", page);
    }

    @PostMapping("/add-service")
    public String addService(@ModelAttribute Service service) {
        servicesRepository.save(service);
        return "redirect:/admin-page";
    }

    @PostMapping("/edit-service")
    public String editService(@RequestParam("id") int id, @RequestParam("newName") String newName) {
        Service service = servicesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + id));
        service.setName(newName);
        servicesRepository.save(service);
        return "redirect:/admin-page";
    }
    @PostMapping("/delete-service/{id}")
    public String deleteService(@PathVariable int id) {
        servicesRepository.deleteById(id);
        return "redirect:/admin-page";
    }

    @PostMapping("/add-utility")
    public String addUtility(@ModelAttribute Utility utility) {
        utilityRepository.save(utility);
        return "redirect:/admin-page";
    }

    @PostMapping("/edit-utility")
    public String editUtility(@RequestParam("id") int id, @RequestParam("newName") String newName) {
        Utility utility = utilityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid utility Id:" + id));
        utility.setName(newName);
        utilityRepository.save(utility);
        return "redirect:/admin-page";
    }

    @PostMapping("/delete-utility/{id}")
    public String deleteUtility(@PathVariable int id) {
        utilityRepository.deleteById(id);
        return "redirect:/admin-page";
    }
}
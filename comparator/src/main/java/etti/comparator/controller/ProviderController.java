package etti.comparator.controller;

import java.security.Principal;
import java.util.List;

import etti.comparator.model.Service;
import etti.comparator.model.ServiceDetails;
import etti.comparator.repositories.ServicesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import etti.comparator.services.CustomUserDetail;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProviderController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @GetMapping("/provider-page")
    public String providerPage(Model model) {
        // Obținem userDetails din contextul de securitate
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            model.addAttribute("user", userDetails);

            // Obținem ofertele utilizatorului autentificat folosind fullName
            String fullName = userDetails.getFullName();
            List<ServiceDetails> userServices = servicesDetailsRepository.findByServiceProvider(fullName);
            model.addAttribute("userServices", userServices);
        }

        return "provider";
    }

    @GetMapping("/create-serviceOffer")
    public String showCreateOfferForm(Model model) {
        model.addAttribute("serviceDetails", new ServiceDetails());
        return "serviceOffers/CreateServiceOffers";
    }

    @PostMapping("/add-serviceOffer")
    public String addOffer(@ModelAttribute ServiceDetails serviceDetails, Principal principal, RedirectAttributes redirectAttributes) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        serviceDetails.setServiceProvider(userDetails.getFullName());
        servicesDetailsRepository.save(serviceDetails);
        redirectAttributes.addFlashAttribute("message", "Offer created successfully!");
        return "redirect:/provider-page";
    }
}

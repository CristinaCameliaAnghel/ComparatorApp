package etti.comparator.controller;
import ch.qos.logback.core.model.Model;
import etti.comparator.model.ServiceDetails;
import etti.comparator.model.User;
import etti.comparator.model.UserServiceOffer;
import etti.comparator.repositories.ServicesDetailsRepository;
import etti.comparator.repositories.UserServiceOfferRepository;
import etti.comparator.services.AuthenticationService;
import etti.comparator.services.CustomUserDetailsService;
import etti.comparator.services.ServiceDetailsService;
import etti.comparator.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class ComparatorController {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ServiceDetailsService serviceDetailsService;

    @Autowired
    private UserServiceOfferRepository userServiceOfferRepository;

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/compare-services")
    public String compareServices(@RequestParam("selectedServices") List<Integer> selectedServiceIds, org.springframework.ui.Model model) {
        List<ServiceDetails> selectedServiceDetailsList = servicesDetailsRepository.findAllById(selectedServiceIds);
        model.addAttribute("selectedServiceDetailsList", selectedServiceDetailsList);
        model.addAttribute("isUserAuthenticated", authenticationService.isUserWithRole("USER"));


        selectedServiceDetailsList.forEach(service -> {
            List<UserServiceOffer> userServiceOffers = userServiceOfferRepository.findByServiceDetails(service);
            model.addAttribute("userServiceOffers" + service.getId(), userServiceOffers);
        });

        return "ServicesComparator";
    }



    @GetMapping("/apply")
    public String applyForService(@RequestParam("serviceId") int serviceId, @AuthenticationPrincipal UserDetails currentUser, org.springframework.ui.Model model) {
        User user = userServiceImpl.findUserByEmail(currentUser.getUsername());
        ServiceDetails serviceDetails = serviceDetailsService.findById(serviceId);

        // Pregătește modelul pentru a fi utilizat în formularul de aplicare
        model.addAttribute("userId", user.getId());
        model.addAttribute("serviceId", serviceDetails.getId());

        // Redirecționează către pagina de formular de aplicare
        return "ServiceFormApplication";
    }

    @PostMapping("/apply")
    public String submitApplicationForm(@ModelAttribute UserServiceOffer userServiceOffer, @AuthenticationPrincipal UserDetails currentUser) {
        // Obține user-ul autentificat
        User user = userServiceImpl.findUserByEmail(currentUser.getUsername());
        userServiceOffer.setUser(user);

        // Setează starea inițială și alte date implicite
        userServiceOffer.setStatus("in asteptare");

        // Salvează entitatea UserServiceOffer
        userServiceOfferRepository.save(userServiceOffer);

        // Redirecționează către o pagină de confirmare sau înapoi la pagina de utilizator
        return "redirect:/user-page";
    }


    @GetMapping("/service-details")
    public String getServiceDetails(@RequestParam("serviceId") int serviceId, org.springframework.ui.Model model) {
        ServiceDetails serviceDetails = serviceDetailsService.findById(serviceId);
        List<UserServiceOffer> userServiceOffers = userServiceOfferRepository.findByServiceDetails(serviceDetails);

        model.addAttribute("serviceDetails", serviceDetails);
        model.addAttribute("userServiceOffers", userServiceOffers);

        return "ServiceDetails";
    }
}

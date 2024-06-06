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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/compare-services")
    public String compareServices(@RequestParam("selectedServices") List<Integer> selectedServiceIds, org.springframework.ui.Model model) {
        List<ServiceDetails> selectedServiceDetailsList = servicesDetailsRepository.findAllById(selectedServiceIds);
        model.addAttribute("selectedServiceDetailsList", selectedServiceDetailsList);
        model.addAttribute("isUserAuthenticated", authenticationService.isUserWithRole("USER"));
        return "ServicesComparator";
    }


    @PostMapping("/apply")
    public String applyForService(@RequestParam("serviceId") int serviceId, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userDetailsService.findUserByUsername(currentUser.getUsername());
        ServiceDetails serviceDetails = serviceDetailsService.findById(serviceId);

        UserServiceOffer userServiceOffer = new UserServiceOffer();
        userServiceOffer.setUser(user);
        userServiceOffer.setServiceDetails(serviceDetails);
        userServiceOfferRepository.save(userServiceOffer);

        return "redirect:/user-page";
    }

}

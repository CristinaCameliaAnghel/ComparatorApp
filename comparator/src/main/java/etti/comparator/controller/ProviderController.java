package etti.comparator.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import etti.comparator.Mappers.UserMapper;
import etti.comparator.dto.ServiceApplicationDto;
import etti.comparator.dto.UserDto;
import etti.comparator.model.UserServiceComments;
import etti.comparator.model.UserServiceOffer;
import etti.comparator.repositories.UserServiceCommentsRepository;
import etti.comparator.repositories.UserServiceOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import etti.comparator.dto.ServiceDetailsDto;
import etti.comparator.model.Provider;
import etti.comparator.model.ServiceDetails;
import etti.comparator.repositories.ProviderRepository;
import etti.comparator.repositories.ServicesDetailsRepository;
import etti.comparator.services.CustomUserDetail;

@Controller
public class ProviderController {

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UserServiceOfferRepository userServiceOfferRepository;

    @Autowired
    private UserServiceCommentsRepository userServiceCommentsRepository;


    @GetMapping("/provider-page")
    public String providerPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            model.addAttribute("user", userDetails);

            String fullName = userDetails.getFullName();
            List<ServiceDetails> userServices = servicesDetailsRepository.findByServiceProvider(fullName);
            model.addAttribute("userServices", userServices);
        }

        return "provider";
    }

    @GetMapping("/create-serviceOffer")
    public String showServiceCreateOfferForm(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            Optional<Provider> providerOpt = providerRepository.findByUserId(userDetails.getId());
            if (providerOpt.isPresent()) {
                Provider provider = providerOpt.get();
                ServiceDetailsDto serviceDetailsDto = new ServiceDetailsDto();
                serviceDetailsDto.setName(provider.getServiceName());
                model.addAttribute("serviceDetailsDto", serviceDetailsDto);
            } else {
                model.addAttribute("serviceDetailsDto", new ServiceDetailsDto());
            }
        } else {
            model.addAttribute("serviceDetailsDto", new ServiceDetailsDto());
        }
        return "/serviceOffers/CreateServiceOffers";
    }

    @PostMapping("/add-serviceOffer")
    public String addServiceOffer(@ModelAttribute ServiceDetailsDto serviceDetailsDto, Principal principal, RedirectAttributes redirectAttributes) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ServiceDetails serviceDetails = new ServiceDetails();
        serviceDetails.setName(serviceDetailsDto.getName());
        serviceDetails.setServiceProvider(userDetails.getFullName());
        serviceDetails.setEstimatedDuration(serviceDetailsDto.getEstimatedDuration());
        serviceDetails.setPrice(serviceDetailsDto.getPrice());
        serviceDetails.setOnlineBookingAvailability(serviceDetailsDto.getOnlineBookingAvailability());
        serviceDetails.setRequiredEquipment(serviceDetailsDto.getRequiredEquipment());
        serviceDetails.setGuarantee(serviceDetailsDto.getGuarantee());
        serviceDetails.setApprovedCertifiedLicensed(serviceDetailsDto.getApprovedCertifiedLicensed());
        serviceDetails.setGeographicCoverage(serviceDetailsDto.getGeographicCoverage());
        serviceDetails.setDescription(serviceDetailsDto.getDescription());
        serviceDetails.setFeedbackStars(serviceDetailsDto.getFeedbackStars());
        serviceDetails.setRegisteredAt(new Date());

        servicesDetailsRepository.save(serviceDetails);
        redirectAttributes.addFlashAttribute("message", "Offer created successfully!");
        return "redirect:/provider-page";
    }

    @GetMapping("/edit-serviceOffer")
    public String showServiceEditOfferForm(Model model, @RequestParam int id) {
        try {
            ServiceDetails serviceDetails = servicesDetailsRepository.findById(id).get();
            model.addAttribute("serviceDetails", serviceDetails);

            ServiceDetailsDto serviceDetailsDto = new ServiceDetailsDto();
            serviceDetailsDto.setName(serviceDetails.getName());
            serviceDetailsDto.setServiceProvider(serviceDetails.getServiceProvider());
            serviceDetailsDto.setEstimatedDuration(serviceDetails.getEstimatedDuration());
            serviceDetailsDto.setPrice(serviceDetails.getPrice());
            serviceDetailsDto.setOnlineBookingAvailability(serviceDetails.getOnlineBookingAvailability());
            serviceDetailsDto.setRequiredEquipment(serviceDetails.getRequiredEquipment());
            serviceDetailsDto.setGuarantee(serviceDetails.getGuarantee());
            serviceDetailsDto.setApprovedCertifiedLicensed(serviceDetails.getApprovedCertifiedLicensed());
            serviceDetailsDto.setGeographicCoverage(serviceDetails.getGeographicCoverage());
            serviceDetailsDto.setDescription(serviceDetails.getDescription());
            serviceDetailsDto.setFeedbackStars(serviceDetails.getFeedbackStars());
            serviceDetailsDto.setRegisteredAt(serviceDetails.getRegisteredAt());

            model.addAttribute("serviceDetailsDto", serviceDetailsDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/provider-page";
        }
        return "serviceOffers/EditServiceOffer";
    }

    @PostMapping("/edit-serviceOffer")
    public String updateServiceOffer(
            Model model,
            @RequestParam int id,
            @ModelAttribute ServiceDetailsDto serviceDetailsDto,
            BindingResult result) {

        try {
            ServiceDetails serviceDetails = servicesDetailsRepository.findById(id).get();
            model.addAttribute("serviceDetails", serviceDetails);

            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                }
            }

            serviceDetails.setName(serviceDetailsDto.getName());
            serviceDetails.setEstimatedDuration(serviceDetailsDto.getEstimatedDuration());
            serviceDetails.setPrice(serviceDetailsDto.getPrice());
            serviceDetails.setOnlineBookingAvailability(serviceDetailsDto.getOnlineBookingAvailability());
            serviceDetails.setRequiredEquipment(serviceDetailsDto.getRequiredEquipment());
            serviceDetails.setGuarantee(serviceDetailsDto.getGuarantee());
            serviceDetails.setApprovedCertifiedLicensed(serviceDetailsDto.getApprovedCertifiedLicensed());
            serviceDetails.setGeographicCoverage(serviceDetailsDto.getGeographicCoverage());
            serviceDetails.setDescription(serviceDetailsDto.getDescription());
            serviceDetails.setFeedbackStars(serviceDetailsDto.getFeedbackStars());

            servicesDetailsRepository.save(serviceDetails);

        } catch (Exception ex) {
            System.out.println("Exception2: " + ex.getMessage());
        }
        return "redirect:/provider-page";
    }

    @GetMapping("/delete-serviceOffer")
    public String deleteServiceOffer(@RequestParam int id, RedirectAttributes redirectAttributes) {
        try {
            servicesDetailsRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Offer deleted successfully!");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/provider-page";
    }

    @GetMapping("/provider/requests")
    public String serviceApplications(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerName = userDetails.getFullName();

        List<UserServiceOffer> serviceOffers = userServiceOfferRepository.findByServiceDetails_ServiceProvider(providerName);

        List<ServiceApplicationDto> serviceApplicationDtos = serviceOffers.stream()
                .map(UserMapper::toServiceApplicationDto)
                .collect(Collectors.toList());

        model.addAttribute("serviceApplicationDtos", serviceApplicationDtos);
        return "ProviderDashboard/ServiceApplications";
    }


    @GetMapping("/updateStatus")
    public String updateStatus(@RequestParam Long serviceApplicationId, @RequestParam String status, RedirectAttributes redirectAttributes) {
        UserServiceOffer serviceOffer = userServiceOfferRepository.findById(serviceApplicationId).orElseThrow(() -> new IllegalArgumentException("Invalid service application Id:" + serviceApplicationId));
        serviceOffer.setStatus(status);
        userServiceOfferRepository.save(serviceOffer);
        redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        return "redirect:/provider/requests";
    }

    @GetMapping("/provider/clients-feedback")
    public String getClientsFeedback(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerName = userDetails.getFullName();

        // Obține toate serviciile furnizate de providerul autentificat
        List<ServiceDetails> userServices = servicesDetailsRepository.findByServiceProvider(providerName);

        // Colectează toate comentariile pentru serviciile furnizorului
        List<UserServiceComments> allComments = new ArrayList<>();
        for (ServiceDetails service : userServices) {
            List<UserServiceComments> comments = userServiceCommentsRepository.findByServiceDetails(service);
            allComments.addAll(comments);
        }

        model.addAttribute("comments", allComments);
        return "ProviderDashboard/ClientsFeedback";
    }









}

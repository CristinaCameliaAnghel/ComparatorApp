package etti.comparator.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import etti.comparator.dto.ServiceDetailsDto;
import etti.comparator.model.ServiceDetails;
import etti.comparator.repositories.ServicesDetailsRepository;
import etti.comparator.services.CustomUserDetail;

@Controller
public class ProviderController {

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @GetMapping("/provider-page")
    public String providerPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            model.addAttribute("user", userDetails);

            String fullName = userDetails.getFullName();
            List<ServiceDetails> userServices = servicesDetailsRepository.findByServiceProvider(fullName);

            List<ServiceDetailsDto> userServicesDto = userServices.stream().map(service -> {
                ServiceDetailsDto dto = new ServiceDetailsDto();
                Date registeredAt = new Date();
                dto.setName(service.getName());
                dto.setServiceProvider(service.getServiceProvider());
                dto.setEstimatedDuration(service.getEstimatedDuration());
                dto.setPrice(service.getPrice());
                dto.setOnlineBookingAvailability(service.getOnlineBookingAvailability());
                dto.setRequiredEquipment(service.getRequiredEquipment());
                dto.setGuarantee(service.getGuarantee());
                dto.setApprovedCertifiedLicensed(service.getApprovedCertifiedLicensed());
                dto.setGeographicCoverage(service.getGeographicCoverage());
                dto.setDescription(service.getDescription());
                dto.setFeedbackStars(service.getFeedbackStars());
                dto.setRegisteredAt(registeredAt);
                return dto;
            }).collect(Collectors.toList());

            model.addAttribute("userServices", userServicesDto);
        }

        return "provider";
    }

    @GetMapping("/create-serviceOffer")
    public String showCreateOfferForm(Model model) {
        model.addAttribute("serviceDetailsDto", new ServiceDetailsDto());
        return "serviceOffers/CreateServiceOffers";
    }

    @PostMapping("/add-serviceOffer")
    public String addOffer(@ModelAttribute ServiceDetailsDto serviceDetailsDto, Principal principal, RedirectAttributes redirectAttributes) {
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
}

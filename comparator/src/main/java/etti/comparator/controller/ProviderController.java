package etti.comparator.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import etti.comparator.Mappers.UserMapper;
import etti.comparator.Mappers.UtilityDetailsMapper;
import etti.comparator.dto.ServiceApplicationDto;
import etti.comparator.dto.UtilityApplicationDto;
import etti.comparator.dto.UtilityDetailsDto;
import etti.comparator.model.*;
import etti.comparator.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import etti.comparator.dto.ServiceDetailsDto;
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

    @Autowired
    private UtilitiesDetailsRepository utilitiesDetailsRepository;

    @Autowired
    private UserUtilityOfferRepository userUtilityOfferRepository;

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
        serviceDetails.setServiceOfferName(serviceDetailsDto.getServiceOfferName());
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
            serviceDetailsDto.setServiceOfferName(serviceDetails.getServiceOfferName());
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
            serviceDetails.setServiceOfferName(serviceDetailsDto.getServiceOfferName());

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

 /*   @GetMapping("/provider/requests")
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
*/
 @GetMapping("/provider/requests")
 public String serviceApplications(Model model) {
     CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String providerName = userDetails.getFullName();

     List<UserServiceOffer> serviceOffers = userServiceOfferRepository.findByServiceDetails_ServiceProvider(providerName);

     List<ServiceApplicationDto> serviceApplicationDtos = serviceOffers.stream()
             .map(serviceOffer -> {
                 ServiceApplicationDto applicationDto = UserMapper.toServiceApplicationDto(serviceOffer);
                 applicationDto.setServiceOfferName(serviceOffer.getServiceDetails().getServiceOfferName());
                 return applicationDto;
             })
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

//utilitati

  /*  @GetMapping("/provider-utilities")
    public String providerUtilitiesPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            model.addAttribute("user", userDetails);

            String fullName = userDetails.getFullName();
            List<UtilityDetails> userUtilities = utilitiesDetailsRepository.findByUtilityProvider(fullName);
            model.addAttribute("userUtilities", userUtilities);
        }

        return "provider-utility";
    }*/
  @GetMapping("/provider-utilities")
  public String providerUtilitiesPage(Model model) {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      if (principal instanceof CustomUserDetail) {
          CustomUserDetail userDetails = (CustomUserDetail) principal;
          model.addAttribute("user", userDetails);

          Long userId = userDetails.getId();
          Optional<Provider> optionalProvider = providerRepository.findByUserId(userId);

          if (optionalProvider.isPresent()) {
              Provider provider = optionalProvider.get();
              if (provider.getUtilityName() == null || provider.getUtilityName().isEmpty()) {
                  model.addAttribute("noUtilityName", true);
              } else {
                  String fullName = userDetails.getFullName();
                  List<UtilityDetails> userUtilities = utilitiesDetailsRepository.findByUtilityProvider(fullName);
                  model.addAttribute("userUtilities", userUtilities);
                  model.addAttribute("noUtilityName", false);
              }
          } else {
              model.addAttribute("noUtilityName", true);
          }
      }

      return "provider-utility";
  }



    @GetMapping("/create-utilityOffer")
    public String showUtilityCreateOfferForm(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) principal;
            Optional<Provider> providerOpt = providerRepository.findByUserId(userDetails.getId());
            if (providerOpt.isPresent()) {
                Provider provider = providerOpt.get();
                UtilityDetailsDto utilityDetailsDto = new UtilityDetailsDto();
                utilityDetailsDto.setName(provider.getUtilityName());
                model.addAttribute("utilityDetailsDto", utilityDetailsDto);
            } else {
                model.addAttribute("utilityDetailsDto", new UtilityDetailsDto());
            }
        } else {
            model.addAttribute("utilityDetailsDto", new UtilityDetailsDto());
        }
        return "/utilityOffers/CreateUtilityOffer";
    }


    @PostMapping("/add-utilityOffer")
    public String addUtilityOffer(@ModelAttribute UtilityDetailsDto utilityDetailsDto, Principal principal, RedirectAttributes redirectAttributes) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UtilityDetails utilityDetails = new UtilityDetails();
        utilityDetails.setName(utilityDetailsDto.getName());
        utilityDetails.setUtilityProvider(userDetails.getFullName());
        utilityDetails.setPrice(utilityDetailsDto.getPrice());
        utilityDetails.setUnitOfMeasure(utilityDetailsDto.getUnitOfMeasure());
        utilityDetails.setUtilityOfferName(utilityDetailsDto.getUtilityOfferName());
        utilityDetails.setGeographicCoverage(utilityDetailsDto.getGeographicCoverage());
        utilityDetails.setContractDuration(utilityDetailsDto.getContractDuration());
        utilityDetails.setCustomerType(utilityDetailsDto.getCustomerType());
        utilityDetails.setPaymentFrequency(utilityDetailsDto.getPaymentFrequency());
        utilityDetails.setDescription(utilityDetailsDto.getDescription());
        utilityDetails.setRegisteredAt(new Date());

        utilitiesDetailsRepository.save(utilityDetails);
        redirectAttributes.addFlashAttribute("message", "Utility offer created successfully!");
        return "redirect:/provider-page";
    }
    @GetMapping("/edit-utilityOffer")
    public String showUtilityEditOfferForm(Model model, @RequestParam int id) {
        try {
            UtilityDetails utilityDetails = utilitiesDetailsRepository.findById(id).get();
            model.addAttribute("utilityDetails", utilityDetails);

            UtilityDetailsDto utilityDetailsDto = new UtilityDetailsDto();
            utilityDetailsDto.setName(utilityDetails.getName());
            utilityDetailsDto.setUtilityProvider(utilityDetails.getUtilityProvider());
            utilityDetailsDto.setPrice(utilityDetails.getPrice());
            utilityDetailsDto.setUnitOfMeasure(utilityDetails.getUnitOfMeasure());
            utilityDetailsDto.setUtilityOfferName(utilityDetails.getUtilityOfferName());
            utilityDetailsDto.setGeographicCoverage(utilityDetails.getGeographicCoverage());
            utilityDetailsDto.setContractDuration(utilityDetails.getContractDuration());
            utilityDetailsDto.setCustomerType(utilityDetails.getCustomerType());
            utilityDetailsDto.setPaymentFrequency(utilityDetails.getPaymentFrequency());
            utilityDetailsDto.setDescription(utilityDetails.getDescription());
            utilityDetailsDto.setRegisteredAt(utilityDetails.getRegisteredAt());

            model.addAttribute("utilityDetailsDto", utilityDetailsDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/provider-page";
        }
        return "utilityOffers/EditUtilityOffer";
    }

    @Autowired
    private UtilityDetailsMapper utilityDetailsMapper;

    @PostMapping("/edit-utilityOffer")
    public String updateUtilityOffer(
            Model model,
            @RequestParam int id,
            @ModelAttribute UtilityDetailsDto utilityDetailsDto,
            BindingResult result) {

        try {


            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                }
            }
           UtilityDetails utilityDetails = utilitiesDetailsRepository.save(utilityDetailsMapper.toModel(utilityDetailsDto));


            model.addAttribute("utilityDetails", utilityDetails);

        } catch (Exception ex) {
            System.out.println("Exception2: " + ex.getMessage());
        }
        return "redirect:/provider-utilities";
    }

    @GetMapping("/delete-utilityOffer")
    public String deleteUtilityOffer(@RequestParam int id, RedirectAttributes redirectAttributes) {
        try {
            utilitiesDetailsRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Utility offer deleted successfully!");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/provider-page";
    }






    @GetMapping("/provider/requests-utility")
    public String utilityApplications(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        Optional<Provider> optionalProvider = providerRepository.findByUserId(userId);

        if (optionalProvider.isPresent()) {
            Provider provider = optionalProvider.get();
            if (provider.getUtilityName() == null || provider.getUtilityName().isEmpty()) {
                model.addAttribute("noUtilityName", true);
            } else {
                String providerName = userDetails.getFullName();
                List<UserUtilityOffer> utilityOffers = userUtilityOfferRepository.findByUtilityDetails_UtilityProvider(providerName);

                List<UtilityApplicationDto> utilityApplicationDtos = utilityOffers.stream()
                        .map(utilityOffer -> {
                            UtilityApplicationDto applicationDto = UserMapper.toUtilityApplicationDto(utilityOffer);
                            applicationDto.setUtilityOfferName(utilityOffer.getUtilityDetails().getUtilityOfferName());
                            return applicationDto;
                        })
                        .collect(Collectors.toList());

                model.addAttribute("utilityApplicationDtos", utilityApplicationDtos);
                model.addAttribute("noUtilityName", false);
            }
        } else {
            model.addAttribute("noUtilityName", true);
        }

        return "ProviderDashboard/UtilityApplications";
    }

    @GetMapping("/updateStatus-utility")
    public String updateStatusForUtility(@RequestParam Long utilityApplicationId, @RequestParam String status, RedirectAttributes redirectAttributes) {
        UserUtilityOffer utilityOffer = userUtilityOfferRepository.findById(utilityApplicationId).orElseThrow(() -> new IllegalArgumentException("Invalid Utility application Id:" + utilityApplicationId));
        utilityOffer.setStatus(status);
        userUtilityOfferRepository.save(utilityOffer);
        redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        return "redirect:/provider/requests-utility";
    }


}

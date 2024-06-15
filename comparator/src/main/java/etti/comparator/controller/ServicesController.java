package etti.comparator.controller;

import etti.comparator.dto.ServiceDetailsDto;
import etti.comparator.dto.UserServiceCommentDto;
import etti.comparator.model.Service;
import etti.comparator.model.ServiceDetails;
import etti.comparator.model.UserServiceComments;
import etti.comparator.repositories.ServicesDetailsRepository;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UserServiceCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ServicesController {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    @Autowired
    private UserServiceCommentsRepository userServiceCommentsRepository;

    @GetMapping("/services")
    public String showServices(Model model) {
        List<Service> serviceEntities = servicesRepository.findAll();
        model.addAttribute("services", serviceEntities);
        return "services";
    }

 @GetMapping("/service-list")
    public String showServiceList(Model model, @RequestParam(required = false) String name) {
        List<ServiceDetails> serviceDetailsList;
        if (name != null && !name.isEmpty()) {
            serviceDetailsList = servicesDetailsRepository.findByName(name);
            model.addAttribute("selectedName", name);
        } else {
            serviceDetailsList = servicesDetailsRepository.findAll();
        }

     // Adăugarea listei de servicii în model
        model.addAttribute("serviceDetailsList", serviceDetailsList);

     // Map pentru a stoca comentariile pentru fiecare serviciu
        Map<Integer, List<UserServiceComments>> commentsMap = new HashMap<>();
        for (ServiceDetails serviceDetails : serviceDetailsList) {
            commentsMap.put(serviceDetails.getId(), userServiceCommentsRepository.findByServiceDetails(serviceDetails));
        }
        model.addAttribute("commentsMap", commentsMap);

     // Mesaj de eroare dacă lista de servicii este goală
     if (serviceDetailsList.isEmpty()) {
         model.addAttribute("noServicesMessage", "Nu există înregistrări pentru categoria selectată.");
     }

        return "service_list";
    }




}

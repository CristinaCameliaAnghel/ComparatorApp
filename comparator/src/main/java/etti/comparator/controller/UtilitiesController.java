package etti.comparator.controller;

import etti.comparator.model.*;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UserUtilityCommentsRepository;
import etti.comparator.repositories.UtilitiesDetailsRepository;
import etti.comparator.repositories.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UtilitiesController {
    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private UtilitiesDetailsRepository utilitiesDetailsRepository;

    @Autowired
    private UserUtilityCommentsRepository userUtilityCommentsRepository;

    @GetMapping("/utilities")
    public String showUtilities(Model model) {
        List<Utility> utilityEntities = utilityRepository.findAll();
        model.addAttribute("utilities", utilityEntities);
        return "utilities";
    }


    @GetMapping("/utility-list")
    public String showUtilityList(Model model, @RequestParam(required = false) String name) {
        List<UtilityDetails> utilityDetailsList;
        if (name != null && !name.isEmpty()) {
            utilityDetailsList = utilitiesDetailsRepository.findByName(name);
            model.addAttribute("selectedName", name);
        } else {
            utilityDetailsList = utilitiesDetailsRepository.findAll();
        }

        // Adăugarea listei de utilități în model
        model.addAttribute("utilityDetailsList", utilityDetailsList);

        // Map pentru a stoca comentariile pentru fiecare utilitate
        Map<Integer, List<UserUtilityComments>> commentsMap = new HashMap<>();
        for (UtilityDetails utilityDetails : utilityDetailsList) {
            commentsMap.put(utilityDetails.getId(), userUtilityCommentsRepository.findByUtilityDetails(utilityDetails));
        }
        model.addAttribute("commentsMap", commentsMap);

        // Mesaj de eroare dacă lista de utilități este goală
        if (utilityDetailsList.isEmpty()) {
            model.addAttribute("noUtilitiesMessage", "Nu există înregistrări pentru categoria selectată.");
        }

        return "utility_list";
    }


}
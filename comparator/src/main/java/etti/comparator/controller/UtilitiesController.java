package etti.comparator.controller;

import etti.comparator.model.Service;
import etti.comparator.model.Utility;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UtilitiesController {
    @Autowired
    private UtilityRepository utilityRepository;

    @GetMapping("/utilities")
    public String showUtilities(Model model) {
        List<Utility> utilityEntities = utilityRepository.findAll();
        model.addAttribute("utilities", utilityEntities);
        return "utilities";
    }
}

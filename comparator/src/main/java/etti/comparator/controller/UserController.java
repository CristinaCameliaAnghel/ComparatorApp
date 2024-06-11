package etti.comparator.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import etti.comparator.Mappers.ServiceDetailsMapper;
import etti.comparator.Mappers.UserMapper;
import etti.comparator.Mappers.UserServiceOfferMapper;
import etti.comparator.dto.ProviderDto;
import etti.comparator.dto.ServiceDetailsDto;
import etti.comparator.dto.UserServiceOfferDto;
import etti.comparator.model.User;
import etti.comparator.model.UserServiceOffer;
import etti.comparator.repositories.ServicesRepository;
import etti.comparator.repositories.UserRepository;
import etti.comparator.repositories.UserServiceOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import etti.comparator.dto.UserDto;
import etti.comparator.services.UserService;

@Controller
public class UserController {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserServiceOfferRepository userServiceOfferRepository;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {

        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Inregistrat cu succes!");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /*  @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(principal.getName());

        List<UserServiceOffer> userServiceOffers = userServiceOfferRepository.findByUser(user);
        List<ServiceDetailsDto> serviceDetailsDtoList = userServiceOffers.stream()
                .map(UserServiceOffer::getServiceDetails)
                .map(ServiceDetailsMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("user", UserMapper.toDto(user));
        //model.addAttribute("userServiceOffers", serviceDetailsDtoList);
        model.addAttribute("userServiceOffers", userServiceOffers);  // Changed to userServiceOffers to include status
        return "user";
    }*/
    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
        // Obține detaliile utilizatorului autentificat
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(principal.getName());

        // Obține ofertele de servicii ale utilizatorului
        List<UserServiceOffer> userServiceOffers = userServiceOfferRepository.findByUser(user);
        List<UserServiceOfferDto> userServiceOfferDtoList = userServiceOffers.stream()
                .map(UserServiceOfferMapper::toDto)
                .collect(Collectors.toList());

        // Adaugă utilizatorul și ofertele în model
        model.addAttribute("user", UserMapper.toDto(user));
        model.addAttribute("userServiceOffers", userServiceOfferDtoList);
        // Returnează numele template-ului Thymeleaf
        return "user";
    }

    @GetMapping("/registrationasprovider")
    public String getProviderRegistrationPage(@ModelAttribute("provider") ProviderDto providerDto) {
        return "registerasprovider";
    }

    @PostMapping("/registrationasprovider")
    public String saveProvider(@ModelAttribute("provider") ProviderDto providerDto, Model model) {
        userService.saveProvider(providerDto);
        model.addAttribute("message", "Inregistrat cu succes!");
        return "registerasprovider";
    }





    }



package etti.comparator.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import etti.comparator.Mappers.*;
import etti.comparator.dto.*;
import etti.comparator.model.*;
import etti.comparator.repositories.*;
import etti.comparator.services.UserServiceImpl;
import etti.comparator.services.UtilityDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private UtilityRepository utilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;


    @Autowired
    private UserServiceOfferRepository userServiceOfferRepository;

    @Autowired
    private UserServiceCommentsRepository userServiceCommentsRepository;

    @Autowired
    private UserServiceCommentMapper userServiceCommentsMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {

        return "register";
    }

/*  @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Inregistrat cu succes!");
        return "register";
    }*/

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            model.addAttribute("error", "Adresa de email este deja folosită.");
            return "register";
        }
        userService.save(userDto);
        model.addAttribute("message", "Inregistrat cu succes!");
        return "register";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

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
    public String getProviderRegistrationPage(@ModelAttribute("provider") ProviderDto providerDto, Model model) {
        model.addAttribute("services", servicesRepository.findAll());
        model.addAttribute("utilities", utilityRepository.findAll());
        return "registerasprovider";
    }

    @PostMapping("/registrationasprovider")
    public String saveProvider(@Valid @ModelAttribute("provider") ProviderDto providerDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("services", servicesRepository.findAll());
            model.addAttribute("utilities", utilityRepository.findAll());
            return "registerasprovider";
        }
        userService.saveProvider(providerDto);
        model.addAttribute("message", "Inregistrat cu succes!");
        return "registerasprovider";
    }

    @GetMapping("/comments/{serviceDetailsId}")
    @ResponseBody
    public List<UserServiceCommentDto> getCommentsByServiceDetailsId(@PathVariable int serviceDetailsId) {
        ServiceDetails serviceDetails = servicesDetailsRepository.findById(serviceDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service details ID: " + serviceDetailsId));
        List<UserServiceComments> comments = userServiceCommentsRepository.findByServiceDetails(serviceDetails);
        return comments.stream().map(UserServiceCommentMapper::toDto).collect(Collectors.toList());
    }


    // Metoda pentru afișarea profilului utilizatorului
    @GetMapping("/profile")
    public String showProfile(Model model, @RequestParam(name = "id", required = false) Long userId, Principal principal) {
        if (userId == null) {
            // Obține utilizatorul curent autentificat
            User user = userRepository.findByEmail(principal.getName());
            userId = user.getId();
        }
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }

    // Metoda pentru actualizarea profilului utilizatorului
    @PostMapping("/profile")
    public String updateProfile(User user, Principal principal) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFullName(user.getFullName());
            // Codifică parola nouă dacă este schimbată
            if (!user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(existingUser);
        }
        return "redirect:/profile?id=" + user.getId();
    }


    @Autowired
    private UtilityDetailsService utilityDetailsService;
    @GetMapping("/apply-utility")
    public String applyForUtility(@RequestParam("utilityId") int utilityId, @AuthenticationPrincipal UserDetails currentUser, org.springframework.ui.Model model) {
        User user = userServiceImpl.findUserByEmail(currentUser.getUsername());

        UtilityDetails utilityDetails = utilityDetailsService.findById(utilityId);

        // Pregătește modelul pentru a fi utilizat în formularul de aplicare
        model.addAttribute("userId", user.getId());
        model.addAttribute("utilityId", utilityDetails.getId());
        UserUtilityOffer userUtilityOffer= new UserUtilityOffer();
        model.addAttribute("userUtilityOffer", new UserUtilityOffer());

        // Redirecționează către pagina de formular de aplicare
        return "UtilityFormApplication";
    }


    @Autowired
    private UserUtilityOfferRepository userUtilityOfferRepository;
  @PostMapping("/apply-utility")
    public String submitApplicationFormForUtility(@ModelAttribute UserUtilityOffer userUtilityOffer, @AuthenticationPrincipal UserDetails currentUser) {
        // Obține user-ul autentificat
        User user = userServiceImpl.findUserByEmail(currentUser.getUsername());
      userUtilityOffer.setUser(user);

        // Setează starea inițială și alte date implicite
      userUtilityOffer.setStatus("in asteptare");

        // Salvează entitatea UserServiceOffer
      userUtilityOfferRepository.save(userUtilityOffer);

        // Redirecționează către o pagină de confirmare sau înapoi la pagina de utilizator
        return "redirect:/user-page";
    }
    @GetMapping("/user-page-utility")
    public String userPageForUtility(Model model, Principal principal) {
        // Obține detaliile utilizatorului autentificat
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(principal.getName());

        // Obține ofertele de servicii ale utilizatorului
        List<UserUtilityOffer> userUtilityOffers = userUtilityOfferRepository.findByUser(user);
        List<UserUtilityOfferDto> userUtilityOfferDtoList = userUtilityOffers.stream()
                .map(UserUtilityOfferMapper::toDto)
                .collect(Collectors.toList());

        // Adaugă utilizatorul și ofertele în model
        model.addAttribute("user", UserMapper.toDto(user));
        model.addAttribute("userUtilityOffers", userUtilityOfferDtoList);

        // Returnează numele template-ului Thymeleaf
        return "user-utility";
    }


    @Autowired
    private UtilitiesDetailsRepository utilityDetailsRepository;

    @Autowired
    private UserUtilityCommentsRepository userUtilityCommentsRepository;

    @GetMapping("/comments-utility/{utilityDetailsId}")
    @ResponseBody
    public List<UserUtilityCommentDto> getCommentsByUtilityDetailsId(@PathVariable int utilityDetailsId) {
        UtilityDetails utilityDetails = utilityDetailsRepository.findById(utilityDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid utility details ID: " + utilityDetailsId));
        List<UserUtilityComments> comments = userUtilityCommentsRepository.findByUtilityDetails(utilityDetails);
        return comments.stream().map(UserUtilityCommentMapper::toDto).collect(Collectors.toList());
    }


}
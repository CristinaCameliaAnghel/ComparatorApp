package etti.comparator.controller;

import etti.comparator.Mappers.UserMapper;
import etti.comparator.Mappers.UserUtilityOfferMapper;
import etti.comparator.dto.UserUtilityOfferDto;
import etti.comparator.model.User;
import etti.comparator.model.UserUtilityOffer;
import etti.comparator.repositories.UserRepository;
import etti.comparator.repositories.UserUtilityOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserUtilityController {
    @Autowired
    private UserUtilityOfferRepository userUtilityOfferRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/user-page-utilities")
    public String userUtilitiesPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userRepository.findByEmail(principal.getName());

        List<UserUtilityOffer> userUtilityOffers = userUtilityOfferRepository.findByUser(user);
        List<UserUtilityOfferDto> userUtilityOfferDtoList = userUtilityOffers.stream()
                .map(UserUtilityOfferMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("user", UserMapper.toDto(user));
        model.addAttribute("userUtilityOffers", userUtilityOfferDtoList);

        return "userUtilityList";
    }
}

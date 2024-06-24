package etti.comparator.controller;

import etti.comparator.Mappers.UserServiceCommentMapper;
import etti.comparator.Mappers.UserUtilityCommentMapper;
import etti.comparator.dto.UserServiceCommentDto;
import etti.comparator.dto.UserUtilityCommentDto;
import etti.comparator.model.*;
import etti.comparator.repositories.UserRepository;
import etti.comparator.repositories.UserServiceCommentsRepository;
import etti.comparator.repositories.UserUtilityCommentsRepository;
import etti.comparator.services.ServiceDetailsService;
import etti.comparator.services.UtilityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private UserServiceCommentsRepository userServiceCommentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceDetailsService serviceDetailsService;

    @Autowired
    private UserServiceCommentMapper userServiceCommentMapper;

    @PostMapping("/submit")
    public String submitComment(@ModelAttribute UserServiceCommentDto commentDTO, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername());
        ServiceDetails serviceDetails = serviceDetailsService.findById(commentDTO.getServiceId());

        if (serviceDetails == null) {
            throw new RuntimeException("Service not found");
        }

        UserServiceComments comment = userServiceCommentMapper.toEntity(commentDTO, user, serviceDetails);
        userServiceCommentsRepository.save(comment);

        return "redirect:/user-page";
    }
    @Autowired
    private UserUtilityCommentsRepository userUtilityCommentsRepository;

    @Autowired
    private UtilityDetailsService utilityDetailsService;

    @Autowired
    private UserUtilityCommentMapper userUtilityCommentMapper;

    @PostMapping("/submit-for-utility")
    public String submitCommentForUtility(@ModelAttribute UserUtilityCommentDto commentDTO, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername());
        UtilityDetails utilityDetails = utilityDetailsService.findById(commentDTO.getUtilityId());

        if (utilityDetails == null) {
            throw new RuntimeException("Utility not found");
        }

        UserUtilityComments comment = userUtilityCommentMapper.toEntity(commentDTO, user, utilityDetails);
        userUtilityCommentsRepository.save(comment);

        return "redirect:/user-page";
    }
}

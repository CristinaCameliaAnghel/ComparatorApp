package etti.comparator.controller;

import etti.comparator.Mappers.UserServiceCommentMapper;
import etti.comparator.dto.UserServiceCommentDto;
import etti.comparator.model.ServiceDetails;
import etti.comparator.model.User;
import etti.comparator.model.UserServiceComments;
import etti.comparator.repositories.UserRepository;
import etti.comparator.repositories.UserServiceCommentsRepository;
import etti.comparator.services.ServiceDetailsService;
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
        ServiceDetails serviceDetails = serviceDetailsService.findById(commentDTO.getOfferId());

        if (serviceDetails == null) {
            throw new RuntimeException("Service not found");
        }

        UserServiceComments comment = userServiceCommentMapper.toEntity(commentDTO, user, serviceDetails);
        userServiceCommentsRepository.save(comment);

        return "redirect:/user-page";
    }
}

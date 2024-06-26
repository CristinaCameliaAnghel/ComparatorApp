package etti.comparator.Mappers;

import etti.comparator.dto.UserServiceCommentDto;
import etti.comparator.model.ServiceDetails;
import etti.comparator.model.User;
import etti.comparator.model.UserServiceComments;
import org.springframework.stereotype.Component;

@Component
public class UserServiceCommentMapper {
    public UserServiceComments toEntity(UserServiceCommentDto dto, User user, ServiceDetails serviceDetails) {
        UserServiceComments comment = new UserServiceComments();
        comment.setUser(user);
        comment.setServiceDetails(serviceDetails);
        comment.setComment(dto.getComment());
        comment.setEvaluationScore(dto.getEvaluationScore());
        return comment;
    }

    public static UserServiceCommentDto toDto(UserServiceComments comment) {
        UserServiceCommentDto dto = new UserServiceCommentDto();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setEvaluationScore(comment.getEvaluationScore());
        dto.setUserName(comment.getUser().getFullName());
        return dto;
    }
}

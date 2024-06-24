package etti.comparator.Mappers;


import etti.comparator.dto.UserUtilityCommentDto;
import etti.comparator.model.*;
import org.springframework.stereotype.Component;

@Component
public class UserUtilityCommentMapper {
    public UserUtilityComments toEntity(UserUtilityCommentDto dto, User user, UtilityDetails utilityDetails) {
        UserUtilityComments comment = new UserUtilityComments();
        comment.setUser(user);
        comment.setUtilityDetails(utilityDetails);
        comment.setComment(dto.getComment());
        comment.setEvaluationScore(dto.getEvaluationScore());
        return comment;
    }

    public static UserUtilityCommentDto toDto(UserUtilityComments comment) {
        UserUtilityCommentDto dto = new UserUtilityCommentDto();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setEvaluationScore(comment.getEvaluationScore());
        dto.setUserName(comment.getUser().getFullName());
        return dto;
    }
}

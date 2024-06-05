package etti.comparator.Mappers;

import etti.comparator.dto.UserDto;
import etti.comparator.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        return userDto;
    }
}

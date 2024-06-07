package etti.comparator.Mappers;

import etti.comparator.dto.ServiceApplicationDto;
import etti.comparator.dto.UserDto;
import etti.comparator.model.User;
import etti.comparator.model.UserServiceOffer;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        return userDto;
    }
    public static ServiceApplicationDto toServiceApplicationDto(UserServiceOffer offer) {
        User user = offer.getUser();
        ServiceApplicationDto dto = new ServiceApplicationDto();
        dto.setId(Math.toIntExact(offer.getId()));  // Setează ID-ul ofertei, convertit în int
        dto.setFullName(user.getFullName());
        dto.setEmail(offer.getEmail());
        dto.setPhone(offer.getPhone());
        dto.setAddress(offer.getAdress());
        dto.setDescription(offer.getDescription());
        dto.setStatus(offer.getStatus());  // Setează statusul ofertei
        return dto;
    }

}

package etti.comparator.Mappers;

import etti.comparator.dto.UserServiceOfferDto;
import etti.comparator.model.UserServiceOffer;

public class UserServiceOfferMapper {
    public static UserServiceOfferDto toDto(UserServiceOffer offer) {
        UserServiceOfferDto dto = new UserServiceOfferDto();
        dto.setId(offer.getId());
        dto.setUser(offer.getUser());
        dto.setServiceDetails(offer.getServiceDetails());
        dto.setStatus(offer.getStatus());
        dto.setDescription(offer.getDescription());
        dto.setEmail(offer.getEmail());
        dto.setPhone(offer.getPhone());
        dto.setAddress(offer.getAddress());
        return dto;
    }
}


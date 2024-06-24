package etti.comparator.Mappers;


import etti.comparator.dto.UserUtilityOfferDto;

import etti.comparator.model.UserUtilityOffer;

public class UserUtilityOfferMapper {
    public static UserUtilityOfferDto toDto(UserUtilityOffer offer) {
        UserUtilityOfferDto dto = new UserUtilityOfferDto();
        dto.setId(offer.getId());
        dto.setUser(offer.getUser());
        dto.setUtilityDetails(offer.getUtilityDetails());
        dto.setStatus(offer.getStatus());
        dto.setDescription(offer.getDescription());
        dto.setEmail(offer.getEmail());
        dto.setPhone(offer.getPhone());
        dto.setAddress(offer.getAddress());
        return dto;
    }
}

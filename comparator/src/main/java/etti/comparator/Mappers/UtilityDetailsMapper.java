package etti.comparator.Mappers;

import etti.comparator.dto.UtilityDetailsDto;
import etti.comparator.model.UtilityDetails;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface UtilityDetailsMapper {
    UtilityDetails toModel (UtilityDetailsDto dto);
}

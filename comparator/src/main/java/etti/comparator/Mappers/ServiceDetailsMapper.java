package etti.comparator.Mappers;

import etti.comparator.dto.ServiceDetailsDto;
import etti.comparator.model.ServiceDetails;
import org.springframework.stereotype.Component;

@Component
public class ServiceDetailsMapper {

    public static ServiceDetailsDto toDto(ServiceDetails serviceDetails) {
        ServiceDetailsDto dto = new ServiceDetailsDto();
        dto.setName(serviceDetails.getName());
        dto.setServiceProvider(serviceDetails.getServiceProvider());
        dto.setPrice(serviceDetails.getPrice());
        dto.setOnlineBookingAvailability(serviceDetails.getOnlineBookingAvailability());
        dto.setEstimatedDuration(serviceDetails.getEstimatedDuration());
        dto.setRequiredEquipment(serviceDetails.getRequiredEquipment());
        dto.setGuarantee(serviceDetails.getGuarantee());
        dto.setApprovedCertifiedLicensed(serviceDetails.getApprovedCertifiedLicensed());
        dto.setFeedbackStars(serviceDetails.getFeedbackStars());
        dto.setGeographicCoverage(serviceDetails.getGeographicCoverage());
        dto.setDescription(serviceDetails.getDescription());
        return dto;
    }
}

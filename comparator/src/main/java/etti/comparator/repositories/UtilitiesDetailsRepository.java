package etti.comparator.repositories;

import etti.comparator.model.ServiceDetails;
import etti.comparator.model.UtilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilitiesDetailsRepository extends JpaRepository<UtilityDetails, Integer> {
    List<UtilityDetails> findByName(String name);
    List<UtilityDetails> findAllByIdIn(List<Integer> ids);
    List<UtilityDetails> findByUtilityProvider(String utilityProvider);
}

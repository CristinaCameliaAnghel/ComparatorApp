package etti.comparator.repositories;

import etti.comparator.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserUtilityOfferRepository extends JpaRepository<UserUtilityOffer, Long>

    {
        List<UserUtilityOffer> findByUser(User user);
        List<UserUtilityOffer> findByUtilityDetails(UtilityDetails utilityDetails);
        List<UserUtilityOffer> findByUtilityDetails_UtilityProvider(String utilityProvider);
}

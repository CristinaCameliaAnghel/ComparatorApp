package etti.comparator.repositories;

import etti.comparator.model.ServiceDetails;
import etti.comparator.model.UserServiceComments;
import etti.comparator.model.UserUtilityComments;
import etti.comparator.model.UtilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserUtilityCommentsRepository extends JpaRepository<UserUtilityComments, Integer> {
    List<UserUtilityComments> findByUtilityDetails(UtilityDetails utilityDetails);
}

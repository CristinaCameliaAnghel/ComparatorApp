package etti.comparator.repositories;

import etti.comparator.model.ServiceDetails;
import etti.comparator.model.UserServiceComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface UserServiceCommentsRepository extends JpaRepository<UserServiceComments, Integer> {
    List<UserServiceComments> findByServiceDetails(ServiceDetails serviceDetails);

}
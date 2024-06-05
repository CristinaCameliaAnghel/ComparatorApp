package etti.comparator.repositories;

import etti.comparator.model.User;
import etti.comparator.model.UserServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceOfferRepository extends JpaRepository<UserServiceOffer, Long> {
    List<UserServiceOffer> findByUser(User user);
}

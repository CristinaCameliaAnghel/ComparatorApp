package etti.comparator.repositories;

import etti.comparator.model.UserServiceComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceCommentsRepository extends JpaRepository<UserServiceComments, Long> {
}

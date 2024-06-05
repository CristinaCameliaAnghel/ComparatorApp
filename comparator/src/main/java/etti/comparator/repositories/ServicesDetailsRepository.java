package etti.comparator.repositories;

import etti.comparator.model.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesDetailsRepository extends JpaRepository<ServiceDetails, Integer> {
    List<ServiceDetails> findByName(String name);
    List<ServiceDetails> findAllByIdIn(List<Integer> ids);

    List<ServiceDetails> findByServiceProvider(String serviceProvider);
}

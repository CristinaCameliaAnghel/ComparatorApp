package etti.comparator.repositories;

import etti.comparator.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Service, Integer>{
}

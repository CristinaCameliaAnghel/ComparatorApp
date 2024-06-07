package etti.comparator.services;

import etti.comparator.model.ServiceDetails;
import etti.comparator.repositories.ServicesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDetailsService {
    @Autowired
    private ServicesDetailsRepository servicesDetailsRepository;

    public ServiceDetails findById(int id) {
        return servicesDetailsRepository.findById(id).orElse(null);
    }

    
}

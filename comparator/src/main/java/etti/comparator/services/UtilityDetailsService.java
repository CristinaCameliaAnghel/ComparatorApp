package etti.comparator.services;

import etti.comparator.model.ServiceDetails;
import etti.comparator.model.UtilityDetails;
import etti.comparator.repositories.ServicesDetailsRepository;
import etti.comparator.repositories.UtilitiesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityDetailsService {
    @Autowired
    private UtilitiesDetailsRepository utilitiesDetailsRepository;

    public UtilityDetails findById(int id) {
        return utilitiesDetailsRepository.findById(id).orElse(null);
    }

}

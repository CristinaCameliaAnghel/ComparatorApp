package etti.comparator.services;

import etti.comparator.repositories.UtilitiesDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import etti.comparator.model.UtilityDetails;
import etti.comparator.repositories.UtilitiesDetailsRepository;

@Service
public class UtilitiesDetailsService {

    @Autowired
    private UtilitiesDetailsRepository utilityDetailsRepository;

    public UtilityDetails findById(int id) {
        return utilityDetailsRepository.findById(id).orElse(null);
    }
}
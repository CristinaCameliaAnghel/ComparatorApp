package etti.comparator.services;
import etti.comparator.model.Provider;
import etti.comparator.model.User;
import etti.comparator.dto.ProviderDto;  // Presupunem că există un DTO pentru provider
import etti.comparator.repositories.ProviderRepository;
import etti.comparator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UserRepository userRepository;

    public Provider saveProvider(ProviderDto providerDto) {
        User user = userRepository.findById(providerDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Provider provider = new Provider(user, providerDto.getServiceName(), providerDto.getUtilities(), providerDto.getCif());
        return providerRepository.save(provider);
    }
}

package etti.comparator.services;

import etti.comparator.dto.ProviderDto;
import etti.comparator.dto.UserDto;
import etti.comparator.model.Provider;
import etti.comparator.model.User;
import etti.comparator.repositories.ProviderRepository;
import etti.comparator.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;  // Adăugarea ProviderRepository

    @Override
    public User save(UserDto userDto) {
        // Crearea unui user simplu
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), "USER", userDto.getFullName());
        return userRepository.save(user);
    }

    @Override
    public User saveProvider(ProviderDto providerDto) {
        // Crearea unui user cu rolul de PROVIDER
        User user = new User(providerDto.getEmail(), passwordEncoder.encode(providerDto.getPassword()), "PROVIDER", providerDto.getFullName());
        user = userRepository.save(user); // Salvarea userului în baza de date

        // Crearea și asocierea entității Provider cu User-ul creat
        Provider provider = new Provider();
        provider.setUser(user);
        provider.setServiceName(providerDto.getServiceName());
        provider.setUtilities(providerDto.getUtilities());
        provider.setCif(providerDto.getCif());

        providerRepository.save(provider); // Salvarea entității Provider în baza de date

        return user; // Returnarea userului cu rolul de PROVIDER
    }
}

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

import java.util.Optional;

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
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole(), userDto.getFullName(), userDto.isActive());
        } else {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(userDto.getRole());
            user.setFullName(userDto.getFullName());
            user.setActive(userDto.isActive());
        }
        return userRepository.save(user);
    }

    @Override
    public User saveProvider(ProviderDto providerDto) {
        User user = userRepository.findByEmail(providerDto.getEmail());
        if (user == null) {
            user = new User(providerDto.getEmail(), passwordEncoder.encode(providerDto.getPassword()), "PROVIDER", providerDto.getFullName(), false);
        } else {
            user.setPassword(passwordEncoder.encode(providerDto.getPassword()));
            user.setRole("PROVIDER");
            user.setFullName(providerDto.getFullName());
            user.setActive(false);
        }
        user = userRepository.save(user);

        Optional<Provider> providerOpt = providerRepository.findByUserId(user.getId());
        Provider provider;
        if (providerOpt.isPresent()) {
            provider = providerOpt.get();
        } else {
            provider = new Provider();
            provider.setUser(user);
        }
        provider.setServiceName(providerDto.getServiceName());
        provider.setUtilities(providerDto.getUtilities());
        provider.setCif(providerDto.getCif());

        providerRepository.save(provider);

        return user;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

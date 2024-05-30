package etti.comparator.services;
import etti.comparator.dto.ProviderDto;
import etti.comparator.dto.UserDto;
import etti.comparator.model.User;

public interface UserService {

    User save (UserDto userDto);
    User saveProvider(ProviderDto providerDto);
}
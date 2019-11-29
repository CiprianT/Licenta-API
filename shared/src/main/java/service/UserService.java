package service;

import dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDetails findByUsername(String username);
}

package br.mag.dev.orange_finance.service;

import br.mag.dev.orange_finance.domain.dto.CreateUserDto;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserDto dto) {

        var existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }

        User user = new User(
                dto.fullName(),
                dto.email(),
                dto.password(),
                dto.userRole()
        );

        return userRepository.save(user);

    }

}

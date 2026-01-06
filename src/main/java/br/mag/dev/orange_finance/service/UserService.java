package br.mag.dev.orange_finance.service;

import br.mag.dev.orange_finance.domain.dto.CreateUserDto;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.exception.BusinessException;
import br.mag.dev.orange_finance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public User createUser(CreateUserDto dto) {

        var existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new BusinessException("email already exists");
        }

        User user = new User(
                dto.fullName(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                dto.userRole()
        );

        return userRepository.save(user);

    }

}

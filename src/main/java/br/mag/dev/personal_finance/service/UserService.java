package br.mag.dev.personal_finance.service;

import br.mag.dev.personal_finance.domain.dto.user.CreateUserDto;
import br.mag.dev.personal_finance.domain.model.User;
import br.mag.dev.personal_finance.exception.BusinessException;
import br.mag.dev.personal_finance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

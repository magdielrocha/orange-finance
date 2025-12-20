package br.mag.dev.orange_finance.config;

import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.domain.model.UserRole;
import br.mag.dev.orange_finance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        var user =  new User("Magdiel Rocha", "magdiel@email.com", "123", UserRole.USER);
        userRepository.save(user);
    }
}

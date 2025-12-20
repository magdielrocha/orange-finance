package br.mag.dev.orange_finance.config;

import br.mag.dev.orange_finance.domain.model.*;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import br.mag.dev.orange_finance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    public DataLoader(UserRepository userRepository,  TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        var user =  new User("Magdiel Rocha", "magdiel@email.com", "123", UserRole.USER);
        userRepository.save(user);

        var transaction =  new Transaction("Almoco", TransactionType.EXPENSE, ExpenseCategory.FOOD, null, LocalDate.now(), new BigDecimal("100.00"), user);
        transactionRepository.save(transaction);
    }
}

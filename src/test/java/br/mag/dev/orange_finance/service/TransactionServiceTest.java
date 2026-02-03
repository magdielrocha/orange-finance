package br.mag.dev.orange_finance.service;


import br.mag.dev.orange_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.orange_finance.domain.enums.IncomeSource;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import br.mag.dev.orange_finance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;


    @Test
    public void shouldCreateTransaction() {

        User user =  new  User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Salário",
                TransactionType.INCOME,
                null,
                IncomeSource.SALARY,
                LocalDate.of(2025, 02, 01),
                new BigDecimal("5000.00")
                );

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.createTransaction(dto, user);

        assertNotNull(result);
        assertEquals(TransactionType.INCOME, result.getTransactionType());
        assertEquals(IncomeSource.SALARY, result.getIncomeSource());
        assertEquals(LocalDate.of(2025, 02, 01), result.getTransactionDate());
        assertNull(result.getExpenseCategory());

        verify(transactionRepository).save(any(Transaction.class));
    }
}

package br.mag.dev.personal_finance.service;


import br.mag.dev.personal_finance.domain.TransactionBuilder;
import br.mag.dev.personal_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.domain.model.Transaction;
import br.mag.dev.personal_finance.domain.model.User;
import br.mag.dev.personal_finance.exception.BusinessException;
import br.mag.dev.personal_finance.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    User  user = new User();


    @Test
    void shouldCreateTrasactionSuccessfully() {
        CreateTransactionDto dto = new CreateTransactionDto(
                "Test",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                null,
                LocalDate.now(),
                BigDecimal.valueOf(5000)
        );

        Transaction savedTransaction = TransactionBuilder.aTransaction()
                .withDescription(dto.description())
                .withType(dto.transactionType())
                .withExpenseCategory(dto.expenseCategory())
                .withIncomeSource(dto.incomeSource())
                .withDate(dto.transactionDate())
                .withAmount(dto.amount())
                .withUser(user)
                .build();


        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createTransaction(dto,  user);

        assertNotNull(result);
        assertEquals(dto.transactionType(), result.getTransactionType());
        assertEquals(dto.expenseCategory(), result.getExpenseCategory());
        assertEquals(user, result.getUser());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {
        CreateTransactionDto dto = new CreateTransactionDto(
                "Test",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                null,
                LocalDate.now(),
                BigDecimal.TEN
        );


        when(transactionRepository.save(any(Transaction.class)))
                .thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionService.createTransaction(dto,  user)
        );

        assertEquals("DB Error", exception.getMessage());

        verify(transactionRepository, times(1)).save(any(Transaction.class));

    }

    @Test
    void shouldNotCallRepositoryWhenDomainThrowsException(){
        CreateTransactionDto invalidDto = new CreateTransactionDto(
                "Test",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                null,
                LocalDate.now(),
                BigDecimal.TEN.negate()
        );

        assertThrows(BusinessException.class, () ->
                transactionService.createTransaction(invalidDto,  user));

        verify(transactionRepository, never()).save(any());

    }

}

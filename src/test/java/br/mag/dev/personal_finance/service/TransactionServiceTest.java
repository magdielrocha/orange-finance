package br.mag.dev.personal_finance.service;


import br.mag.dev.personal_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User user;


    @Test
    public void shouldCreateIncomeTransaction() {

        user =  new  User();
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

    @Test
    public void shouldCreateExpenseTransaction() {

        user = new User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Almoço",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                null,
                LocalDate.of(2025, 02, 02),
                new BigDecimal("50.00")

        );

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer( invocation -> invocation.getArgument(0));

        Transaction result = transactionService.createTransaction(dto, user);

        assertNotNull(result);
        assertEquals(TransactionType.EXPENSE, result.getTransactionType());
        assertEquals(ExpenseCategory.FOOD, result.getExpenseCategory());
        assertEquals(LocalDate.of(2025, 02, 02), result.getTransactionDate());
        assertNull(result.getIncomeSource());

        verify(transactionRepository).save(any(Transaction.class));

    }

    @Test
    public void shouldThrowExceptionWhenIncomeHasExpenseCategory() {

        user = new User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Erro",
                TransactionType.INCOME,
                ExpenseCategory.FOOD,
                IncomeSource.SALARY,
                LocalDate.of(2025, 02, 03),
                new BigDecimal("100.00")

        );

        assertThrows(BusinessException.class,
                () -> transactionService.createTransaction(dto, user));

        verify(transactionRepository, never()).save(any());

    }

    @Test
    public void shouldThrowExceptionWhenExpenseHasIncomeSource() {

        user = new User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Erro",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                IncomeSource.SALARY,
                LocalDate.of(2025, 02, 03),
                new BigDecimal("100.00")

        );

        assertThrows(BusinessException.class,
                () -> transactionService.createTransaction(dto, user));

        verify(transactionRepository, never()).save(any());

    }

    @Test
    public void shouldThrowExceptionWhenExpenseCategoryIsNull() {

        user = new User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Erro",
                TransactionType.EXPENSE,
                null,
                null,
                LocalDate.of(2025, 02, 03),
                new BigDecimal("100.00")
        );
        assertThrows(BusinessException.class,
                () -> transactionService.createTransaction(dto, user));

        verify(transactionRepository, never()).save(any());
    }


    @Test
    public void shouldThrowExceptionWhenIncomeSourceIsNull() {

        user = new User();
        user.setId(1L);

        CreateTransactionDto dto = new CreateTransactionDto(
                "Erro",
                TransactionType.INCOME,
                null,
                null,
                LocalDate.of(2025, 02, 03),
                new BigDecimal("100.00")
        );

        assertThrows(BusinessException.class,
                () -> transactionService.createTransaction(dto, user));

        verify(transactionRepository, never()).save(any());
    }


    @Test
    public void shouldThrowExceptionWhenTransactionTypeIsNull() {

        user = new User();
        user.setId(1L);


        CreateTransactionDto dto = new CreateTransactionDto(
                "erro",
                null,
                ExpenseCategory.FOOD,
                null,
                LocalDate.of(2025, 02, 03),
                new BigDecimal("100.00")
        );

        assertThrows(BusinessException.class,
                () -> transactionService.createTransaction(dto, user));

        verify(transactionRepository, never()).save(any());


    }



}

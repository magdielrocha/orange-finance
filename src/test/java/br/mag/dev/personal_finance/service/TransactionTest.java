package br.mag.dev.personal_finance.service;


import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.domain.model.Transaction;
import br.mag.dev.personal_finance.domain.model.User;
import br.mag.dev.personal_finance.exception.BusinessException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void shouldCreateValidIncomeTransaction() {

        User user = new User();

        Transaction transaction = new Transaction (
                "Salário",
                TransactionType.INCOME,
                null,
                IncomeSource.SALARY,
                LocalDate.of(2026, 2, 20),
                new BigDecimal("5000.00"),
                user
        );

        assertEquals(TransactionType.INCOME, transaction.getTransactionType());
        assertEquals(IncomeSource.SALARY, transaction.getIncomeSource());
        assertNull(transaction.getExpenseCategory());
        assertEquals(new BigDecimal("5000.00"), transaction.getAmount());

    }

    @Test
    void shouldCreateValidExpenseTransaction() {

        User user = new User();

        Transaction transaction = new Transaction (
                "Almoço",
                TransactionType.EXPENSE,
                ExpenseCategory.FOOD,
                null,
                LocalDate.of(2026, 2, 20),
                new BigDecimal("60.00"),
                user
        );

        assertEquals(TransactionType.EXPENSE, transaction.getTransactionType());
        assertEquals(ExpenseCategory.FOOD, transaction.getExpenseCategory());
        assertNull(transaction.getIncomeSource());
        assertEquals(new BigDecimal("60.00"), transaction.getAmount());

    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        "Test",
                        TransactionType.INCOME,
                        null,
                        IncomeSource.SALARY,
                        LocalDate.now(),
                        BigDecimal.ZERO,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        "Test",
                        TransactionType.INCOME,
                        null,
                        IncomeSource.SALARY,
                        LocalDate.now(),
                        BigDecimal.ONE.negate(),
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenTransactionTypeIsNull() {

        assertThrows(BusinessException.class, () ->
                new Transaction(

                        "Test",
                        null,
                        null,
                        IncomeSource.SALARY,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenIncomeHasExpenseCategory () {

        assertThrows(BusinessException.class, () ->
                new Transaction(

                        "Test",
                        TransactionType.INCOME,
                        ExpenseCategory.FOOD,
                        IncomeSource.SALARY,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenIncomeSourceIsNull () {

        assertThrows(BusinessException.class, () ->
                new Transaction(

                        "Test",
                        TransactionType.INCOME,
                        null,
                        null,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenExpenseHasIncomeSource () {

        assertThrows(BusinessException.class, () ->
                new Transaction(

                        "Test",
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        IncomeSource.SALARY,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenExpenseCategoryIsNull () {

        assertThrows(BusinessException.class, () ->
                new Transaction(

                        "Test",
                        TransactionType.EXPENSE,
                        null,
                        null,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }


    @Test
    void shouldThrowExceptionWhenDescriptionIsNull () {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        null,
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        null,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty () {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        " ",
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        null,
                        LocalDate.now(),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }


    @Test
    void shouldThrowExceptionWhenTransactionDateIsNull () {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                     "Test",
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        null,
                        null,
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenTransactionDateIsTooOld () {

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        "Test",
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        null,
                        LocalDate.of(2014, 1, 01),
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

    public static  LocalDate addDayAfter(LocalDate futureDate) {
        return futureDate.plusDays(1);
    }

    @Test
    void shouldThrowExceptionWhenTransactionIsinTheFuture () {

        LocalDate futureDate = addDayAfter(LocalDate.now());

        assertThrows(BusinessException.class, () ->
                new Transaction(
                        "Test",
                        TransactionType.EXPENSE,
                        ExpenseCategory.FOOD,
                        null,
                        futureDate,
                        BigDecimal.TEN,
                        new User()
                )
        );
    }

}

package br.mag.dev.personal_finance.domain;


import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.exception.BusinessException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    void shouldCreateValidIncomeTransaction() {

        assertDoesNotThrow(() ->
                TransactionBuilder.aTransaction()
                        .withType(TransactionType.INCOME)
                        .withIncomeSource(IncomeSource.SALARY)
                        .withExpenseCategory(null)
                        .build()
        );
    }

    @Test
    void shouldCreateValidExpenseTransaction() {

        assertDoesNotThrow(() ->
                TransactionBuilder.aTransaction()
                        .withType(TransactionType.EXPENSE)
                        .withExpenseCategory(ExpenseCategory.FOOD)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withAmount(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {

        assertThrows(BusinessException.class, () ->
               TransactionBuilder.aTransaction()
                       .withAmount(BigDecimal.TEN.negate())
                       .build()
        );
    }


    @Test
    void shouldThrowExceptionWhenTransactionTypeIsNull() {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withType(null)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenIncomeHasExpenseCategory () {

        assertThrows(BusinessException.class, () ->
              TransactionBuilder.aTransaction()
                      .withType(TransactionType.INCOME)
                      .withIncomeSource(IncomeSource.SALARY)
                      .withExpenseCategory(ExpenseCategory.FOOD)
                      .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenIncomeSourceIsNull () {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                .withType(TransactionType.INCOME)
                .withIncomeSource(null)
                .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenExpenseHasIncomeSource () {

        assertThrows(BusinessException.class, () ->
               TransactionBuilder.aTransaction()
                       .withType(TransactionType.EXPENSE)
                       .withExpenseCategory(ExpenseCategory.FOOD)
                       .withIncomeSource(IncomeSource.SALARY)
                       .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenExpenseCategoryIsNull () {

        assertThrows(BusinessException.class, () ->
              TransactionBuilder.aTransaction()
                      .withType(TransactionType.EXPENSE)
                      .withExpenseCategory(null)
                      .build()
        );
    }


    @Test
    void shouldThrowExceptionWhenDescriptionIsNull () {

        assertThrows(BusinessException.class, () ->
               TransactionBuilder.aTransaction()
                       .withDescription(null)
                       .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty () {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withDescription("")
                        .build()
        );
    }


    @Test
    void shouldThrowExceptionWhenTransactionDateIsNull () {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withDate(null)
                        .build()
        );
    }

    @Test
    void shouldCreateTransactionWhenDateIsToday() {

        assertDoesNotThrow(() ->
                TransactionBuilder.aTransaction()
                        .withDate(LocalDate.now())
                        .build()
        );
    }

    @Test
    void shouldCreateTransactionWhenDateIsExactlyAtLimit() {

        assertDoesNotThrow(() ->
                TransactionBuilder.aTransaction()
                        .withDate(LocalDate.now().minusYears(10))
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenDateIsOlderThanLimit() {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withDate(LocalDate.now().minusYears(10).minusDays(1))
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenTransactionIsInTheFuture () {

        assertThrows(BusinessException.class, () ->
                TransactionBuilder.aTransaction()
                        .withDate(LocalDate.now().plusDays(1))
                        .build()
        );
    }
}

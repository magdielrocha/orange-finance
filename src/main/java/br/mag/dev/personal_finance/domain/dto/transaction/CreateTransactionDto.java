package br.mag.dev.personal_finance.domain.dto.transaction;

import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionDto(@NotBlank
                                   String description,

                                   @NotNull
                                   TransactionType transactionType,

                                   ExpenseCategory expenseCategory,

                                   IncomeSource incomeSource,

                                   @NotNull
                                   LocalDate transactionDate,

                                   @DecimalMin("0.01")
                                   @NotNull
                                   BigDecimal amount) {
}

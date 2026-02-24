package br.mag.dev.personal_finance.domain.dto.transaction;

import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDto(Long id,
                                     String description,
                                     TransactionType transactionType,
                                     ExpenseCategory expenseCategory,
                                     IncomeSource incomeSource,
                                     BigDecimal amount,
                                     LocalDate transactionDate,
                                     Long userId) {
    public TransactionResponseDto( Transaction transaction) {
        this(
               transaction.getId(),
               transaction.getDescription(),
               transaction.getTransactionType(),
               transaction.getExpenseCategory(),
               transaction.getIncomeSource(),
               transaction.getAmount(),
               transaction.getTransactionDate(),
               transaction.getUser().getId()
        );
    }
}
package br.mag.dev.personal_finance.domain;

import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.domain.model.Transaction;
import br.mag.dev.personal_finance.domain.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionBuilder {

    private String description = "Test description";
    private TransactionType type = TransactionType.EXPENSE;
    private ExpenseCategory expenseCategory = ExpenseCategory.FOOD;
    private IncomeSource incomeSource = null;
    private LocalDate date = LocalDate.now();
    private BigDecimal amount = BigDecimal.TEN;
    private User user = new User();


    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder withDescription (String description) {
        this.description = description;
        return this;
    }

    public TransactionBuilder withType (TransactionType type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder withExpenseCategory (ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
        return this;
    }

    public TransactionBuilder withIncomeSource (IncomeSource incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public TransactionBuilder withDate (LocalDate date) {
        this.date = date;
        return this;
    }

    public TransactionBuilder withAmount (BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withUser (User user) {
        this.user = user;
        return this;
    }

    public Transaction build () {
        return new Transaction(
                description,
                type,
                expenseCategory,
                incomeSource,
                date,
                amount,
                user
        );
    }
}

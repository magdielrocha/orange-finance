package br.mag.dev.personal_finance.domain.model;

import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;
import br.mag.dev.personal_finance.domain.enums.IncomeSource;
import br.mag.dev.personal_finance.domain.enums.TransactionType;
import br.mag.dev.personal_finance.exception.BusinessException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Enumerated(EnumType.STRING)
    private IncomeSource incomeSource;

    @Column (nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column (name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;



    protected Transaction() {
    }



    public Transaction(

            String description,
            TransactionType transactionType,
            ExpenseCategory expenseCategory,
            IncomeSource incomeSource,
            LocalDate transactionDate,
            BigDecimal amount,
            User user
    ) {

        this.description = validateAndNormalizeDesciption(description);
        this.transactionType = transactionType;
        this.expenseCategory = expenseCategory;
        this.incomeSource = incomeSource;
        this.transactionDate = validateTransactionDate(transactionDate);
        this.amount = validateAmount(amount);
        this.user = user;

        validateTypeRules(transactionType, expenseCategory, incomeSource);

    }

    private String validateAndNormalizeDesciption(String desciption) {
        if (desciption == null) {
            throw new BusinessException("Description cannot be null");
        }

        String trimmedDescription =  desciption.trim();

        if (trimmedDescription.isEmpty()) {
            throw new BusinessException("Description cannot be empty");
        }

        return trimmedDescription;

    }

    private static final int MAX_YEARS_PAST = 10;

    private LocalDate validateTransactionDate(LocalDate transactionDate) {

        if (transactionDate == null) {
            throw new BusinessException("Transaction Date cannot be null");
        }
        LocalDate today = LocalDate.now();
        LocalDate limiteDate = today.minusYears(MAX_YEARS_PAST);

        if (transactionDate.isBefore(limiteDate)) {
            throw new BusinessException("Transaction date is too old");
        }

        if (transactionDate.isAfter(today)) {
            throw new BusinessException("Transaction date cannot be in the future");
        }

        return transactionDate;
    }


    private BigDecimal validateAmount(BigDecimal amount){

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }

        return amount;
    }

    private void validateTypeRules(
            TransactionType type,
            ExpenseCategory expenseCategory,
            IncomeSource incomeSource
    ) {

        if (type == null) {
            throw new BusinessException("Transaction type is required");
        }

        switch (type) {

            case EXPENSE -> {
                if (expenseCategory == null) {
                    throw new BusinessException("Expense category is required for EXPENSE transactions");
                }

                if (incomeSource != null) {
                    throw new BusinessException("Income source must be null for EXPENSE transactions");
                }
            }

            case INCOME -> {
                if (incomeSource == null) {
                    throw new BusinessException("Income source is required for INCOME transactions");
                }
                if (expenseCategory != null) {
                    throw new BusinessException("Expense category must be null for INCOME transactions");
                }
            }
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public IncomeSource getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(IncomeSource incomeSource) {
        this.incomeSource = incomeSource;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

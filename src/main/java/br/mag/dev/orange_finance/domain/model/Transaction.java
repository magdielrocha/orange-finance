package br.mag.dev.orange_finance.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    public Transaction() {
    }

    public Transaction(String description, TransactionType transactionType, ExpenseCategory expenseCategory,
                       IncomeSource incomeSource,
                       LocalDate transactionDate,
                       BigDecimal amount,
                       User user) {
        this.description = description;
        this.transactionType = transactionType;
        this.expenseCategory = expenseCategory;
        this.incomeSource = incomeSource;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.user = user;
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

package br.mag.dev.orange_finance.service;

import br.mag.dev.orange_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.exception.BusinessException;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction createTransaction(CreateTransactionDto dto, User user) {


        validateTransaction(dto);

        var transaction = new Transaction(
                dto.description(),
                dto.transactionType(),
                dto.expenseCategory(),
                dto.incomeSource(),
                dto.transactionDate(),
                dto.amount(),
                user
        );

        return transactionRepository.save(transaction);

    }

    private void validateTransaction(CreateTransactionDto dto) {

        if (dto.transactionType() == null) {
            throw new BusinessException("Transaction type is required");
        }

        switch (dto.transactionType()) {
            case EXPENSE -> validateExpense(dto);
            case INCOME -> validateIncome(dto);
            default -> throw new BusinessException("Unsupported transaction type");
        }
    }

    private void validateExpense(CreateTransactionDto dto) {

        if (dto.expenseCategory() == null) {
            throw new BusinessException("Expense category is required for EXPENSE transactions");
        }

        if (dto.incomeSource() != null) {
            throw new BusinessException("Income source must be null for EXPENSE transactions");
        }
    }

    private void validateIncome(CreateTransactionDto dto) {

        if (dto.incomeSource() == null) {
            throw new BusinessException("Income source is required for INCOME transactions");
        }

        if (dto.expenseCategory() != null) {
            throw new BusinessException("Expense category must be null for INCOME transactions");
        }
    }

}

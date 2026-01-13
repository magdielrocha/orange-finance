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

        if(dto.transactionType() == TransactionType.EXPENSE && dto.expenseCategory() == null) {
            throw  new BusinessException("Expense category is required for EXPENSE transactions");
        }

        if(dto.transactionType() == TransactionType.INCOME && dto.incomeSource() == null) {
            throw  new BusinessException("Income source is required for INCOME transactions");
        }

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

}

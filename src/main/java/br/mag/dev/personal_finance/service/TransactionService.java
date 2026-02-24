package br.mag.dev.personal_finance.service;

import br.mag.dev.personal_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.personal_finance.domain.model.Transaction;
import br.mag.dev.personal_finance.domain.model.User;
import br.mag.dev.personal_finance.repository.TransactionRepository;
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

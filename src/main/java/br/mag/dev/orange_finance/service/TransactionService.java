package br.mag.dev.orange_finance.service;

import br.mag.dev.orange_finance.domain.dto.CreateTransactionDto;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.exception.BusinessException;
import br.mag.dev.orange_finance.exception.ResourceNotFoundException;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import br.mag.dev.orange_finance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction createTransaction(CreateTransactionDto dto) {

        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

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

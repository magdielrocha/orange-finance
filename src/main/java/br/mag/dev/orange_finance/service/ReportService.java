package br.mag.dev.orange_finance.service;


import br.mag.dev.orange_finance.domain.dto.FinancialSummaryDto;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import br.mag.dev.orange_finance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public ReportService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public FinancialSummaryDto getFinancialSummary(User user) {

        List<Transaction> transactions =
                transactionRepository.findByUserId(user.getId());


        BigDecimal totalIncome =
                transactionRepository.sumByUserAndType(
                        user.getId(), TransactionType.INCOME);

        BigDecimal totalExpense =
                transactionRepository.sumByUserAndType(
                        user.getId(), TransactionType.EXPENSE);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new FinancialSummaryDto(
                totalIncome,
                totalExpense,
                balance
        );
    }
}

package br.mag.dev.orange_finance.service;


import br.mag.dev.orange_finance.domain.dto.report.CategorySummaryDto;
import br.mag.dev.orange_finance.domain.dto.report.ExpenseExcelRowDto;
import br.mag.dev.orange_finance.domain.dto.report.FinancialSummaryDto;
import br.mag.dev.orange_finance.domain.dto.report.MonthlyEvolutionDto;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.domain.model.User;
import br.mag.dev.orange_finance.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {


    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<CategorySummaryDto> getExpenseSummaryByCategory(User user) {
        return transactionRepository
                .getExpenseSummaryByCategory(user.getId());
    }


    @Transactional(readOnly = true)
    public FinancialSummaryDto getFinancialSummaryByPeriod(
            User user,
            int year,
            int month
    ) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions =
                transactionRepository.findByUserAndPeriod(
                        user.getId(),
                        startDate,
                        endDate);

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FinancialSummaryDto(
                totalIncome,
                totalExpense,
                totalIncome.subtract(totalExpense)
        );
    }

    @Transactional(readOnly = true)
    public List<MonthlyEvolutionDto> getMonthlyEvolution(
            User user,
            Integer startYear,
            Integer startMonth,
            Integer endYear,
            Integer endMonth
    ) {

        if (startYear == null || startMonth == null ||
                 endYear == null || endMonth == null) {

            return transactionRepository.findMonthlyEvolutionByUser(user.getId());
        }

        LocalDate start = LocalDate.of(startYear, startMonth, 1);
        LocalDate end = LocalDate.of(endYear, endMonth, 1)
                .withDayOfMonth(
                        LocalDate.of(endYear, endMonth, 1).lengthOfMonth()
                );

        return transactionRepository.findMonthlyEvolutionByUserAndPeriod(
                user.getId(), start, end);
    }

    @Transactional(readOnly = true)
    public List<ExpenseExcelRowDto> getExpenseReportForExcel(User user) {
        return transactionRepository.findExpenseSummaryForExcel(user.getId());
    }
}

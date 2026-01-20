package br.mag.dev.orange_finance.repository;

import br.mag.dev.orange_finance.domain.dto.report.CategorySummaryDto;
import br.mag.dev.orange_finance.domain.dto.report.ExpenseExcelRowDto;
import br.mag.dev.orange_finance.domain.dto.report.MonthlyEvolutionDto;
import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    @Query("""
   select coalesce(sum(t.amount), 0)
   from Transaction t
   where t.user.id = :userId
     and t.transactionType = :type
   """)
    BigDecimal sumByUserAndType(@Param("userId") Long userId,
                                @Param("type") TransactionType type);

    @Query("""
    select new br.mag.dev.orange_finance.domain.dto.report.CategorySummaryDto(
        t.expenseCategory,
        sum(t.amount)
    )
    from Transaction t
    where t.user.id = :userId
      and t.transactionType = br.mag.dev.orange_finance.domain.enums.TransactionType.EXPENSE
    group by t.expenseCategory
    """)
    List<CategorySummaryDto> getExpenseSummaryByCategory(@Param("userId") Long userId);


    @Query("""
    select t
    from Transaction t
    where t.user.id = :userId
      and t.transactionDate between :start and :end
    """)
    List<Transaction> findByUserAndPeriod(
            @Param("userId") Long userId,
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate
            );


    @Query("""
    select new br.mag.dev.orange_finance.domain.dto.report.MonthlyEvolutionDto(
        year(t.transactionDate),
        month(t.transactionDate),
        sum(case when t.transactionType = 'INCOME' then t.amount else 0 end),
        sum(case when t.transactionType = 'EXPENSE' then t.amount else 0 end)
    )
    from Transaction t
    where t.user.id = :userId
    group by year(t.transactionDate), month(t.transactionDate)
    order by year(t.transactionDate), month(t.transactionDate)
    """)
    List<MonthlyEvolutionDto> findMonthlyEvolutionByUser(
            @Param("userId") Long userId
    );


    @Query("""
    select new br.mag.dev.orange_finance.domain.dto.report.MonthlyEvolutionDto(
        year(t.transactionDate),
        month(t.transactionDate),
        sum(case when t.transactionType = 'INCOME' then t.amount else 0 end),
        sum(case when t.transactionType = 'EXPENSE' then t.amount else 0 end)
    )
    from Transaction t
    where t.user.id = :userId
      and t.transactionDate between :start and :end
    group by year(t.transactionDate), month(t.transactionDate)
    order by year(t.transactionDate), month(t.transactionDate)
    """)
    List<MonthlyEvolutionDto> findMonthlyEvolutionByUserAndPeriod(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
    select new br.mag.dev.orange_finance.domain.dto.report.ExpenseExcelRowDto(
        cast(t.expenseCategory as string),
        sum(t.amount)
    )
    from Transaction t
    where t.user.id = :userId
      and t.transactionType = 'EXPENSE'
    group by t.expenseCategory
    order by t.expenseCategory
    """)
    List<ExpenseExcelRowDto> findExpenseSummaryForExcel(
            @Param("userId") Long userId
    );

}

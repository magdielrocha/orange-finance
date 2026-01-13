package br.mag.dev.orange_finance.repository;

import br.mag.dev.orange_finance.domain.dto.report.CategorySummaryDto;
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

}

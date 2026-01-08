package br.mag.dev.orange_finance.repository;

import br.mag.dev.orange_finance.domain.enums.TransactionType;
import br.mag.dev.orange_finance.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

}

package br.mag.dev.personal_finance.domain.dto.report;

import java.math.BigDecimal;

public record FinancialSummaryDto(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {
}

package br.mag.dev.orange_finance.domain.dto.report;

import br.mag.dev.orange_finance.domain.enums.ExpenseCategory;

import java.math.BigDecimal;

public record CategorySummaryDto(
        ExpenseCategory category,
        BigDecimal total) {
}

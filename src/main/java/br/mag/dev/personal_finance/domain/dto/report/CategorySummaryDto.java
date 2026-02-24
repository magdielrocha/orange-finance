package br.mag.dev.personal_finance.domain.dto.report;

import br.mag.dev.personal_finance.domain.enums.ExpenseCategory;

import java.math.BigDecimal;

public record CategorySummaryDto(
        ExpenseCategory category,
        BigDecimal total) {
}

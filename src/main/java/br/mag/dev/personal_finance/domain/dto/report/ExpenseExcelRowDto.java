package br.mag.dev.personal_finance.domain.dto.report;

import java.math.BigDecimal;

public record ExpenseExcelRowDto(
        String category,
        BigDecimal total
) {
}

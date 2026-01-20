package br.mag.dev.orange_finance.domain.dto.report;

import java.math.BigDecimal;

public record ExpenseExcelRowDto(
        String category,
        BigDecimal total
) {
}

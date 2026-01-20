package br.mag.dev.orange_finance.domain.dto.report;

import java.math.BigDecimal;

public record IncomeExcelRowDto(
       String source,
       BigDecimal total
){}

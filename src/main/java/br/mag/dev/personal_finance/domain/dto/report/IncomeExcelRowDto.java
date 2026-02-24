package br.mag.dev.personal_finance.domain.dto.report;

import java.math.BigDecimal;

public record IncomeExcelRowDto(
       String source,
       BigDecimal total
){}

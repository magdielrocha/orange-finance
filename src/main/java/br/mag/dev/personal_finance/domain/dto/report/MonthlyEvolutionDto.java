package br.mag.dev.personal_finance.domain.dto.report;

import java.math.BigDecimal;

public record MonthlyEvolutionDto(
        Integer year,
        Integer month,
        BigDecimal totalIncome,
        BigDecimal totalExpense
) { }

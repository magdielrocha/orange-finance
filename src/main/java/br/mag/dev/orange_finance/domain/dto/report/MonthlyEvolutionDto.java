package br.mag.dev.orange_finance.domain.dto.report;

import java.math.BigDecimal;

public record MonthlyEvolutionDto(
        Integer year,
        Integer month,
        BigDecimal totalIncome,
        BigDecimal totalExpense
) { }

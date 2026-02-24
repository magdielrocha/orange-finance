package br.mag.dev.personal_finance.controller;


import br.mag.dev.personal_finance.domain.dto.report.CategorySummaryDto;
import br.mag.dev.personal_finance.domain.dto.report.FinancialSummaryDto;
import br.mag.dev.personal_finance.domain.dto.report.MonthlyEvolutionDto;
import br.mag.dev.personal_finance.infra.ExpenseExcelExporter;
import br.mag.dev.personal_finance.infra.IncomeExcelExporter;
import br.mag.dev.personal_finance.security.UserDetailsImpl;
import br.mag.dev.personal_finance.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ResponseEntity<FinancialSummaryDto> summary(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        FinancialSummaryDto summary = reportService.getFinancialSummary(userDetails.getUser());

        return ResponseEntity.ok(summary);

    }

    @GetMapping("/expenses-by-category")
    public ResponseEntity<List<CategorySummaryDto>> expensesByCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<CategorySummaryDto> result =
                reportService.getExpenseSummaryByCategory(userDetails.getUser());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/summary-by-period")
    public ResponseEntity<FinancialSummaryDto> summaryByPeriod(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        var summary = reportService.getFinancialSummaryByPeriod(
                userDetails.getUser(), year, month);

        return ResponseEntity.ok(summary);

    }

    @GetMapping("/monthly-evolution")
    public ResponseEntity<List<MonthlyEvolutionDto>> getMonthlyEvolution(
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer startMonth,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) Integer endMonth,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        var  result = reportService.getMonthlyEvolution(
                userDetails.getUser(),
                startYear,
                startMonth,
                endYear,
                endMonth
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/expenses/excel")
    public ResponseEntity<byte[]> exportExpensesExcel(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = reportService.getExpenseReportForExcel(userDetails.getUser());

        var exporter = new ExpenseExcelExporter();
        byte[] file = exporter.export(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }


    @GetMapping("/income/excel")
    public ResponseEntity<byte[]> exportIncomeExcel(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var  data = reportService.getIncomeReportForExcel(userDetails.getUser());

        var exporter = new IncomeExcelExporter();
        byte[] file = exporter.export(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=income.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);

    }






}

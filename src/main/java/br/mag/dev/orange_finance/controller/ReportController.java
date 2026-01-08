package br.mag.dev.orange_finance.controller;


import br.mag.dev.orange_finance.domain.dto.FinancialSummaryDto;
import br.mag.dev.orange_finance.security.UserDetailsImpl;
import br.mag.dev.orange_finance.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}

package br.mag.dev.personal_finance.controller;

import br.mag.dev.personal_finance.domain.dto.transaction.CreateTransactionDto;
import br.mag.dev.personal_finance.domain.dto.transaction.TransactionResponseDto;
import br.mag.dev.personal_finance.domain.model.Transaction;
import br.mag.dev.personal_finance.security.UserDetailsImpl;
import br.mag.dev.personal_finance.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('USER')")
   @PostMapping
   public ResponseEntity<TransactionResponseDto> create(
           @RequestBody @Valid CreateTransactionDto dto,
           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Transaction transaction = transactionService.createTransaction(
            dto, userDetails.getUser());

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new TransactionResponseDto(transaction));

   }
}

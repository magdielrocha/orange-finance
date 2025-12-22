package br.mag.dev.orange_finance.controller;

import br.mag.dev.orange_finance.domain.dto.CreateTransactionDto;
import br.mag.dev.orange_finance.domain.dto.TransactionResponseDto;
import br.mag.dev.orange_finance.domain.model.Transaction;
import br.mag.dev.orange_finance.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

   @PostMapping
   public ResponseEntity<TransactionResponseDto> create(@RequestBody @Valid CreateTransactionDto dto) {

    var transaction = transactionService.createTransaction(dto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new TransactionResponseDto(transaction));

   }
}

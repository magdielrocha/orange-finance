package br.mag.dev.orange_finance.controller;

import br.mag.dev.orange_finance.domain.dto.user.CreateUserDto;
import br.mag.dev.orange_finance.domain.dto.user.UserResponseDto;
import br.mag.dev.orange_finance.security.UserDetailsImpl;
import br.mag.dev.orange_finance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody @Valid CreateUserDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        var userCreated = userService.createUser(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponseDto(userCreated));
    }

}

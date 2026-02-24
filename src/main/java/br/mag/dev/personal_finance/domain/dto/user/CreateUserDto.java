package br.mag.dev.personal_finance.domain.dto.user;

import br.mag.dev.personal_finance.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(@NotBlank String fullName,
                            @NotBlank String email,
                            @NotBlank String password,
                            @NotNull UserRole userRole) {

}


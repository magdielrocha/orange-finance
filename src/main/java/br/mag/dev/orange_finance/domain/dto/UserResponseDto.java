package br.mag.dev.orange_finance.domain.dto;

import br.mag.dev.orange_finance.domain.enums.UserRole;
import br.mag.dev.orange_finance.domain.model.User;

public record UserResponseDto(Long id,
                              String fullName,
                              String email,
                              UserRole userRole) {
    public UserResponseDto(User user) {
        this(
            user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUserRole()
        );

    }
}

package br.mag.dev.personal_finance.domain.dto.user;

import br.mag.dev.personal_finance.domain.enums.UserRole;
import br.mag.dev.personal_finance.domain.model.User;

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

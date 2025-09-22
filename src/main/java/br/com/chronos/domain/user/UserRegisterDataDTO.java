package br.com.chronos.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDataDTO(

        @NotBlank @Email
        String email,

        @NotBlank @Size(min = 8, max = 255)
        String password,

        @NotBlank @Size(min = 8, max = 255)
        String confirmPassword


) {
}

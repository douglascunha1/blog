package org.douglas.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
        @NotEmpty(message = "Username is required") String username,
        @NotEmpty(message = "Email is required") @Email() String email,
        @NotEmpty(message = "Password is required") String password)
{}

package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    @NotBlank(message = "empty name")
    private String name;
    @NotNull
    @NotBlank(message = "empty email")
    @Email(message = "incorrect email")
    private String email;
}

package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemRequest {
    private Long id;
    @NotBlank(message = "empty name")
    private String name;
    @NotBlank(message = "empty description")
    private String description;
    @NotNull
    private Boolean available;
    private Long userId;
}

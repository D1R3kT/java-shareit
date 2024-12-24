package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    @Size(max = 50)
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    @Size(max = 512)
    @NotBlank(message = "Необходимо указать  описание")
    private String description;
    @NotNull(message = "null недопустимое значение для available")
    private Boolean available;
    private Long ownerId;
    private Long requestId;
}

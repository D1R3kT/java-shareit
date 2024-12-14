package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

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
    private UserDto owner;
    private ItemRequestDto itemRequestDto;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer rentCount;
}

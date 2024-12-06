package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.enums.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long id;
    @NotNull(message = "null недопустимое значение для start")
    private LocalDateTime start;
    @NotNull(message = "null недопустимое значение для end")
    private LocalDateTime end;
    @NotNull(message = "null недопустимое значение для itemId")
    @Positive(message = "Отрицательное значение недопустимо для itemId")
    private Long itemId;
    private Status status;
}

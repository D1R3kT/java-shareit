package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import ru.practicum.shareit.booking.enums.BookingState;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.user.dto.UserResponse;

import java.time.LocalDateTime;

@Builder
public record BookingResponse(
        Long id,
        LocalDateTime start,
        LocalDateTime end,
        ItemResponse item,
        UserResponse booker,
        Status status,
        BookingState state
) {
}

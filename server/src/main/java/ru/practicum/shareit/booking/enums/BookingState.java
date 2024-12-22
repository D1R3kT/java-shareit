package ru.practicum.shareit.booking.enums;

public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState from(String stringState) {
        return BookingState.valueOf(stringState.toUpperCase());
    }
}

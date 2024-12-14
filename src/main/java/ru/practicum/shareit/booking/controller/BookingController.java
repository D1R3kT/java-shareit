package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.enums.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_ID = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingResponse getForUser(@PathVariable Long id,
                                      @NotNull @Positive @RequestHeader(USER_ID) Long userId) {
        return bookingService.getForUser(id, userId);
    }

    @GetMapping
    public List<BookingResponse> getAllForBooker(@NotNull @Positive @RequestHeader(USER_ID) Long bookerId,
                                                 @RequestParam(
                                                         value = "state",
                                                         required = false,
                                                         defaultValue = "ALL") BookingState state) {
        return bookingService.getAllForBooker(bookerId, state);
    }

    @PostMapping
    public BookingResponse create(@RequestHeader(USER_ID) Long userId,
                                  @RequestBody BookingDto request) {
        System.out.println(LocalDateTime.now());
        System.out.println(request.getStart());
        return bookingService.create(userId, request);
    }

    @PatchMapping("/{id}")
    public BookingResponse patch(@PathVariable Long id,
                                 @NotNull @Positive @RequestHeader(USER_ID) Long userId,
                                 @RequestBody(required = false) BookingDto request,
                                 @RequestParam(value = "approved", required = false) Boolean isAccept) {
        if (isAccept != null) {
            return bookingService.acceptBooking(id, userId, isAccept);
        } else {
            return bookingService.patch(id, userId, request);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @NotNull @Positive @RequestHeader(USER_ID) Long userId) {
        bookingService.delete(id, userId);
    }

    @GetMapping("/owner")
    public List<BookingResponse> getAllForOwner(@NotNull @Positive @RequestHeader(USER_ID) Long userId,
                                                @RequestParam(
                                                        value = "state",
                                                        required = false,
                                                        defaultValue = "ALL") BookingState state) {
        return bookingService.getAllForOwner(userId, state);
    }
}

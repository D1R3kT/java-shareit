package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.enums.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingForUser(@PathVariable Long bookingId,
                                             @RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Get booking with id={} for user with id={}", bookingId, userId);
        return bookingService.getBookingForUser(bookingId, userId);
    }

    @GetMapping
    public List<BookingResponse> getBookings(
            @RequestHeader(X_SHARER_USER_ID) long userId,
            @RequestParam(name = "state", defaultValue = "all") String stateParam,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        BookingState state = BookingState.from(stateParam);
        log.info("Server received: Get booking with state={}, userId={}, from={}, size={}", stateParam, userId, from, size);
        return bookingService.getBookings(userId, state, from, size);
    }

    @PostMapping
    public BookingResponse createBooking(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @RequestBody BookingDto request) {
        log.info("Server received: Create booking={} by user with id={}", request, userId);
        return bookingService.createBooking(userId, request);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse patchBooking(@PathVariable Long bookingId,
                                        @RequestHeader(X_SHARER_USER_ID) Long userId,
                                        @RequestBody(required = false) BookingDto request,
                                        @RequestParam(value = "approved", required = false) Boolean isAccept) {
        if (isAccept != null) {
            log.info("Server received: Accept booking with id={} by user with id={}", bookingId, userId);
            return bookingService.acceptBooking(bookingId, userId, isAccept);
        } else {
            log.info("Server received: Patch booking={} by user with id={}", request, userId);
            return bookingService.patchBooking(bookingId, userId, request);
        }
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId,
                              @RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Delete booking with id={} by user with id={}", bookingId, userId);
        bookingService.deleteBooking(bookingId, userId);
    }

    @GetMapping("/owner")
    public List<BookingResponse> getBookingsForOwner(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                     @RequestParam(name = "state", defaultValue = "all") String stateParam) {
        log.info("Server received: Get booking with state={} for user with id={}", stateParam, userId);
        BookingState state = BookingState.from(stateParam);
        return bookingService.getBookingsForOwner(userId, state);
    }
}
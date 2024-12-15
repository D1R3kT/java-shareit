package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponse;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String USER_ID = "X-Sharer-User-Id";
    private final ItemRequestService itemRequestService;

    @GetMapping("/{id}")
    public ItemRequestResponse get(@PathVariable Long id) {
        return itemRequestService.get(id);
    }

    @GetMapping
    public List<ItemRequestResponse> getAll() {
        return itemRequestService.getAll();
    }

    @PostMapping
    public ItemRequestResponse create(@RequestHeader(USER_ID) Long userId, ItemRequestDto request) {
        return itemRequestService.create(userId, request);
    }

    @PatchMapping
    public ItemRequestResponse patch(@RequestHeader(USER_ID) Long userId, ItemRequestDto request) {
        return itemRequestService.patch(userId, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader(USER_ID) Long userId, @PathVariable Long id) {
        itemRequestService.delete(userId, id);
    }
}

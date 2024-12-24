package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponse;
import ru.practicum.shareit.request.dto.ItemRequestWithItems;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping("/{itemRequestId}")
    public ItemRequestWithItems getItemRequest(@PathVariable Long itemRequestId) {
        log.info("Server received: Get itemRequest with id={}", itemRequestId);
        return itemRequestService.getItemRequest(itemRequestId);
    }

    @GetMapping
    public List<ItemRequestWithItems> getItemRequestForRequestor(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Get itemRequests for requestor with id={}", userId);
        return itemRequestService.getItemRequestForRequestor(userId);
    }

    @PostMapping
    public ItemRequestResponse createItemRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                 @RequestBody ItemRequestDto request) {
        log.info("Server received: Create itemRequest={} by user with id={}", request, userId);
        return itemRequestService.createItemRequest(userId, request);
    }

    @PatchMapping("/{itemRequestId}")
    public ItemRequestResponse patchItemRequest(@PathVariable Long itemRequestId,
                                                @RequestHeader(X_SHARER_USER_ID) Long userId,
                                                @RequestBody ItemRequestDto request) {
        log.info("Server received: Patch itemRequest={} with id={} by user with id={}", request, itemRequestId, userId);
        return itemRequestService.patchItemRequest(itemRequestId, userId, request);
    }

    @DeleteMapping("/{itemRequestId}")
    public void deleteItemRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                  @PathVariable Long itemRequestId) {
        log.info("Server received: Delete itemRequest with id={} by user with id={}", itemRequestId, userId);
        itemRequestService.deleteItemRequest(userId, itemRequestId);
    }

    @GetMapping("/all")
    public List<ItemRequestResponse> getItemRequestForOther(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Get other user's itemRequests for user with id={}", userId);
        return itemRequestService.getItemRequestForOther(userId);
    }
}
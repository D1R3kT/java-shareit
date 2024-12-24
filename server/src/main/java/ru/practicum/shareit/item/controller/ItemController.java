package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId) {
        log.info("Server received: Get item with id={}", itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemResponse> getItemsForUser(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Get items for user with id={}", userId);
        return itemService.getItemsForUser(userId);
    }

    @PostMapping
    public ItemResponse createItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                   @RequestBody ItemDto request) {
        log.info("Server received: Create item={} by user with id={}", request, userId);
        return itemService.createItem(userId, request);
    }

    @PatchMapping("/{itemId}")
    public ItemResponse patchItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                  @PathVariable Long itemId,
                                  @RequestBody ItemDto request) {
        log.info("Server received: Patch item ={} with id={} by user with id={}", request, itemId, userId);
        return itemService.patchItem(itemId, userId, request);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId,
                           @RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Server received: Delete item with id={} by user with id={}", itemId, userId);
        itemService.deleteItem(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemResponse> searchItem(@RequestParam("text") String text) {
        if (text.isBlank())
            return new ArrayList<>();
        log.info("Server received: Get item by text={}", text);
        return itemService.getBySearch(text.toLowerCase());
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponse createComment(@PathVariable Long itemId,
                                         @RequestHeader(X_SHARER_USER_ID) Long bookerId,
                                         @RequestBody CommentDto request) {
        log.info("Server received: Create comment={} for item with id={} by user with id={}", request, itemId, bookerId);
        return itemService.createComment(itemId, bookerId, request);
    }
}
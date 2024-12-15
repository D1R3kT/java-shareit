package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static final String USER_ID = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ItemResponse get(@PathVariable Long id) {
        return itemService.get(id);
    }

    @PostMapping
    public ItemResponse create(@RequestBody @Valid ItemDto request, @RequestHeader(USER_ID) Long userId) {
        return itemService.create(request, userId);
    }

    @PatchMapping("/{id}")
    public ItemResponse patch(@NotNull @Positive @RequestHeader(USER_ID) Long userId,
                              @PathVariable Long id,
                              @RequestBody ItemDto request) {
        return itemService.patch(id, userId, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @NotNull @Positive @RequestHeader(USER_ID) Long userId) {
        itemService.delete(id, userId);
    }

    @GetMapping
    public List<ItemResponse> getAllForUser(@NotNull @Positive @RequestHeader(USER_ID) Long userId) {
        return itemService.getAllForUser(userId);
    }

    @GetMapping("/search")
    public List<ItemResponse> search(@RequestParam("text") String text) {
        if (text.isBlank())
            return new ArrayList<>();

        return itemService.getForSearch(text.toLowerCase());
    }

    @PostMapping("/{id}/comment")
    public CommentResponse createComment(@Positive @PathVariable Long id,
                                         @NotNull @Positive @RequestHeader(USER_ID) Long bookerId,
                                         @Valid @RequestBody CommentDto request) {
        return itemService.createComment(id, bookerId, request);
    }

}

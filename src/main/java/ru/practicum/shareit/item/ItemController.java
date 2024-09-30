package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.dto.RequestItemDto;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> getItem(@RequestParam final String text) {
        return itemService.getItem(text);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PostMapping
    public ItemDto create(@RequestBody @Valid ItemRequest request, @RequestHeader("X-Sharer-User-Id") long userId) {
        request.setUserId(userId);
        return itemService.createItem(request);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody RequestItemDto request, @RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId) {
        request.setId(itemId);
        return itemService.updateItem(request, userId);
    }
}

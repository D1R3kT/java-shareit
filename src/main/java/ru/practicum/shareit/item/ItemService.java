package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.dto.RequestItemDto;

import java.util.Collection;

public interface ItemService {

    Collection<ItemDto> getItems(Long userId);

    Collection<ItemDto> getItem(String name);

    ItemDto updateItem(RequestItemDto request, long userId);

    ItemDto getItem(Long id);

    ItemDto createItem(ItemRequest request);
}

package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private long id = 0;

    @Override
    public Collection<ItemDto> getItems(Long userId) {
        return itemRepository.getItems()
                .stream()
                .filter(item -> item.getUserId().equals(userId))
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItem(String name) {
        return itemRepository.getItems().stream()
                .filter(item -> item.getName().toLowerCase().equals(name.toLowerCase()))
                .filter(item -> item.getAvailable() == true)
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(Long id) {
        Item item = itemRepository.getItem(id);
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto createItem(ItemRequest request) {
        Long userId = request.getUserId();
        if (userId != null) {
            if (userRepository.getUser(userId) == null) {
                throw new NotFoundException("пользователя с Id = " + userId + " не найден");
            }
        }
        Item item = ItemMapper.mapToItem(request);
        item.setId(getNextId());
        itemRepository.saveItem(item);
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto updateItem(RequestItemDto request, long userId) {
        Item item = itemRepository.getItem(request.getId());
        Item updatedItem = ItemMapper.mapToItem(request);
        if (item.getUserId() != userId) {
            throw new NotFoundException("данная вещь относится к другому пользователю");
        }
        if (updatedItem.getName() != null) {
            item.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            item.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getAvailable() != null) {
            item.setAvailable(updatedItem.getAvailable());
        }
        if (updatedItem.getUserId() != null) {
            item.setUserId(updatedItem.getUserId());
        }
        itemRepository.updateItem(item);
        return ItemMapper.mapToItemDto(item);
    }

    public long getNextId() {
        id++;
        return id;
    }
}

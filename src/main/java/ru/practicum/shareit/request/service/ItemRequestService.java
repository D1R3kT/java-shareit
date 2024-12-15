package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestResponse;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestResponse get(Long id);

    List<ItemRequestResponse> getAll();

    ItemRequestResponse create(Long userId, ItemRequestDto itemRequestDto);

    ItemRequestResponse patch(Long id, ItemRequestDto itemRequestDto);

    void delete(Long userId, Long id);
}

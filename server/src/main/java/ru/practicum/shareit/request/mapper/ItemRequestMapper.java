package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponse;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {

    ItemRequestResponse toItemRequestResponse(ItemRequest itemRequest);

    List<ItemRequestResponse> toItemRequestResponseList(List<ItemRequest> itemRequests);

    ItemRequest toItemRequest(ItemRequestDto itemRequestDto);
}

package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enums.BookingState;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.LeaveCommentException;
import ru.practicum.shareit.exception.NoPermissionException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional(readOnly = true)
    public ItemResponse getItem(long itemId) {
        log.info("Server: Method getItem begin");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ID = " + itemId + " не найден"));
        List<Comment> comments = commentRepository.findCommentByItem_Id(item.getId());
        List<CommentResponse> commentResponses = commentMapper.toCommentResponseList(comments);
        return itemMapper.toItemResponseWithComments(item, commentResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsForUser(long userId) {
        log.info("Server: Method getItemsForUser begin");
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        return itemMapper.toItemResponseList(items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getBySearch(String text) {
        log.info("Server: Method getBySearch begin");
        List<Item> items = itemRepository.findAllByNameOrDescription(text);
        return itemMapper.toItemResponseList(items);
    }

    @Override
    public CommentResponse createComment(long itemId, long bookerId, CommentDto commentDTO) {
        log.info("Server: Method createComment begin");
        Booking booking = bookingRepository.findByItem_IdAndBooker_Id(itemId, bookerId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        if (bookingMapper.getState(booking).equals(BookingState.PAST)) {
            Item item = booking.getItem();
            User booker = booking.getBooker();
            Comment comment = commentMapper.toComment(commentDTO);
            comment.setItem(item);
            comment.setAuthor(booker);
            comment.setCreated(Timestamp.from(Instant.now()));
            comment = commentRepository.save(comment);
            return commentMapper.toCommentResponse(comment);
        } else {
            throw new LeaveCommentException("Вы не можете оставить комментарий");
        }
    }

    @Override
    public ItemResponse createItem(long userId, ItemDto request) {
        log.info("Server: Method createItem begin");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID = " + userId + " не найден"));
        Item item = itemMapper.toItem(request);
        item.setOwner(user);
        if (request.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(request.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Запрос с ID = " + userId + " не найден"));
            item.setItemRequest(itemRequest);
        }
        itemRepository.save(item);
        return itemMapper.toItemResponse(item);
    }

    @Override
    public ItemResponse patchItem(long itemId, long userId, ItemDto request) {
        log.info("Server: Method patchItem begin");
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID = " + userId + " не найден"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ID = " + itemId + " не найден"));

        if (!item.getOwner().getId().equals(userId)) {
            throw new NoPermissionException("Недостаточно прав для данного запроса");
        }

        if (request.getName() != null)
            item.setName(request.getName());

        if (request.getDescription() != null)
            item.setDescription(request.getDescription());

        if (request.getAvailable() != null)
            item.setAvailable(request.getAvailable());
        return itemMapper.toItemResponse(item);
    }

    @Override
    public void deleteItem(long itemId, long userId) {
        log.info("Server: Method deleteItem begin");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ID = " + itemId + " не найден"));

        if (!item.getOwner().getId().equals(userId)) {
            throw new NoPermissionException("Недостаточно прав для данного запроса");
        }

        itemRepository.deleteById(itemId);
    }

}
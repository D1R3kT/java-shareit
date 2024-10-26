package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserResponse;

public interface UserService {

    UserResponse get(Long id);

    UserResponse create(UserDto request);

    UserResponse patch(UserDto request, Long id);

    void delete(Long id);
}

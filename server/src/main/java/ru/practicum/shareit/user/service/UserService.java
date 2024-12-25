package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserResponse;

public interface UserService {

    UserResponse getUser(Long userId);

    UserResponse createUser(UserDto request);

    UserResponse patchUser(UserDto request, Long userId);

    void deleteUser(Long userId);
}

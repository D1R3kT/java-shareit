package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.RequestUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getUsers();

    UserDto getUser(Long id);

    UserDto createUser(UserRequest request);

    UserDto updateUser(RequestUserDto request, Long id);

    boolean deleteUser(Long id);
}

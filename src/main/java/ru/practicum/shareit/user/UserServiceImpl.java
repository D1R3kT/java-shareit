package ru.practicum.shareit.user;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.RequestUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private long id = 0;

    @Override
    public Collection<UserDto> getUsers() {
        return userRepository.getUsers()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        if (userRepository.getUser(id) == null) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        } else {
            User user = userRepository.getUser(id);
            return UserMapper.mapToUserDto(user);
        }
    }

    @Override
    public UserDto createUser(UserRequest request) {
        if (userRepository.getEmail(request.getEmail()) == false) {
            try {
                User user = UserMapper.mapToUser(request);
                user.setId(getNextId());
                userRepository.saveUser(user);
                return UserMapper.mapToUserDto(user);
            } catch (ValidationException e) {
                throw new ValidationException(e.getMessage());
            }
        } else {
            throw new ValidationException("Данный email уже используется");
        }
    }

    @Override
    public UserDto updateUser(RequestUserDto request, Long id) {
        if (userRepository.getEmail(request.getEmail()) == true) {
            throw new ValidationException("пользователь с email = " + request.getEmail() + " уже существует");
        }
        User user = userRepository.getUser(id);
        User updatedUser = UserMapper.mapToUser(request);
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        userRepository.updateUser(user);
        return UserMapper.mapToUserDto(userRepository.getUser(id));
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.getUser(id) != null) {
            userRepository.deleteUser(id);
            return true;
        } else {
            return false;
        }
    }

    public long getNextId() {
        id++;
        return id;
    }
}

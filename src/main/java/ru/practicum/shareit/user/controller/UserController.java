package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.service.UserService;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserDto request) {
        log.info("CREATE USER");
        return userService.create(request);

    }

    @PatchMapping("/{id}")
    public UserResponse patch(@RequestBody UserDto request, @PathVariable Long id) {
        log.info("UPDATE USER");
        return userService.patch(request, id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE USER");
        userService.delete(id);
    }

}

package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.RequestUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserRequest request) {
        log.info("CREATE USER");
        return userService.createUser(request);

    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@RequestBody @Valid RequestUserDto request, @PathVariable Long id) {
        log.info("UPDATE USER");
        return userService.updateUser(request, id);
    }


    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        log.info("DELETE USER");
        return userService.deleteUser(id);
    }

}

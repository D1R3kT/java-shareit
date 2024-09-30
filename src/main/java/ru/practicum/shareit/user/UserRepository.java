package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserRepository {

    Collection<User> getUsers();

    User getUser(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    boolean getEmail(String email);
}

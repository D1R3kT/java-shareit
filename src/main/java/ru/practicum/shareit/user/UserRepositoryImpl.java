package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final List<String> emails = new ArrayList<>();

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(Long id) {
        return users.get(id);
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
        emails.add(user.getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        emails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    @Override
    public User updateUser(User updatedUser) {
        return users.put(updatedUser.getId(), updatedUser);
    }

    @Override
    public boolean getEmail(String email) {
        if (emails.contains(email)) {
            return true;
        } else {
            return false;
        }
    }
}

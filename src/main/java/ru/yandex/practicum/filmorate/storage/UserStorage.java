package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    Map<Long, User> getUsers();

    User getById(Long userId);

    User add(User user);

    User update(User newUser);

    void delete(Long userId);


}

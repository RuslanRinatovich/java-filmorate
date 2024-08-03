package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;
import java.util.Set;

public interface UserStorage {

    Map<Long, User> getUsers();

    Map<Long, Set<Long>> getFriends();

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);


    User add(User user);

    User update(User newUser);

    void delete(Long userId);


}

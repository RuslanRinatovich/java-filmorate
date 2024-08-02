package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Getter
    private final UserStorage inMemoryUserStorage;
    private final Map<Long, Set<Long>> friends = new HashMap<>();
    private long currentMaxId = 0;

    public void add(Long userId, Long friendId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friendId)) {
            logger.error("пользователя с id = " + friendId + " нет");
            throw new NotFoundException("пользователя с id = " + friendId + " нет");
        }

        if (friends.containsKey(userId)) {
            friends.get(userId).add(friendId);
            logger.info("Добавлен новый друг");
        } else {
            friends.put(userId, new HashSet<>(Arrays.asList(friendId)));
        }
        if (friends.containsKey(friendId)) {
            friends.get(friendId).add(userId);
            logger.info("Добавлен новый друг");
        } else {
            friends.put(friendId, new HashSet<>(Arrays.asList(userId)));
        }
    }


    public void deleteFriend(Long userId, Long friendId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friendId)) {
            logger.error("пользователя с id = " + friendId + " нет");
            throw new NotFoundException("пользователя с id = " + friendId + " нет");
        }
        if (friends.containsKey(userId)) {
            if (friends.get(userId).contains(friendId)) {
                friends.get(userId).remove(friendId);
                if (friends.get(userId).isEmpty()) {
                    friends.remove(userId);
                }
            }
        }
        if (friends.containsKey(friendId)) {
            if (friends.get(friendId).contains(userId)) {
                friends.get(friendId).remove(userId);
                if (friends.get(friendId).isEmpty()) {
                    friends.remove(friendId);
                }
            }
        }

    }

    public Collection<User> getFriends(Long userId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        Set<User> returnUsers = new HashSet<>();
        if (!friends.containsKey(userId) || friends.get(userId).isEmpty())
        {
            return returnUsers;
        }
        if (!friends.get(userId).isEmpty()) {
            List<Long> friends1 = friends.get(userId).stream().sorted(Long::compareTo).toList();
            for (Long f : friends1) {
                returnUsers.add(inMemoryUserStorage.getUsers().get(f));
            }
        }
        return returnUsers;
    }

    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        Set<User> firstUserFriends = new HashSet<>(getFriends(userId));
        Set<User> secondUserFriends = new HashSet<>(getFriends(otherUserId));
        Set<User> commonFriends = new HashSet<>();
        if (firstUserFriends.isEmpty() || secondUserFriends.isEmpty())
            return commonFriends;
        commonFriends.addAll(firstUserFriends);
        commonFriends.retainAll(secondUserFriends);
        return commonFriends;
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        return ++currentMaxId;
    }
}

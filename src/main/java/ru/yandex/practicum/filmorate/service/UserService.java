package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Getter
    private final UserStorage inMemoryUserStorage;
    private final Map<Long, Friend> friends = new HashMap<>();
    private long currentMaxId = 0;

    public Friend add(Friend friend) {
        Optional<Friend> friend1 = friends.values().stream().filter(f -> (Objects.equals(f.getFirtsUserId(), friend.getFirtsUserId())) &&
                (Objects.equals(f.getSecondUserId(), friend.getSecondUserId())) || (Objects.equals(f.getFirtsUserId(), friend.getSecondUserId())) &&
                (Objects.equals(f.getSecondUserId(), friend.getFirtsUserId()))).findFirst();
        if (friend1.isPresent()) {
            logger.error("такая запись уже есть");
            throw new InternalServerErrorException("такая запись уже есть");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friend.getFirtsUserId())) {
            logger.error("пользователя с id = " + friend.getFirtsUserId() + " нет");
            throw new NotFoundException("пользователя с id = " + friend.getFirtsUserId() + " нет");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friend.getSecondUserId())) {
            logger.error("пользователя с id = " + friend.getSecondUserId() + " нет");
            throw new NotFoundException("пользователя с id = " + friend.getSecondUserId() + " нет");
        }

        friend.setId(getNextId());
        friends.put(friend.getId(), friend);
        logger.info("Добавлен новый друг");
        return friend;
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
        // проверяем необходимые условия
        Optional<Friend> friend = friends.values().stream().filter(f -> (Objects.equals(f.getFirtsUserId(), userId) && Objects.equals(f.getSecondUserId(), friendId) ||
                Objects.equals(f.getFirtsUserId(), friendId) && Objects.equals(f.getSecondUserId(), userId))).findFirst();

        friend.ifPresent(value -> friends.remove(value.getId()));

    }

    public Collection<User> getFriends(Long userId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        List<Friend> friends1 = friends.values().stream().filter(f -> Objects.equals(f.getFirtsUserId(), userId)).toList();
        List<Friend> friends2 = friends.values().stream().filter(f -> Objects.equals(f.getSecondUserId(), userId)).toList();
        Set<User> returnUsers = new HashSet<>();
        for (Friend f : friends1) {
            returnUsers.add(inMemoryUserStorage.getUsers().get(f.getSecondUserId()));
        }

        for (Friend f : friends2) {
            returnUsers.add(inMemoryUserStorage.getUsers().get(f.getFirtsUserId()));
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

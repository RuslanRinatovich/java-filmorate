package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.User;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserStorage.class);
    @Getter
    private final Map<Long, User> users = new HashMap<>();
    @Getter
    private final Map<Long, Set<Long>> friends = new HashMap<>();
    private long currentMaxId = 0;



    @Override
    public User add(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        // формируем дополнительные данные
        user.setId(getNextId());
        // сохраняем новую публикацию в памяти приложения
        users.put(user.getId(), user);
        logger.info("Добавлен новый пользователь");
        return user;
    }

    @Override
    public User update(User newUser) {
        // проверяем необходимые условия
        // не указан его идентификатор
        User oldUser = users.get(newUser.getId());
            if (newUser.getBirthday() != null) {
                oldUser.setBirthday(newUser.getBirthday());
            }
            if (newUser.getName() != null) {
                oldUser.setName(newUser.getName());
            }
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                oldUser.setName(newUser.getLogin());
            }
            if (newUser.getLogin() != null) {
                oldUser.setLogin(newUser.getLogin());
            }
            logger.info("Информация обновлена");
            return oldUser;
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
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

    @Override
    public void deleteFriend(Long userId, Long friendId) {
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

    private long getNextId() {
        return ++currentMaxId;
    }
}

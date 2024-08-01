package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Getter
    private final UserStorage inMemoryUserStorage;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final Map<Long, Friend> friends = new HashMap<>();
    private long currentMaxId = 0;
    public Friend add(Friend friend) {
        Optional<Friend> friend1 = friends.values().stream().filter(f -> (Objects.equals(f.getFirtsUserId(), friend.getFirtsUserId())) &&
                (Objects.equals(f.getSecondUserId(), friend.getSecondUserId())) || (Objects.equals(f.getFirtsUserId(), friend.getSecondUserId())) &&
                (Objects.equals(f.getSecondUserId(), friend.getFirtsUserId()))).findFirst();
        if (friend1.isPresent())
        {
            logger.error("такая запись уже есть");
            throw new InternalServerErrorException("такая запись уже есть");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friend.getFirtsUserId()))
        {
            logger.error("пользователя с id = " + friend.getFirtsUserId() + " нет");
            throw new ValidationException("пользователя с id = " + friend.getFirtsUserId() + " нет");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(friend.getSecondUserId()))
        {
            logger.error("пользователя с id = " + friend.getSecondUserId() + " нет");
            throw new ValidationException("пользователя с id = " + friend.getSecondUserId() + " нет");
        }

        friend.setId(getNextId());
        friends.put(friend.getId(), friend);
        logger.info("Добавлен новый друг");
        return friend;
    }


    public void delete(Long friendId) {
        // проверяем необходимые условия
        if (friendId == null) {
            throw new IncorrectParameterException("Id должен быть указан");
        }
        if (!friends.containsKey(friendId)) {
            logger.warn("Пользователь с id = " + friendId + " не найден");
            throw new ValidationException("Пользователь с id = " + friendId + " не найден");
        }

        friends.remove(friendId);
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        return ++currentMaxId;
    }
}

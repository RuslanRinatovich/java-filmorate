package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public UserService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    void validateUsersData(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            logger.error("электронная почта не может быть пустой");
            throw new ValidationException("электронная почта не может быть пустой");
        }
        if (!user.getEmail().contains("@")) {
            logger.error("электронная почта не корректна");
            throw new ValidationException("электронная почта не корректна");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            logger.error("логин не может быть пустым и содержать пробелы");
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDateTime.now().toLocalDate())) {
            logger.error("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

    // получить всех пользователей
    public Collection<User> getUsers() {
        return userStorage.getUsers().values();
    }

    public User addUser(User user) {
        validateUsersData(user);
        return userStorage.add(user);
    }

    // обновить фильм
    public User updateUser(User newUser) {
        if (userStorage.getUsers().containsKey(newUser.getId())) {
            User oldUser = userStorage.getUsers().get(newUser.getId());
            // указан новый адрес электронной почты и в приложении уже есть пользователь с таким адресом
            if (!Objects.equals(oldUser.getEmail(), newUser.getEmail())) {
                Optional<User> currentUser = userStorage.getUsers().values().stream().filter(u -> u.getEmail().equals(newUser.getEmail())).findFirst();
                if (currentUser.isPresent()) {
                    logger.error("Этот имейл уже используется");
                    try {
                        throw new InternalServerErrorException("Этот имейл уже используется");
                    } catch (InternalServerErrorException e) {
                        throw new RuntimeException(e);
                    }
                }
                oldUser.setEmail(newUser.getEmail());
            }
            validateUsersData(newUser);

            return userStorage.update(newUser);
        } else {
            logger.warn("Пользователь с id = " + newUser.getId() + " не найден");
            throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
        }
    }

    // удалить пользователя
    public void deleteUser(Long userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            logger.warn("Пользователь с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        userStorage.delete(userId);
    }

    public User getUser(Long userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            logger.warn("Пользователь с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        return userStorage.getUsers().get(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!userStorage.getUsers().containsKey(friendId)) {
            logger.error("пользователя с id = " + friendId + " нет");
            throw new NotFoundException("пользователя с id = " + friendId + " нет");
        }
        userStorage.addFriend(userId, friendId);
    }


    public void deleteFriend(Long userId, Long friendId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!userStorage.getUsers().containsKey(friendId)) {
            logger.error("пользователя с id = " + friendId + " нет");
            throw new NotFoundException("пользователя с id = " + friendId + " нет");
        }
        userStorage.deleteFriend(userId, friendId);

    }

    public Collection<User> getFriends(Long userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        Set<User> returnUsers = new HashSet<>();
        if (!userStorage.getFriends().containsKey(userId) || userStorage.getFriends().get(userId).isEmpty()) {
            return returnUsers;
        }
        if (!userStorage.getFriends().get(userId).isEmpty()) {
            List<Long> friends1 = userStorage.getFriends().get(userId).stream().sorted(Long::compareTo).toList();
            for (Long f : friends1) {
                returnUsers.add(userStorage.getUsers().get(f));
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

}

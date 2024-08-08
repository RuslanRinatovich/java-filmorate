package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;


    void validateUsersData(User user) {

        Optional<User> currentUser = userStorage.getUserByEmail(user.getEmail());
        if (currentUser.isPresent()) {
            logger.error("Этот имейл уже используется");
            throw new ValidationException("Этот имейл уже используется");
        }
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
    public Collection<UserDto> getUsers() {
        return userStorage.getUsers().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    public UserDto getUser(Long userId) {
        Optional<User> user = userStorage.getUserById(userId);
        if (user.isEmpty()) {
            logger.warn("Пользователь с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь с id = " + userId+ " не найден");
        }
        return UserMapper.mapToUserDto(user.get());
    }
    public UserDto addUser(User user) {
        validateUsersData(user);
        return UserMapper.mapToUserDto(userStorage.add(user));
    }

    //
    // обновить пользователя
    public User updateUser(User newUser) {
        Optional<User> oldUser = userStorage.getUserById(newUser.getId());
        if (oldUser.isEmpty()) {
            logger.warn("Пользователь с id = " + newUser.getId() + " не найден");
            throw new NotFoundException("Пользователь с id = " + newUser.getId()+ " не найден");
        }
        if (!Objects.equals(oldUser.get().getEmail(), newUser.getEmail()))
        {
            Optional<User> currentUser = userStorage.getUserByEmail(newUser.getEmail());
            if (currentUser.isPresent()) {
                logger.error("Этот имейл уже используется");
                try {
                    throw new InternalServerErrorException("Этот имейл уже используется");
                } catch (InternalServerErrorException e) {
                    throw new RuntimeException(e);
                }
            }

        }

       return userStorage.update(newUser);
    }


//    // удалить пользователя
    public Boolean deleteUser(Long userId) {
        Optional<User> user = userStorage.getUserById(userId);
        if (user.isEmpty()) {
            logger.warn("Пользователь с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь с id = " + userId+ " не найден");
        }

        return userStorage.delete(userId);
    }

        public void addFriend(Long userId, Long friendId) {
        Optional<User> user = userStorage.getUserById(userId);
        if (user.isEmpty()) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        Optional<User> friend = userStorage.getUserById(friendId);
        if (friend.isEmpty()) {
                logger.error("пользователя с id = " + friendId + " нет");
                throw new NotFoundException("пользователя с id = " + friendId + " нет");
            }
        Optional<Friendship> friendship = userStorage.findByFriendshipId(userId, friendId);
            if (friendship.isPresent()) {
                logger.error("Пользователь с id = " + userId + " уже добавил пользователя " + friendId + "в друзья");
                throw new NotFoundException("Пользователь с id = \" + userId + \" уже добавил пользователя \" + friendId + \"в друзья");
            }
        Optional<Friendship> friendship1 = userStorage.findByFriendshipId(friendId, userId);
            if (friendship1.isPresent()) {
                logger.error("Пользователь с id = " + friendId + " уже добавил пользователя " + userId + "в друзья");
                throw new NotFoundException("Пользователь с id = " + friendId + " уже добавил пользователя " + userId + "в друзья");
            }
        userStorage.addFriend(userId, friendId);
    }

        public Collection<User> getFriends(Long userId) {
            Optional<User> user = userStorage.getUserById(userId);
            if (user.isEmpty()) {
                logger.error("пользователя с id = " + userId + " нет");
                throw new NotFoundException("пользователя с id = " + userId + " нет");
            }

        return userStorage.getFriends(userId);
    }


        public void deleteFriend(Long userId, Long friendId) {
            Optional<User> user = userStorage.getUserById(userId);
            if (user.isEmpty()) {
                logger.error("пользователя с id = " + userId + " нет");
                throw new NotFoundException("пользователя с id = " + userId + " нет");
            }
            Optional<User> friend = userStorage.getUserById(friendId);
            if (friend.isEmpty()) {
                logger.error("пользователя с id = " + friendId + " нет");
                throw new NotFoundException("пользователя с id = " + friendId + " нет");
            }
        userStorage.deleteFriend(userId, friendId);

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

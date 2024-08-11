package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.RepositoryDbException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;


    void validateUsersData(UserDto user) {

        Optional<User> currentUser = userStorage.getUserByEmail(user.getEmail());
        if (currentUser.isPresent()) {
            logger.error("Этот имейл уже используется");
            throw new ValidationException("Этот имейл уже используется");
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
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        return UserMapper.mapToUserDto(user.get());
    }

    public UserDto addUser(UserDto user) {
        validateUsersData(user);
        return UserMapper.mapToUserDto(userStorage.add(UserMapper.mapToUser(user)));
    }

    //
    // обновить пользователя
    public UserDto updateUser(UserDto newUser) {
        Optional<User> oldUser = userStorage.getUserById(newUser.getId());
        if (oldUser.isEmpty()) {
            logger.warn("Пользователь с id = " + newUser.getId() + " не найден");
            throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
        }
        if (!Objects.equals(oldUser.get().getEmail(), newUser.getEmail())) {
            Optional<User> currentUser = userStorage.getUserByEmail(newUser.getEmail());
            if (currentUser.isPresent()) {
                logger.error("Этот имейл уже используется");
                try {
                    throw new RepositoryDbException("Этот имейл уже используется");
                } catch (RepositoryDbException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return UserMapper.mapToUserDto(userStorage.update(UserMapper.mapToUser(newUser)));
    }


    //    // удалить пользователя
    public Boolean deleteUser(Long userId) {
        Optional<User> user = userStorage.getUserById(userId);
        if (user.isEmpty()) {
            logger.warn("Пользователь с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
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

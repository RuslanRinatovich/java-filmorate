package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserDbStorage {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private final UserRepository userRepository;
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User add(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        logger.info("Добавлен новый пользователь" + user.getEmail() + user.getBirthday().toString());
        return userRepository.add(user);
    }

    public User update(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        logger.info("Данные пользователя обновлены");
        return userRepository.update(user);
    }

    public boolean delete(Long userId)
    {
        return userRepository.delete(userId);
    }

    public void addFriend(Long userId, Long friendId) {
       ;
    }
}

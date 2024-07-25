package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    //метод для проверки пользователя
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

    @PostMapping
    public User create(@RequestBody User user) {
        // проверяем выполнение необходимых условий
        validateUsersData(user);
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

    @PutMapping
    public User update(@RequestBody User newUser) {
        // проверяем необходимые условия
        // не указан его идентификатор
        if (newUser.getId() == null) {
            logger.error("Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            // указан новый адрес электронной почты и в приложении уже есть пользователь с таким адресом
            if (!Objects.equals(oldUser.getEmail(), newUser.getEmail())) {
                Optional<User> currentUser = users.values().stream().filter(u -> u.getEmail().equals(newUser.getEmail())).findFirst();
                if (currentUser.isPresent()) {
                    logger.error("Этот имейл уже используется");
                    throw new ValidationException("Этот имейл уже используется");
                }
                oldUser.setEmail(newUser.getEmail());
            }

            validateUsersData(newUser);
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
        logger.warn("Пользователь с id = " + newUser.getId() + " не найден");
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    // вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}


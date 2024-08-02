package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // вернуть всех пользователей пользователя
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAll() {
        return userService.getInMemoryUserStorage().getUsers().values();
    }

    // добавить пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User create(@RequestBody User user) {
        return userService.getInMemoryUserStorage().add(user);
    }

    // обновить пользователя
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User newUser) {
        return userService.getInMemoryUserStorage().update(newUser);
    }

    //добавление в друзья
    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable(name = "id", required = false) final Long userId, @PathVariable(name = "friendId", required = false) final Long friendId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (friendId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        userService.add(userId, friendId);
    }

    // удаление друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable(name = "id", required = false) final Long userId, @PathVariable(name = "friendId", required = false) final Long friendId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (friendId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        userService.deleteFriend(userId, friendId);
    }

    //  возвращаем список пользователей, являющихся его друзьями
    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> like(@PathVariable(name = "id", required = false) final Long userId) {
        // добавьте необходимые проверки
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        return userService.getFriends(userId);
    }

    // возвращаем список пользователей, являющихся его друзьями.
    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getCommonFriends(@PathVariable(name = "id", required = false) final Long userId,
                                             @PathVariable(name = "otherId", required = false) final Long otherUserId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (otherUserId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        return userService.getCommonFriends(userId, otherUserId);
    }

}


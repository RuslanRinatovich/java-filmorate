package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // вернуть всех пользователей пользователя
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable(name = "id") final Long userId) {
        if (userId == null) throw new IncorrectParameterException("Id должен быть указан");
        return userService.getUser(userId);
    }

    //    // добавить пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // обновить пользователя
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User newUser) {
        if (newUser.getId() == null) throw new IncorrectParameterException("Id должен быть указан");
        return userService.updateUser(newUser);
    }

    //
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteUser(@PathVariable(name = "id") final Long userId) {
        if (userId == null) throw new IncorrectParameterException("Id должен быть указан");
        return userService.deleteUser(userId);
    }


    //    //добавление в друзья
    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable(name = "id") final Long userId, @PathVariable(name = "friendId") final Long friendId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (friendId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        userService.addFriend(userId, friendId);
    }

    //
//    // удаление друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable(name = "id") final Long userId, @PathVariable(name = "friendId") final Long friendId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (friendId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        userService.deleteFriend(userId, friendId);
    }

    //
//    //  возвращаем список пользователей, являющихся его друзьями
    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> like(@PathVariable(name = "id") final Long userId) {
        // добавьте необходимые проверки
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        return userService.getFriends(userId);
    }

    //
//    // возвращаем список пользователей, являющихся его друзьями.
    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getCommonFriends(@PathVariable(name = "id") final Long userId,
                                             @PathVariable(name = "otherId") final Long otherUserId) {
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        if (otherUserId == null) throw new IncorrectParameterException("Необходимо установить параметр friendId");
        return userService.getCommonFriends(userId, otherUserId);
    }

}


package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDbService  {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private final UserRepository userRepository;
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

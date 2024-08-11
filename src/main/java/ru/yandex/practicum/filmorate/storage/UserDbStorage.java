package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.repository.FriendshipRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Qualifier
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private static final Logger logger = LoggerFactory.getLogger(UserDbStorage.class);
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Friendship> findByFriendshipId(long userId, long friendId) {
        return friendshipRepository.findByFriendshipId(userId, friendId);
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

    public boolean delete(Long userId) {
        return userRepository.delete(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        Friendship friendship = new Friendship();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        friendship.setStatus(true);
        friendshipRepository.addFriend(friendship);

    }

    public Collection<User> getFriends(Long userId) {
        return friendshipRepository.getFriends(userId);
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        return friendshipRepository.deleteFriend(userId, friendId);
    }
}

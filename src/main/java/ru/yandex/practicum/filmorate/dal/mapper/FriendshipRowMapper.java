package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class FriendshipRowMapper implements RowMapper<Friendship> {

    public Friendship mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setUserId(resultSet.getLong("user_id"));
        friendship.setFriendId(resultSet.getLong("friend_id"));
        friendship.setStatus(resultSet.getBoolean("status"));
        return friendship;
    }
}

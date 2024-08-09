package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getString("email"), resultSet.getString("login"));
        user.setId( resultSet.getLong("id"));
        user.setName( resultSet.getString("name"));
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        user.setBirthday(birthday);
        return user;
    }
}

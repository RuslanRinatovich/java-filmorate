package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
@Component
public class FilmRowMapper implements RowMapper<Film> {

    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId( resultSet.getLong("id"));
        film.setRatingId( resultSet.getLong("ratingId"));
        film.setName( resultSet.getString("name"));
        film.setDescription( resultSet.getString("description"));
        LocalDate releaseDate = resultSet.getDate("releaseDate").toLocalDate();
        film.setReleaseDate(releaseDate);
        film.setDuration(resultSet.getInt("duration"));
        return film;
    }
}
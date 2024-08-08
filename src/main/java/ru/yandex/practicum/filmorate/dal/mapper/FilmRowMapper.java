package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class FilmRowMapper implements RowMapper<Film> {


    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setRate(resultSet.getLong("rate"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        LocalDate releaseDate = resultSet.getDate("releaseDate").toLocalDate();
        film.setReleaseDate(releaseDate);
        film.setDuration(resultSet.getInt("duration"));
        film.setMpa(resultSet.getObject("mpa", MPA.class));
//        Map<String, Integer> x = resultSet.getObject("mpa", HashMap.class);
//        if (x.containsKey("id")) film.setMpa(x.get("id").longValue());
        return film;
    }
}
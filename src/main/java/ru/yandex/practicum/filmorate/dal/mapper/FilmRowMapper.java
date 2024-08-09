package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MPARepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    private final MPARepository mpaRepository;
    private final GenreRepository genreRepository;

    public FilmRowMapper(MPARepository mpaRepository, GenreRepository genreRepository) {
        this.mpaRepository = mpaRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("ID"));
        film.setRate(resultSet.getLong("RATE"));
        film.setName(resultSet.getString("NAME"));
        film.setDescription(resultSet.getString("DESCRIPTION"));
        LocalDate releaseDate = resultSet.getDate("RELEASE_DATE").toLocalDate();
        film.setReleaseDate(releaseDate);
        film.setDuration(resultSet.getInt("DURATION"));
        film.setMpaId(resultSet.getLong("MPA_ID"));
        Optional<MPA> mpa = mpaRepository.findById(resultSet.getInt("MPA_ID"));
        mpa.ifPresent(film::setMpa);
        film.setGenres(genreRepository.findGenresOfFilm(film.getId()));
        return film;
    }

}
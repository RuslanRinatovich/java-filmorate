package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dal.mapper.FilmRowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    private static final String FIND_ALL_FILMS = "SELECT * FROM FILM";
    private static final String FIND_BY_GENRE = "SELECT * FROM FILM WHERE GENRE_ID = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM FILM WHERE ID = ?";
    private static final String INSERT_QUERY = "INSERT INTO FILM(RATING_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE FILM SET RATING_ID = ?, NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? WHERE ID = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM FILM WHERE ID = ?";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    public List<Film> findAll() {
        return jdbc.query(FIND_ALL_FILMS, mapper);
        //return findMany(FIND_ALL_QUERY);
    }

    public Optional<Film> findByGenre(Long genreId) {
        return findOne(FIND_BY_GENRE, genreId);
    }

    public Optional<Film> findById(long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }
    public boolean delete(long filmId)
    {
        return delete(DELETE_BY_ID_QUERY, filmId);
    }
    @SneakyThrows
    public Film add(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getRatingId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
        );

        film.setId(id);
        return film;
    }
    @SneakyThrows
    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getRatingId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );
        return film;
    }


}
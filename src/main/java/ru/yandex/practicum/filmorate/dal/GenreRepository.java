package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
@Repository
public class GenreRepository extends BaseRepository<Genre> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM GENRE WHERE ID = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM GENRE";
    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }
    public Optional<Genre> findById(long genreId) {
        return findOne(FIND_BY_ID_QUERY, genreId);
    }

    public List<Genre> findAll() {
        return jdbc.query(FIND_ALL_QUERY, mapper);
        //return findMany(FIND_ALL_QUERY);
    }
}

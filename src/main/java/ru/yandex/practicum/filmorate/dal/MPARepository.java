package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Optional;
@Repository
public class MPARepository extends BaseRepository<MPA> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM MPA WHERE ID = ?";
    public MPARepository(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper, MPA.class);
    }
    public Optional<MPA> findById(long mpaId) {
        return findOne(FIND_BY_ID_QUERY, mpaId);
    }
}
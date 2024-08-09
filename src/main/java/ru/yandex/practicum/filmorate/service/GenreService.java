package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final FilmDbStorage filmStorage;
    public GenreService(FilmDbStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Genre> getGenres() {
        return filmStorage.getGenres();
    }

    public Genre getGenre(Long genreId) {
        Optional<Genre> genre = filmStorage.getGenreById(genreId);
        if (genre.isEmpty()) {
            logger.warn("Жанр с id = " + genreId + " не найден");
            throw new NotFoundException("Жанр с id = " + genreId + " не найден");
        }
        return genre.get();
    }
}

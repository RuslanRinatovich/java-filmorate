package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.Collection;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);
    private final FilmDbStorage filmStorage;

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

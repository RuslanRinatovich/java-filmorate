package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage {
    private static final Logger logger = LoggerFactory.getLogger(FilmDbStorage.class);
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final MPARepository mpaRepository;
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(long filmId) {
        return filmRepository.findById(filmId);
    }

    public Film add(Film film) {
        logger.info("Добавлен новый фильм");
        return filmRepository.add(film);
    }

    public Film update(Film film) {

        logger.info("Данные фильма обновлены");
        return filmRepository.update(film);
    }

    public boolean delete(Long filmId)
    {
        return filmRepository.delete(filmId);
    }
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(long genreId) {
        return genreRepository.findById(genreId);
    }

    public Optional<MPA> getMPAById(long mpaId) {
        return mpaRepository.findById(mpaId);
    }
}

package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.FriendshipRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage {
    private static final Logger logger = LoggerFactory.getLogger(FilmDbStorage.class);
    private final FilmRepository filmRepository;
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

}

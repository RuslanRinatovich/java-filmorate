package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
public class FilmService {
    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    public FilmService(UserDbStorage userStorage, FilmDbStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    //метод для проверки фильма
    void validateFilmsData(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            logger.error("название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            logger.error("максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        if (film.getDuration() <= 0) {
            logger.error("продолжительность фильма должна быть положительным числом");
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Calendar.DECEMBER, 28))) {
            logger.error("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }

    // получить все фильмы
    public Collection<FilmDto> getFilms() {
        return filmStorage.getFilms().stream().map(FilmMapper::mapToFilmDto).collect(Collectors.toList());
    }

    // добавить фильм
    public Film addFilm(Film film) {
        validateFilmsData(film);
        return filmStorage.add(film);
    }

    // обновить фильм
    public Film updateFilm(Film newFilm) {
        Optional<Film> oldFilm = filmStorage.getFilmById(newFilm.getId());
        if (oldFilm.isEmpty()) {
            logger.warn("Фильм с id = " + newFilm.getId() + " не найден");
            throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
        }
        validateFilmsData(newFilm);
        return filmStorage.update(newFilm);
    }

    // удалить фильм
    public Boolean deleteFilm(Long filmId) {
        Optional<Film> oldFilm = filmStorage.getFilmById(filmId);
        if (oldFilm.isEmpty()) {
            logger.warn("Фильм с id = " + filmId + " не найден");
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        return filmStorage.delete(filmId);
    }

    public Film getFilm(Long filmId) {
        Optional<Film> film = filmStorage.getFilmById(filmId);
        if (film.isEmpty()) {
            logger.warn("Фильм с id = " + filmId + " не найден");
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        return film.get();
    }

//
//    public void addLike(Long filmId, Long userId) {
//        if (!userStorage.getUsers().containsKey(userId)) {
//            logger.error("пользователя с id = " + userId + " нет");
//            throw new NotFoundException("пользователя с id = " + userId + " нет");
//        }
//        if (!filmStorage.getFilms().containsKey(filmId)) {
//            logger.error("Фильма с id = " + filmId + " нет");
//            throw new NotFoundException("Фильма с id = " + filmId + " нет");
//        }
//        filmStorage.addLike(filmId, userId);
//    }
//
//    public void deleteLike(Long filmId, Long userId) {
//        if (!userStorage.getUsers().containsKey(userId)) {
//            logger.error("пользователя с id = " + userId + " нет");
//            throw new NotFoundException("пользователя с id = " + userId + " нет");
//        }
//        if (!filmStorage.getFilms().containsKey(filmId)) {
//            logger.error("Фильма с id = " + filmId + " нет");
//            throw new NotFoundException("Фильма с id = " + filmId + " нет");
//        }
//        filmStorage.deleteLike(filmId, userId);
//    }
//
//    public Collection<Film> findMostPopular(Integer size, Integer from, String sort) {
//        return filmStorage.getFilms().values().stream().sorted((p0, p1) -> {
//            int comp = p0.getLikesCount().compareTo(p1.getLikesCount()); //прямой порядок сортировки
//            if (sort.equals("desc")) {
//                comp = -1 * comp; //обратный порядок сортировки
//            }
//            return comp;
//        }).skip(from).limit(size).collect(Collectors.toList());
//    }
}

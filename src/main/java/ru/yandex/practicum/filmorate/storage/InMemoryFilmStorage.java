package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    @Getter
    private final Map<Long, Film> films = new HashMap<>();
    private long currentMaxId = 0;

    //метод для проверки пользователя
    void validateFilmsData(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            logger.error("название не может быть пустым");
            throw new InternalServerErrorException("название не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            logger.error("максимальная длина описания — 200 символов");
            throw new InternalServerErrorException("максимальная длина описания — 200 символов");
        }
        if (film.getDuration() <= 0) {
            logger.error("продолжительность фильма должна быть положительным числом");
            throw new InternalServerErrorException("продолжительность фильма должна быть положительным числом");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Calendar.DECEMBER, 28))) {
            logger.error("дата релиза — не раньше 28 декабря 1895 года");
            throw new InternalServerErrorException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }

    @Override
    public Film add(Film film) {
        validateFilmsData(film);
        // формируем дополнительные данные
        film.setId(getNextId());
        film.setLikesCount(0L);
        // сохраняем новую публикацию в памяти приложения
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        // проверяем необходимые условия
        if (newFilm.getId() == null) {
            throw new IncorrectParameterException("Id должен быть указан");
        }
        if (!films.containsKey(newFilm.getId())) {
            logger.warn("Фильм с id = " + newFilm.getId() + " не найден");
            throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
        }
        Film oldFilm = films.get(newFilm.getId());
        validateFilmsData(newFilm);
        oldFilm.setName(newFilm.getName());
        oldFilm.setDuration(newFilm.getDuration());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDescription(newFilm.getDescription());
        if (newFilm.getLikesCount() == null) {
            newFilm.setLikesCount(0L);
        }
        oldFilm.setLikesCount(newFilm.getLikesCount());
        logger.info("Информация обновлена");
        return oldFilm;
    }

    @Override
    public void delete(Long filmId) {
        // проверяем необходимые условия
        if (filmId == null) {
            throw new IncorrectParameterException("Id должен быть указан");
        }
        if (!films.containsKey(filmId)) {
            logger.warn("Фильм с id = " + filmId + " не найден");
            throw new ValidationException("Фильм с id = " + filmId + " не найден");
        }

        films.remove(filmId);
    }

    @Override
    public Film getById(Long filmId) {
        if (filmId == null) {
            throw new IncorrectParameterException("Id должен быть указан");
        }
        if (!films.containsKey(filmId)) {
            logger.warn("Фильм с id = " + filmId + " не найден");
            throw new ValidationException("Фильм с id = " + filmId + " не найден");
        }
        return films.get(filmId);
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        return ++currentMaxId;
    }
}

package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    private long currentMaxId = 0;
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }


    @PostMapping
    public Film create(@RequestBody Film film) {

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        // проверяем необходимые условия
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
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
        logger.info("Информация обновлена");
        return oldFilm;
    }


}

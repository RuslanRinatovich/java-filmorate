package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAll() {
        return filmService.getInMemoryFilmStorage().getFilms().values();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody Film film) {
        return filmService.getInMemoryFilmStorage().add(film);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film update(@RequestBody Film newFilm) {
        return filmService.getInMemoryFilmStorage().update(newFilm);
    }

    // добавить лайк
    @PutMapping("/{id}/like/{userId}")
    public Like like(@PathVariable(name = "id", required = false) final Long filmId, @PathVariable(required = false) final Long userId) {
        // добавьте необходимые проверки
        if (filmId == null) throw new IncorrectParameterException("Необходимо установить параметр filmId");
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        return filmService.add(new Like(filmId, userId));
    }

    // удалить лайк
    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "id", required = false) final Long filmId, @PathVariable(required = false) final Long userId) {
        // добавьте необходимые проверки
        if (filmId == null) throw new IncorrectParameterException("Необходимо установить параметр filmId");
        if (userId == null) throw new IncorrectParameterException("Необходимо установить параметр userId");
        filmService.delete(new Like(filmId, userId));
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAll(@RequestParam(name = "count", defaultValue = "10", required = false) Integer count) {
        String sort = "desc"; //asc
        return filmService.findMostPopular(count, 0, sort);
    }


}

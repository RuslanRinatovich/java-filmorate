package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;
import java.util.Set;

public interface FilmStorage {

    Map<Long, Film> getFilms();

    Map<Long, Set<Long>> getLikes();

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Film add(Film film);

    Film update(Film newFilm);

    void delete(Long filmId);


}

package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    @Getter
    private final UserStorage inMemoryUserStorage;
    @Getter
    private final FilmStorage inMemoryFilmStorage;
    private final Map<Long, Set<Long>> likes = new HashMap<>();


    public void add(Long filmId, Long userId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            logger.error("Фильма с id = " + filmId + " нет");
            throw new NotFoundException("Фильма с id = " + filmId + " нет");
        }

        if (likes.containsKey(filmId)) {
            likes.get(filmId).add(userId);
            logger.info("Добавлен новый лайк");
        } else {
            likes.put(filmId, new HashSet<>(Arrays.asList(userId)));
        }
        Film film = inMemoryFilmStorage.getById(filmId);
        film.setLikesCount(film.getLikesCount() + 1);

    }

    public void delete(Long filmId, Long userId) {
        if (!inMemoryUserStorage.getUsers().containsKey(userId)) {
            logger.error("пользователя с id = " + userId + " нет");
            throw new NotFoundException("пользователя с id = " + userId + " нет");
        }
        if (!inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            logger.error("Фильма с id = " + filmId + " нет");
            throw new NotFoundException("Фильма с id = " + filmId + " нет");
        }

        if (likes.containsKey(filmId)) {
            if (likes.get(filmId).contains(userId)) {
                likes.get(filmId).remove(userId);
                if (likes.get(filmId).isEmpty()) {
                    likes.remove(userId);
                }
                Film film = inMemoryFilmStorage.getById(filmId);
                film.setLikesCount(film.getLikesCount() - 1);
            }
        }
    }

    public Collection<Film> findMostPopular(Integer size, Integer from, String sort) {
        return inMemoryFilmStorage.getFilms().values().stream().sorted((p0, p1) -> {
            int comp = p0.getLikesCount().compareTo(p1.getLikesCount()); //прямой порядок сортировки
            if (sort.equals("desc")) {
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }
}

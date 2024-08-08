package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    @Getter
    private final Map<Long, Film> films = new HashMap<>();
    @Getter
    private final Map<Long, Set<Long>> likes = new HashMap<>();
    private long currentMaxId = 0;

    @Override
    public Film add(Film film) {
        film.setId(getNextId());
       // film.setLikesCount(0L);
        films.put(film.getId(), film);
        logger.info("Фильм добавлен");
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        oldFilm.setDuration(newFilm.getDuration());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDescription(newFilm.getDescription());
//        if (newFilm.getLikesCount() == null) {
//            newFilm.setLikesCount(0L);
//        }
//        oldFilm.setLikesCount(newFilm.getLikesCount());
        logger.info("Информация обновлена");
        return oldFilm;
    }

    @Override
    public void delete(Long filmId) {
        likes.remove(filmId);
        films.remove(filmId);
    }


    @Override
    public void addLike(Long filmId, Long userId) {
        if (likes.containsKey(filmId)) {
            likes.get(filmId).add(userId);
            logger.info("Добавлен новый лайк");
        } else {
            likes.put(filmId, new HashSet<>(Arrays.asList(userId)));
        }
        Film film = films.get(filmId);
        //film.setLikesCount(film.getLikesCount() + 1);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        if (likes.containsKey(filmId)) {
            if (likes.get(filmId).contains(userId)) {
                likes.get(filmId).remove(userId);
                if (likes.get(filmId).isEmpty()) {
                    likes.remove(userId);
                }
                Film film = films.get(filmId);
              //  film.setLikesCount(film.getLikesCount() - 1);
            }
        }
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        return ++currentMaxId;
    }
}

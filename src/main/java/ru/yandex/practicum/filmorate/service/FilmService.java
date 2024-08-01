package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Getter
    private final UserStorage inMemoryUserStorage;
    @Getter
    private final FilmStorage inMemoryFilmStorage;
    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);
    private final Map<Long, Like> likes = new HashMap<>();
    private long currentMaxId = 0;
    public Like add(Like like)
    {

        Optional<Like> like1 = likes.values().stream().filter(f -> (Objects.equals(f.getUserId(), like.getUserId())) &&
                (Objects.equals(f.getFilmId(), like.getFilmId()))).findFirst();
        if (like1.isPresent())
        {
            logger.error("такая запись уже есть");
            throw new InternalServerErrorException("такая запись уже есть");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(like.getUserId()))
        {
            logger.error("пользователя с id = " + like.getUserId() + " нет");
            throw new ValidationException("пользователя с id = " + like.getUserId() + " нет");
        }
        if (!inMemoryFilmStorage.getFilms().containsKey(like.getFilmId()))
        {
            logger.error("Фильма с id = " + like.getFilmId() + " нет");
            throw new ValidationException("Фильма с id = " + like.getFilmId() + " нет");
        }
        like.setId(getNextId());
        // сохраняем новую публикацию в памяти приложения
        likes.put(like.getId(), like);

        return like;
    }

    private long getNextId() {
        return ++currentMaxId;
    }

    public void delete(Like like)
    {
        Optional<Like> like1 = likes.values().stream().filter(f -> (Objects.equals(f.getUserId(), like.getUserId())) &&
                (Objects.equals(f.getFilmId(), like.getFilmId()))).findFirst();
        if (like1.isEmpty())
        {
            logger.error("лайка нет");
            throw new ValidationException("лайка нет");
        }
        likes.remove(like1.get().getId());
    }

    public Collection<Film> findMostPopular(Integer size, Integer from, String sort) {
        return inMemoryFilmStorage.getFilms().values().stream().sorted((p0, p1) -> {
            int comp = p0.getLikesCount().compareTo(p1.getLikesCount()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }
}

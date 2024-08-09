package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureTestDatabase
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(FilmorateApplicationTests.class);
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    // получение пользователя с id = 1
    @Test
    public void testFindUserById() {
        Optional<User> userOptional = userStorage.getUserById(1);
        userOptional.ifPresent(user -> logger.info(user.getName()));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    // получение всех пользователей
    @Test
    public void testFindAllUsers() {
        List<User> users = userStorage.getUsers();
        assertThat(users.size()).isEqualTo(20);
    }

    // получение всех пользователя по емайлу
    @Test
    public void testFindUserByEmail() {
        Optional<User> userOptional = userStorage.getUserByEmail("cursus.vestibulum.mauris@icloud.org");
        userOptional.ifPresent(user -> logger.info(user.getName()));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "Mechelle")
                );
    }

    // добавить друзей пользователю под номером 3
    @Test
    public void addFriendsToUserWithId3() {
        userStorage.addFriend(3L, 12L);
        int friendCount = userStorage.getFriends(3L).size();
        assertThat(friendCount).isEqualTo(1);
    }

    // получение фильма с id = 1
    @Test
    public void testFindFilmById() {
        Optional<Film> filmOptional = filmStorage.getFilmById(1);
        filmOptional.ifPresent(film -> logger.info(film.getName()));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "По щучьему велению (2023)")
                );
    }

    // получение всех фильмов
    @Test
    public void testFindAllFilms() {
        List<Film> films = filmStorage.getFilms();
        assertThat(films.size()).isEqualTo(3);
    }

    // получение лайков фильма с id = 1
    @Test
    public void testGetFilmsLikesCount() {
        Film fIlm = new Film();
        fIlm.setId(1L);
        int likesCount = filmStorage.getLikesCount(fIlm);
        assertThat(likesCount).isEqualTo(3);
    }

    // добавление лайков фильма с id = 3
    @Test
    public void addLikeToFilmWithId3() {
        filmStorage.addLike(3L, 12L);
        Film fIlm = new Film();
        fIlm.setId(3L);
        int likesCount = filmStorage.getLikesCount(fIlm);
        assertThat(likesCount).isEqualTo(2);
    }

    // получение жанра с id = 1
    @Test
    public void testFindGenreById() {
        Optional<Genre> genreOptional = filmStorage.getGenreById(1);
        genreOptional.ifPresent(genre -> logger.info(genre.getName()));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия")
                );
    }

    // получение всех жанров
    @Test
    public void testFindAllGenres() {
        List<Genre> genres = filmStorage.getGenres();
        assertThat(genres.size()).isEqualTo(6);
    }

    // получение MPA с id = 1
    @Test
    public void testFindMPAById() {
        Optional<MPA> mpaOptional = filmStorage.getMPAById(1);
        mpaOptional.ifPresent(mpa -> logger.info(mpa.getName()));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G")
                );
    }

    // получение всех жанров
    @Test
    public void testFindAllMPAs() {
        List<MPA> mpas = filmStorage.getMPAs();
        assertThat(mpas.size()).isEqualTo(5);
    }

}



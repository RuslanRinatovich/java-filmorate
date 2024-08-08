package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
@Data
public class FilmGenre {
    @NonNull
    private Long filmId;
    @NonNull
    private Long genreId;

}

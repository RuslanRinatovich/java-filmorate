package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
@Builder
@Data
public class FilmGenre {
    @NonNull
    private Long filmId;
    @NonNull
    private Long genreId;

}

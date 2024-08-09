package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FilmGenre {
    private Long filmId;
    private Long genreId;

}

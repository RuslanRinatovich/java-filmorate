package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Builder
@Data
public class FilmLike {
    @NonNull
    private Long filmId;
    @NonNull
    private Long userId;
    private LocalDate createDate;

}

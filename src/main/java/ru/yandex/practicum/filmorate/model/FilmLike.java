package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class FilmLike {
    private Long filmId;
    private Long userId;
    private Instant createDate;

}

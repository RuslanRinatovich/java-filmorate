package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

/**
 * Film.
 */
@Getter
@Setter
public class Film {
    Long id;
    String name;
    String description;
    Date releaseDate;
    int duration;
}

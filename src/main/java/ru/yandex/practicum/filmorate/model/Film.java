package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;


import java.time.LocalDate;
import java.util.List;


@Data
public class Film {
    @NonNull
    private Long id;
    @NonNull
    private Long ratingId;
    @NonNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Long getLikesCount(){
        return 8L;
    }
}

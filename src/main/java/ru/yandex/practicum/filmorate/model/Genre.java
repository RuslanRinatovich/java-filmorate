package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Genre {
    @NonNull
    private Long id;
    @NonNull private String name;

}

package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Data
public class Genre {
    private Long id;
    private String name;

}

package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class MPA {
    private Long id;
    private String name;
    private String description;

}

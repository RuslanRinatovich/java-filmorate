package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Builder
@Data
public class Rating {
    @NonNull
    private Long id;
    @NonNull private String name;
    @NonNull private String description;

}
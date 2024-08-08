package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class MPA {
    @NonNull
    private Long id;
    private String name;
    private String description;

}

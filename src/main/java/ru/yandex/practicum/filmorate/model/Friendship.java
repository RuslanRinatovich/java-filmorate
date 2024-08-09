package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
public class Friendship {
    private Long userId;
    private Long friendId;

    private Boolean status;


}

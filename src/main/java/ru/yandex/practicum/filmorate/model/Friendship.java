package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Friendship {
    @NonNull
    private Long userId;
    @NonNull
    private Long friendId;

    private Boolean status;


}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Like {
    private Long id;
    private Long filmId;
    private Long userId;

    public Like(Long filmId, Long userId)
    {
        this.filmId = filmId;
        this.userId = userId;
    }
}

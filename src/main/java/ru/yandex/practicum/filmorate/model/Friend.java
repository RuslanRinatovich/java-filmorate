package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friend {
    private Long id;
    private Long firtsUserId;
    private Long secondUserId;
}

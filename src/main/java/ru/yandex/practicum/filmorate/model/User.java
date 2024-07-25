package ru.yandex.practicum.filmorate.model;

import java.time.Instant;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"email"})
public class User {

    Long id;

    String email;

    String login;

    String name;

    Date birthday;
}


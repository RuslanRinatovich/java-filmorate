package ru.yandex.practicum.filmorate.model;


import java.time.LocalDate;


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

    LocalDate birthday;
}


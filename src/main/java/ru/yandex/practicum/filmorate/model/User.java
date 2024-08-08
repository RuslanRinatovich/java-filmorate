package ru.yandex.practicum.filmorate.model;


import java.time.LocalDate;
import java.util.List;


import lombok.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"email"})
public class User {
    @NonNull private Long id;
    @NonNull private String email;
    @NonNull private String login;
    private String name;
    private LocalDate birthday;
}


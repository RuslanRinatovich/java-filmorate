package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;
import java.util.List;

@Data
@Validated
public class FilmDto {
    private Long id;
    private Long rate;
    @NotNull(message = "название не может быть пустым")
    @NotBlank(message = "название не может быть пустым")
    private String name;
    @Size(min = 0, max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Min(value = 0, message = "продолжительность фильма должна быть положительным числом")
    private int duration;
    private Long mpaId;
    private MPA mpa;
    private List<Genre> genres;
}


package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
public class UserDto {
    private Long id;
    @NotBlank(message = "логин не может быть пустым и содержать пробелы" )
    @NotNull(message = "надо указать логин" )
    private String login;
    @Email(message = "электронная почта не корректна")
    @NotBlank(message = "электронная почта не может быть пустой")
    @NotNull(message = "электронная почта не может быть пустой" )
    private String email;
    private String name;
    @Past(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
}

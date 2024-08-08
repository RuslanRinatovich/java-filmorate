package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


import java.time.LocalDate;
import java.util.List;



@Data
public class Film {
    private Long id;
    private Long rate;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private MPA mpa;
    //private List<Genre> genres;
    public Long getLikesCount(){
        return 8L;
    }
}

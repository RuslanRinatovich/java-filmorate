package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class MPAController {

    private static final Logger logger = LoggerFactory.getLogger(MPAController.class);
    private final MPAService mpaService;

    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<MPA> getMPAs() {
        return mpaService.getMPAs();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MPA getMPA(@PathVariable(name = "id") final Long mpaId) {
        if (mpaId == null) throw new IncorrectParameterException("Id должен быть указан");
        return mpaService.getMPA(mpaId);
    }
}

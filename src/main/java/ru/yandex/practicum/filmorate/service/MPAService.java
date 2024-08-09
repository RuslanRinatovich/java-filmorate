package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.Collection;
import java.util.Optional;

@Getter
@Service
public class MPAService {
    private static final Logger logger = LoggerFactory.getLogger(MPAService.class);
    private final FilmDbStorage filmStorage;
    public MPAService(FilmDbStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<MPA> getMPAs() {
        return filmStorage.getMPAs();
    }

    public MPA getMPA(Long mpaId) {
        Optional<MPA> mpa = filmStorage.getMPAById(mpaId);
        if (mpa.isEmpty()) {
            logger.warn("MPA с id = " + mpaId + " не найден");
            throw new NotFoundException("MPA с id = " + mpaId + " не найден");
        }
        return mpa.get();
    }
}

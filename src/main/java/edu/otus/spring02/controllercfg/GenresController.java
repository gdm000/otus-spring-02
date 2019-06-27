package edu.otus.spring02.controllercfg;

import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RestController
public class GenresController {
    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService ops) {
        this.genresService = ops;
    }

    @GetMapping("genres")
    public Flux<Genre> getGenreList() {
        return genresService.getGenres(Function.identity());
    }
}

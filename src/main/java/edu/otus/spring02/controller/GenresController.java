package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
public class GenresController {
    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService ops) {
        this.genresService = ops;
    }

    //@GetMapping("genres")
    public String listPage(Model model) {
        List<Genre> genres = genresService.getGenres(Function.identity());
        model.addAttribute("genres", genres);
        return "genresList";
    }

    @GetMapping("genres")
    public List<Genre> getGenreList() {
        return genresService.getGenres(Function.identity());
    }
}

package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.function.Function;

@Controller
public class GenresController {
    private final OperationsService opService;

    @Autowired
    public GenresController(OperationsService ops) {
        this.opService = ops;
    }

    @GetMapping("genres")
    public String listPage(Model model) {
        List<Genre> genres = opService.getGenres(Function.identity());
        model.addAttribute("genres", genres);
        return "genresList";
    }
}

package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
public class AuthorsController {
    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService ops) {
        this.authorsService = ops;
    }

    //@GetMapping("authors")
    public String listPage(Model model) {
        List<Author> authors = authorsService.getAuthors(Function.identity());
        model.addAttribute("authors", authors);
        return "authorsList";
    }

    @GetMapping("authors")
    public List<Author> getAuthorsList() {
        return authorsService.getAuthors(Function.identity());
    }
}

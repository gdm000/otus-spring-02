package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.function.Function;

@Controller
public class AuthorsController {
    private final OperationsService opService;

    @Autowired
    public AuthorsController(OperationsService ops) {
        this.opService = ops;
    }

    @GetMapping("authors")
    public String listPage(Model model) {
        List<Author> authors = opService.getAuthors(Function.identity());
        model.addAttribute("authors", authors);
        return "authorsList";
    }
}

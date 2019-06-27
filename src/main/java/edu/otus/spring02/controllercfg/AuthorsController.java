package edu.otus.spring02.controllercfg;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RestController
public class AuthorsController {
    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService ops) {
        this.authorsService = ops;
    }

    @GetMapping("authors")
    public Flux<Author> getAuthorsList() {
        return authorsService.getAuthors(Function.identity());
    }
}

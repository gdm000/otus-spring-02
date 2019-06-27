package edu.otus.spring02.controllercfg;

import edu.otus.spring02.domain.Book;
import edu.otus.spring02.service.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
@Slf4j
public class BooksController {
    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService ops) {
        this.booksService = ops;
    }

    @GetMapping("books")
    public Flux<Book> getBooksList() {
        return booksService.getBooks(Function.identity());
    }

    @GetMapping("books/{id}")
    public Mono<Book> getBook(@PathVariable("id") String id) {
        return booksService.getBook(id, Function.identity());
    }

    @GetMapping("books/delete/{id}")
    public Mono<Void> deletePage(@PathVariable("id") String id, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().add(HttpHeaders.LOCATION, "/booksList.html");
        return booksService.deleteBook(id).flatMap(v -> response.setComplete());
    }

    @PostMapping("books/save/{id}")
    public Mono<Void> save(@PathVariable("id") String id, ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().add(HttpHeaders.LOCATION, "/booksList.html");
        return exchange.getFormData().map(params -> booksService.updateBook(id, params.getFirst("name"), params.getFirst("author_id"), params.getFirst("genre_id"), Function.identity()).doOnError(t ->
                log.error("Failed to update book", t)
        ).flatMap(s -> response.setComplete())).flatMap(Function.identity());
    }

    @PostMapping("books/create")
    public Mono<Void> create(@ModelAttribute("name") String name, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().add(HttpHeaders.LOCATION, "/booksList.html");
        return exchange.getFormData().map(params -> booksService.createBook(params.getFirst("name"), params.getFirst("author_id"), params.getFirst("genre_id")).doOnError(t ->
                log.error("Failed to create book", t)
        ).flatMap(s -> response.setComplete())).flatMap(Function.identity());
    }
}

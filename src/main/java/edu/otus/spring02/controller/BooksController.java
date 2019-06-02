package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Book;
import edu.otus.spring02.service.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
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
    public List<Book> getBooksList() {
        return booksService.getBooks(Function.identity());
    }

    @GetMapping("books/{id}")
    public Book getBook(@PathVariable("id") String id) {
        return booksService.getBook(id, Function.identity()).orElse(null);
    }

    @GetMapping("books/delete/{id}")
    public RedirectView deletePage( @PathVariable("id") String id) {
        booksService.deleteBook(id);
        return new RedirectView("/booksList.html");
    }

    @PostMapping("books/save/{id}")
    public RedirectView save(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("genre_id") String genreId, @RequestParam("author_id") String authorId){
        try {
            booksService.updateBook(id, name, authorId, genreId, Function.identity());
        } catch (Exception e) {
            log.error("Failed to update book {}", id, e);
        } finally {
            return new RedirectView("/booksList.html");
        }
    }

    @PostMapping("books/create")
    public RedirectView create(@RequestParam("name") String name, @RequestParam("genre_id") String genreId, @RequestParam("author_id") String authorId) {
        try {
            booksService.createBook(name, authorId, genreId);
        } catch (Exception e) {
            log.error("Failed to create book", e);
        } finally {
            return new RedirectView("/booksList.html");
        }
    }
}

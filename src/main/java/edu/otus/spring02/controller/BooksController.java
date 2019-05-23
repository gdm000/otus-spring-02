package edu.otus.spring02.controller;

import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.OperationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Controller
@Slf4j
public class BooksController {
    private final OperationsService opService;

    @Autowired
    public BooksController(OperationsService ops) {
        this.opService = ops;
    }

    @GetMapping("books")
    public String listPage(Model model) {
        List<Book> books = opService.getBooks(Function.identity());
        model.addAttribute("books", books);
        return "booksList";
    }

    @GetMapping("books/{id}")
    public String viewPage(Model model, @PathVariable("id") String id) {
        Book book = opService.getBook(id, Function.identity()).orElse(null);
        if (book == null) {
            return "redirect:/books";
        } else {
            model.addAttribute("book", book);
            return "bookView";
        }
    }

    @GetMapping("books/delete/{id}")
    public String deletePage( @PathVariable("id") String id) {
        opService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("books/edit/{id}")
    public String editPage(Model model, @PathVariable("id") String id) {
        Book book = opService.getBook(id, Function.identity()).orElse(null);
        if (book == null) {
            return "redirect:/books";
        } else {
            model.addAttribute("book", book);
            model.addAttribute("genres", opService.getGenres(Function.identity()));
            model.addAttribute("authors", opService.getAuthors(Function.identity()));
            return "bookEdit";
        }
    }

    @GetMapping("books/create")
    public String createPage(Model model) {
            model.addAttribute("genres", opService.getGenres(Function.identity()));
            model.addAttribute("authors", opService.getAuthors(Function.identity()));
            return "bookCreate";
    }

    @PostMapping("books/save/{id}")
    public String save(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("genre_id") String genreId, @RequestParam("author_id") String authorId) {
        try {
            opService.updateBook(id, name, authorId, genreId, Function.identity());
        } catch (Exception e) {
            log.error("Failed to update book {}", id, e);
        } finally {
            return "redirect:/books";
        }
    }

    @PostMapping("books/create")
    public String editCreate(@RequestParam("name") String name, @RequestParam("genre_id") String genreId, @RequestParam("author_id") String authorId) {
        try {
            opService.createBook(name, authorId, genreId);
        } catch (Exception e) {
            log.error("Failed to create book", e);
        } finally {
            return "redirect:/books";
        }
    }
}

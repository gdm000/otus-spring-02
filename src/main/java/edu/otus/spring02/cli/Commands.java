package edu.otus.spring02.cli;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.OperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final OperationsService opService;

    public static final String CMD_AUTHOR = "author";
    public static final String CMD_BOOK = "book";
    public static final String CMD_GENRE = "genre";
    public static final String CMD_COMMENT = "comment";
    public static final String NOT_FOUND = "Not found";

    @ShellMethod("list available entities")
    public List<String> list(@ShellOption String entity, @ShellOption(defaultValue = "") String subsetId) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return opService.getAuthors(Object::toString);
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return opService.getGenres(Object::toString);
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return opService.getBooks(Object::toString);
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            if (subsetId.isEmpty()) {
                return opService.getComments(Object::toString);
            } else {
                return opService.getComments(subsetId, Object::toString);
            }
        } else {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod("get entity")
    public String get(@ShellOption String entity, @ShellOption String id) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return opService.getAuthor(id, Author::toString).orElse(NOT_FOUND);
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return opService.getGenre(id, Genre::toString).orElse(NOT_FOUND);
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return opService.getBook(id, Book::toString).orElse(NOT_FOUND);
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            return opService.getComment(id, Comment::toString).orElse(NOT_FOUND);
        } else  {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod(value = "Create book (name, genre author)", key = "create book")
    public String  createBook(String name, String genreId, String authorId) {
        return opService.createBook(name, authorId, genreId);
    }

    @ShellMethod(value = "Create author", key = "create author")
    public String createAuthor(String name) {
        return opService.createAuthor(name);
    }

    @ShellMethod(value = "Create genre", key = "create genre")
    public String createGenre(String name, String description) {
        return opService.createGenre(name, description);
    }

    @ShellMethod(value = "Create comment (text, book)", key = "create comment")
    public String createComment(String name, String bookId) {
        return opService.createComment(name, bookId);
    }

    @ShellMethod(value = "Delete book with comments", key = "delete book")
    public String deleteBook(String bookId) {
        opService.deleteBook(bookId);
        return "No more book with id " + bookId;
    }
}

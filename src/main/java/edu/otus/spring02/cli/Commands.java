package edu.otus.spring02.cli;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import edu.otus.spring02.domain.Genre;
import edu.otus.spring02.service.AuthorsService;
import edu.otus.spring02.service.BooksService;
import edu.otus.spring02.service.CommentsService;
import edu.otus.spring02.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final BooksService booksService;
    private final CommentsService commentsService;

    public static final String CMD_AUTHOR = "author";
    public static final String CMD_BOOK = "book";
    public static final String CMD_GENRE = "genre";
    public static final String CMD_COMMENT = "comment";
    public static final String NOT_FOUND = "Not found";

    @ShellMethod("list available entities")
    public List<String> list(@ShellOption String entity, @ShellOption(defaultValue = "") String subsetId) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorsService.getAuthors(Object::toString).collectList().block();
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genresService.getGenres(Object::toString).collectList().block();
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return booksService.getBooks(Object::toString).collectList().block();
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            if (subsetId.isEmpty()) {
                return commentsService.getComments(Object::toString).collectList().block();
            } else {
                return commentsService.getComments(subsetId, Object::toString).collectList().block();
            }
        } else {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod("get entity")
    public String get(@ShellOption String entity, @ShellOption String id) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorsService.getAuthor(id, Author::toString).blockOptional().orElse(NOT_FOUND);
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genresService.getGenre(id, Genre::toString).blockOptional().orElse(NOT_FOUND);
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return booksService.getBook(id, Book::toString).blockOptional().orElse(NOT_FOUND);
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            return commentsService.getComment(id, Comment::toString).blockOptional().orElse(NOT_FOUND);
        } else  {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod(value = "Create book (name, genre author)", key = "create book")
    public String  createBook(String name, String genreId, String authorId) {
        return booksService.createBook(name, authorId, genreId).block();
    }

    @ShellMethod(value = "Create author", key = "create author")
    public String createAuthor(String name) {
        return authorsService.createAuthor(name).block();
    }

    @ShellMethod(value = "Create genre", key = "create genre")
    public String createGenre(String name, String description) {
        return genresService.createGenre(name, description).block();
    }

    @ShellMethod(value = "Create comment (text, book)", key = "create comment")
    public String createComment(String name, String bookId) {
        return commentsService.createComment(name, bookId).block();
    }

    @ShellMethod(value = "Delete book with comments", key = "delete book")
    public String deleteBook(String bookId) {
        booksService.deleteBook(bookId);
        return "No more book with id " + bookId;
    }
}

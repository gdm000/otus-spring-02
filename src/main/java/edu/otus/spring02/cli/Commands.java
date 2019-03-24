package edu.otus.spring02.cli;

import edu.otus.spring02.dao.AuthorDao;
import edu.otus.spring02.dao.BookDao;
import edu.otus.spring02.dao.GenreDao;
import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class Commands {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    public static final String CMD_AUTHOR = "author";
    public static final String CMD_BOOK = "book";
    public static final String CMD_GENRE = "genre";

    public Commands(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @ShellMethod("list available entities")
    public List<String> list(@ShellOption String entity) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorDao.getAuthors().stream().map(Object::toString).collect(Collectors.toList());
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genreDao.getGenres().stream().map(Object::toString).collect(Collectors.toList());
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return bookDao.getBooks().stream().map(Object::toString).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod("get entity")
    public String get(@ShellOption String entity, @ShellOption int id) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorDao.getAuthor(id).map(Author::toString).orElse("Not found");
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genreDao.getGenre(id).map(Genre::toString).orElse("Not found");
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return bookDao.getBook(id).map(Book::toString).orElse("Not found");
        } else {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    @ShellMethod(value = "Create book", key = "create book")
    public int  createBook(String name, int genreId, int authorId) {
        return bookDao.createBook(new Book(name, genreId, authorId));
    }

    @ShellMethod(value = "Create author", key = "create author")
    public int createAuthor(String name) {
        return authorDao.createAuthor(new Author(name));
    }

    @ShellMethod(value = "Create genre", key = "create genre")
    public int createGnere(String name) {
        return genreDao.createGenre(new Genre(name));
    }
}

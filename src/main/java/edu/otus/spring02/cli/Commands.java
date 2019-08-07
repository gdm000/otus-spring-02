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

import java.util.List;

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

    public List<String> list(String entity, String subsetId) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorsService.getAuthors(Object::toString);
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genresService.getGenres(Object::toString);
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return booksService.getBooks(Object::toString);
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            if (subsetId.isEmpty()) {
                return commentsService.getComments(Object::toString);
            } else {
                return commentsService.getComments(subsetId, Object::toString);
            }
        } else {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    public String get(String entity, String id) {
        if (CMD_AUTHOR.equalsIgnoreCase(entity)) {
            return authorsService.getAuthor(id, Author::toString).orElse(NOT_FOUND);
        } else if (CMD_GENRE.equalsIgnoreCase(entity)) {
            return genresService.getGenre(id, Genre::toString).orElse(NOT_FOUND);
        } else if (CMD_BOOK.equalsIgnoreCase(entity)) {
            return booksService.getBook(id, Book::toString).orElse(NOT_FOUND);
        } else if (CMD_COMMENT.equalsIgnoreCase(entity)) {
            return commentsService.getComment(id, Comment::toString).orElse(NOT_FOUND);
        } else  {
            throw new IllegalArgumentException("Unknown entity: "+entity);
        }
    }

    public String  createBook(String name, String genreId, String authorId) {
        return booksService.createBook(name, authorId, genreId);
    }

    public String createAuthor(String name) {
        return authorsService.createAuthor(name);
    }

    public String createGenre(String name, String description) {
        return genresService.createGenre(name, description);
    }

    public String createComment(String name, String bookId) {
        return commentsService.createComment(name, bookId);
    }

    public String deleteBook(String bookId) {
        booksService.deleteBook(bookId);
        return "No more book with id " + bookId;
    }
}

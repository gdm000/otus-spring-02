package edu.otus.spring02.service;

import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import edu.otus.spring02.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OperationsService {
    <T> List<T> getAuthors(Function<Author, T> mapper);
    <T> List<T> getBooks(Function<Book, T> mapper);
    <T> List<T>  getGenres(Function<Genre, T> mapper);
    <T> List<T>  getComments(Function<Comment, T> mapper);
    <T> List<T>  getComments(String bookId, Function<Comment, T> mapper);

    <T> Optional<T> getAuthor(String id, Function<Author, T> mapper);
    <T> Optional<T> getBook(String id, Function<Book, T> mapper);
    <T> Optional<T> getGenre(String id, Function<Genre, T> mapper);
    <T> Optional<T> getComment(String id, Function<Comment, T> mapper);

    String createAuthor(String name);
    String createGenre(String name, String description);
    String createBook(String name, String authorId, String genreId);
    String createComment(String text, String bookId);

}

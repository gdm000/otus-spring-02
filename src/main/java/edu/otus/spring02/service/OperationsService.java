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
    <T> List<T>  getComments(int bookId, Function<Comment, T> mapper);

    <T> Optional<T> getAuthor(int id, Function<Author, T> mapper);
    <T> Optional<T> getBook(int id, Function<Book, T> mapper);
    <T> Optional<T> getGenre(int id, Function<Genre, T> mapper);
    <T> Optional<T> getComment(int id, Function<Comment, T> mapper);

    int createAuthor(String name);
    int createGenre(String name);
    int createBook(String name, int authorId, int genreId);
    int createComment(String text, int bookId);

}

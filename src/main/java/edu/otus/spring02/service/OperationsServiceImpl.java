package edu.otus.spring02.service;

import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.dao.GenreRepository;
import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import edu.otus.spring02.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OperationsServiceImpl implements OperationsService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public <T> List<T> getAuthors(Function<Author, T> mapper) {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> List<T> getBooks(Function<Book, T> mapper) {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> List<T> getGenres(Function<Genre, T> mapper) {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> List<T> getComments(Function<Comment, T> mapper) {
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> List<T> getComments(String bookId, Function<Comment, T> mapper) {
        return bookRepository.findById(bookId).map(Book::getComments).get().stream().map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> Optional<T> getAuthor(String id, Function<Author, T> mapper) {
        return authorRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public <T> Optional<T> getBook(String id, Function<Book, T> mapper) {
        return bookRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public <T> Optional<T> getGenre(String id, Function<Genre, T> mapper) {
        return genreRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public <T> Optional<T> getComment(String id, Function<Comment, T> mapper) {
        return commentRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public String createAuthor(String name) {
        return authorRepository.save(Author.builder().name(name).build()).getId();
    }

    @Transactional
    @Override
    public String createGenre(String name, String description) {
        return genreRepository.save(Genre.builder().name(name).build()).getName();
    }

    @Transactional
    @Override
    public String createBook(String name, String authorId, String genreId) {
        return bookRepository.save(
                Book.builder()
                        .name(name)
                        .author(getAuthor(authorId, Function.identity()).get())
                        .genre(getGenre(genreId, Function.identity()).get())
                        .build()).getId();
    }

    @Transactional
    @Override
    public String createComment(String text, String bookId) {
        Comment comment = Comment.builder().text(text).build();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.getComments().add(comment);
        book = bookRepository.save(book);
        return book.getComments().get(book.getComments().indexOf(comment)).getId();
    }
}

package edu.otus.spring02.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.GenreRepository;
import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
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
public class BooksServiceImpl implements BooksService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final AuthorsService authorsService;
    private final GenresService genresService;

    @HystrixCommand
    @Transactional
    @Override
    public <T> List<T> getBooks(Function<Book, T> mapper) {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @HystrixCommand
    @Transactional
    @Override
    public <T> Optional<T> getBook(String id, Function<Book, T> mapper) {
        return bookRepository.findById(id).map(mapper);
    }

    @HystrixCommand(fallbackMethod = "fallbackCreate")
    @Transactional
    @Override
    public String createBook(String name, String authorId, String genreId) {
        return bookRepository.save(
                Book.builder()
                        .name(name)
                        .author(authorsService.getAuthor(authorId, Function.identity()).get())
                        .genre(genresService.getGenre(genreId, Function.identity()).get())
                        .build()).getId();
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdate")
    @Override
    public <T> T updateBook(String bookId, String name, String authorId, String genreId, Function<Book, T> mapper) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new IllegalArgumentException("Genre not found"));
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        return mapper.apply(bookRepository.save(book));
    }

    public String fallbackCreate(String name, String authorId, String genreId) {
        return null;
    }

    public Object fallbackUpdate(String bookId, String name, String authorId, String genreId, Function<Book, ?> mapper) {
        return null;
    }

    @Override
    public void deleteBook(String bookId) {
        bookRepository.deleteById(bookId);
    }
}

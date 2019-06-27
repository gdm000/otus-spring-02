package edu.otus.spring02.service;

import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.GenreRepository;
import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final AuthorsService authorsService;
    private final GenresService genresService;

    @Override
    public <T> Flux<T> getBooks(Function<Book, T> mapper) {
        return bookRepository.findAll().map(mapper);
    }

    @Override
    public <T> Mono<T> getBook(String id, Function<Book, T> mapper) {
        return bookRepository.findById(id).map(mapper);
    }

    @Override
    public Mono<String> createBook(String name, String authorId, String genreId) {
        return Mono.just(Book.builder().name(name))
                .zipWith(authorsService.getAuthor(authorId, Function.identity()), (book, author) -> book.author(author))
                .zipWith(genresService.getGenre(genreId, Function.identity()), (book, genre) -> book.genre(genre))
                .flatMap(book-> bookRepository.save(book.build()))
                .map(Book::getId);
    }

    @Override
    public <T> Mono<T> updateBook(String bookId, String name, String authorId, String genreId, Function<Book, T> mapper) {
        return bookRepository.findById(bookId).switchIfEmpty(Mono.error(new IllegalArgumentException("Book not found")))
                .doOnNext(book -> book.setName(name))
                .zipWith(authorRepository.findById(authorId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Author not found"))),
                        (book, author) -> {book.setAuthor(author); return book;})
                .zipWith(genreRepository.findById(genreId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Genre not found"))),
                        (book, genre) -> {book.setGenre(genre); return book;})
                .flatMap(book -> bookRepository.save(book)).map(book -> mapper.apply(book));
    }

    @Override
    public Mono<Void> deleteBook(String bookId) {
        return bookRepository.deleteById(bookId);
    }
}

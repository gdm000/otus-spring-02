package edu.otus.spring02.service;

import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public <T> Flux<T> getComments(Function<Comment, T> mapper) {
        return commentRepository.findAll().map(mapper);
    }

    @Transactional
    @Override
    public <T> Flux<T> getComments(String bookId, Function<Comment, T> mapper) {
        return bookRepository.findById(bookId).map(Book::getComments).flatMapIterable(Function.identity()).map(mapper);
    }

    @Transactional
    @Override
    public <T> Mono<T> getComment(String id, Function<Comment, T> mapper) {
        return commentRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public Mono<String> createComment(String text, String bookId) {
        Comment comment = Comment.builder().text(text).build();
        return bookRepository.findById(bookId)
                .zipWith(commentRepository.save(comment), (book, c) -> {book.getComments().add(c); return book;})
                .flatMap(book -> bookRepository.save(book))
                .map(book -> book.getComments().get(book.getComments().indexOf(comment)).getId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Book not found")));
    }
}

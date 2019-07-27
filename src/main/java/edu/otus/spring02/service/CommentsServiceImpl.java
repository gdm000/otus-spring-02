package edu.otus.spring02.service;

import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
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
public class CommentsServiceImpl implements CommentsService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

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
    public <T> Optional<T> getComment(String id, Function<Comment, T> mapper) {
        return commentRepository.findById(id).map(mapper);
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

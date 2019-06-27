package edu.otus.spring02.service;

import edu.otus.spring02.domain.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface CommentsService {
    <T> Flux<T> getComments(Function<Comment, T> mapper);
    <T> Flux<T>  getComments(String bookId, Function<Comment, T> mapper);
    <T> Mono<T> getComment(String id, Function<Comment, T> mapper);
    Mono<String> createComment(String text, String bookId);
}

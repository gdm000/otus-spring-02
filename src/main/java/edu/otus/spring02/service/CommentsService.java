package edu.otus.spring02.service;

import edu.otus.spring02.domain.Comment;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CommentsService {
    <T> List<T> getComments(Function<Comment, T> mapper);
    <T> List<T>  getComments(String bookId, Function<Comment, T> mapper);
    <T> Optional<T> getComment(String id, Function<Comment, T> mapper);
    String createComment(String text, String bookId);
}

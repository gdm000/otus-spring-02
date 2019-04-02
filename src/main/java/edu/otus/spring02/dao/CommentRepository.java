package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> getComments();
    List<Comment> getComments(int bookId);
    Optional<Comment> getComment(int id);
    int createComment(Comment prototype);
}

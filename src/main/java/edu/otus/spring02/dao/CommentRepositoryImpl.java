package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final EntityManager em;

    @Override
    public List<Comment> getComments() {
        return em.createQuery("select c from Comment c order by c.id", Comment.class).getResultList();
    }

    @Override
    public List<Comment> getComments(int bookId) {
        return em.createQuery("select c from Comment c where c.book.id = :bookId order by c.id", Comment.class)
                .setParameter("bookId", bookId).getResultList();
    }

    @Override
    public Optional<Comment> getComment(int id) {
        return Optional.of(em.find(Comment.class, id));
    }

    @Override
    public int createComment(Comment prototype) {
        em.persist(prototype);
        return prototype.getId();
    }
}

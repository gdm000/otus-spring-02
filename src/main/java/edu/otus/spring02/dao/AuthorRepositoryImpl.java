package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final EntityManager em;

    @Override
    public List<Author> getAuthors() {
        return em.createQuery("select a from Author a order by a.id asc ", Author.class).getResultList();
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public int createAuthor(Author prototype) {
        em.persist(prototype);
        return prototype.getId();
    }
}

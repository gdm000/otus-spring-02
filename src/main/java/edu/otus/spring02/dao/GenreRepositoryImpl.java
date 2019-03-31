package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    private final EntityManager em;

    @Override
    public List<Genre> getGenres() {
        return em.createQuery("select g from Genre g order by g.id", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> getGenre(int id) {
            return Optional.of(em.find(Genre.class, id));

    }

    @Override
    public int createGenre(Genre prototype) {
        em.persist(prototype);
        return prototype.getId();
    }
}

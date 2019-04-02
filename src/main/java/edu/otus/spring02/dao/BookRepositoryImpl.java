package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManager em;

    @Override
    public List<Book> getBooks() {
        return em.createQuery("select b from Book b order by b.id", Book.class).getResultList();
    }

    @Override
    public Optional<Book> getBook(int id) {
            return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public int createBook(Book prototype) {
        em.persist(prototype);
        return prototype.getId();
    }
}

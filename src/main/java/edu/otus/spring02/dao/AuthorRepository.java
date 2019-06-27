package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}

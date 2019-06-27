package edu.otus.spring02.service;

import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public <T> Flux<T> getAuthors(Function<Author, T> mapper) {
        return authorRepository.findAll().map(mapper);
    }

    @Transactional
    @Override
    public <T> Mono<T> getAuthor(String id, Function<Author, T> mapper) {
        return authorRepository.findById(id).map(mapper);
    }

    @Transactional
    @Override
    public Mono<String> createAuthor(String name) {
        return authorRepository.save(Author.builder().name(name).build()).map(Author::getId);
    }
}

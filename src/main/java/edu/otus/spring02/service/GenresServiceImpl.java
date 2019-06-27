package edu.otus.spring02.service;

import edu.otus.spring02.dao.GenreRepository;
import edu.otus.spring02.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public <T> Flux<T> getGenres(Function<Genre, T> mapper) {
        return genreRepository.findAll().map(mapper);
    }

    @Transactional
    @Override
    public <T> Mono<T> getGenre(String id, Function<Genre, T> mapper) {
        return genreRepository.findById(id).map(mapper);
    }


    @Transactional
    @Override
    public Mono<String> createGenre(String name, String description) {
        return genreRepository.save(Genre.builder().name(name).build()).map(Genre::getName);
    }
}

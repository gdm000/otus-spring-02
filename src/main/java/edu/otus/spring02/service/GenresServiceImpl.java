package edu.otus.spring02.service;

import edu.otus.spring02.dao.GenreRepository;
import edu.otus.spring02.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public <T> List<T> getGenres(Function<Genre, T> mapper) {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <T> Optional<T> getGenre(String id, Function<Genre, T> mapper) {
        return genreRepository.findById(id).map(mapper);
    }


    @Transactional
    @Override
    public String createGenre(String name, String description) {
        return genreRepository.save(Genre.builder().name(name).build()).getName();
    }
}

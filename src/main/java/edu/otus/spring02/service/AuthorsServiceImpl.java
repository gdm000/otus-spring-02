package edu.otus.spring02.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.domain.Author;
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
public class AuthorsServiceImpl implements AuthorsService{
    private final AuthorRepository authorRepository;

    @HystrixCommand
    @Transactional
    @Override
    public <T> List<T> getAuthors(Function<Author, T> mapper) {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @HystrixCommand
    @Transactional
    @Override
    public <T> Optional<T> getAuthor(String id, Function<Author, T> mapper) {
        return authorRepository.findById(id).map(mapper);
    }

    @HystrixCommand
    @Transactional
    @Override
    public String createAuthor(String name) {
        return authorRepository.save(Author.builder().name(name).build()).getId();
    }
}

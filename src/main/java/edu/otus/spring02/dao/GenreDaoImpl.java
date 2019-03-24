package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOp;
    private final RowMapper<Genre> mapper = (rs, i) -> {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    };

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOp) {
        this.jdbcOp = jdbcOp;
    }

    @Override
    public List<Genre> getGenres() {
        return jdbcOp.getJdbcOperations().query("select id, `name` from genre order by id asc", mapper);
    }

    @Override
    public Optional<Genre> getGenre(int id) {
        try {
            return Optional.of(jdbcOp.queryForObject("select id, `name` from genre where id = :id", new MapSqlParameterSource().addValue("id", id), mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int createGenre(Genre prototype) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(
                "insert into genre (`name`) values (:name)",
                new MapSqlParameterSource()
                        .addValue("name", prototype.getName()),
                keyHolder);
        return keyHolder.getKey().intValue();
    }
}

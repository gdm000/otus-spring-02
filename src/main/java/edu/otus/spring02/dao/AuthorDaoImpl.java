package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOp;
    private final RowMapper<Author> mapper = (rs, i) -> {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));
        return author;
    };

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOp) {
        this.jdbcOp = jdbcOp;
    }

    @Override
    public List<Author> getAuthors() {
        return jdbcOp.getJdbcOperations().query("select id, `name` from author order by id asc", mapper);
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        try {
            return Optional.of(jdbcOp.queryForObject("select id, `name` from author where id = :id", new MapSqlParameterSource().addValue("id", id),  mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int createAuthor(Author prototype) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(
                "insert into author (`name`) values (:name)",
                new MapSqlParameterSource()
                        .addValue("name", prototype.getName()),
                keyHolder);
        return keyHolder.getKey().intValue();
    }
}

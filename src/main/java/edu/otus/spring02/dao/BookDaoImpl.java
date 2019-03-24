package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations jdbcOp;
    private final RowMapper<Book> mapper = (rs, i) -> {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setGenreId(rs.getInt("genre_id"));
        return book;
    };

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOp) {
        this.jdbcOp = jdbcOp;
    }

    @Override
    public List<Book> getBooks() {
        return jdbcOp.query("select id, `name`, author_id, genre_id from book", mapper);
    }

    @Override
    public Optional<Book> getBook(int id) {
        try {
            return Optional.of(jdbcOp.queryForObject("select id, `name`, author_id, genre_id from book where id = :id", new MapSqlParameterSource().addValue("id", id), mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int createBook(Book prototype) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(
                "insert into book (`name`, genre_id, author_id) values (:name, :genre_id, :author_id)",
                new MapSqlParameterSource()
                        .addValue("name", prototype.getName())
                        .addValue("genre_id", prototype.getGenreId())
                        .addValue("author_id", prototype.getAuthorId()),
                        keyHolder);
        return keyHolder.getKey().intValue();
    }
}

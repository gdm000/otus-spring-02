package edu.otus.spring02.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOp;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOp) {
        this.jdbcOp = jdbcOp;
    }
}

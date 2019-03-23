package edu.otus.spring02.cli;

import edu.otus.spring02.dao.AuthorDao;
import edu.otus.spring02.dao.BookDao;
import edu.otus.spring02.dao.KindDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;

@ShellComponent
public class Commands {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final KindDao kindDao;

    public Commands(AuthorDao authorDao, BookDao bookDao, KindDao kindDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.kindDao = kindDao;
    }

    @ShellMethod("list available entities")
    public List<String> list(@ShellOption String entity) {
        return Arrays.asList("1", "2");
    }
}

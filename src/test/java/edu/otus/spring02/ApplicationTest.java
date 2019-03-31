package edu.otus.spring02;

import edu.otus.spring02.cli.Commands;
import edu.otus.spring02.dao.AuthorRepository;
import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.GenreDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.initialization-mode=never",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Sql(scripts={"classpath:schema.sql", "classpath:testData.sql"})
        @ExtendWith(SpringExtension.class)
public class ApplicationTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GenreDao genreDao;

    Commands commands;

    @PostConstruct
    public void before() {
        commands = new Commands(authorRepository, bookRepository, genreDao);
    }


    @Test
    public void testList() throws Exception {
        assertThat(commands.list(Commands.CMD_AUTHOR)).hasSize(2).allMatch(s-> s.matches(".*Author\\d.*"));
        assertThat(commands.list(Commands.CMD_GENRE)).hasSize(2).allMatch(s-> s.matches(".*Genre\\d.*"));
        assertThat(commands.list(Commands.CMD_BOOK)).hasSize(2).allMatch(s-> s.matches(".*Book\\d.*"));
    }

    @Test
    public void testGet() throws Exception {
        assertThat(commands.get(Commands.CMD_AUTHOR, 2)).contains("Author2");
        assertThat(commands.get(Commands.CMD_GENRE, 2)).contains("Genre2");
        assertThat(commands.get(Commands.CMD_BOOK, 2)).contains("Book2");
    }

    @Test
    public void testCreate() throws Exception {
        int aId = commands.createAuthor("AuthorTest");
        assertThat(commands.get(Commands.CMD_AUTHOR, aId).contains("AuthorTest"));
        int gId = commands.createGnere("GenreTest");
        assertThat(commands.get(Commands.CMD_GENRE, gId).contains("GenreTest"));
        int bId = commands.createBook("BookTest", gId, aId);
        assertThat(commands.get(Commands.CMD_BOOK, bId).contains("BookTest"));
    }
}

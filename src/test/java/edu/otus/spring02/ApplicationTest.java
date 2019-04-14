package edu.otus.spring02;

import edu.otus.spring02.cli.Commands;
import edu.otus.spring02.service.OperationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import static edu.otus.spring02.cli.Commands.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.initialization-mode=never",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Sql(scripts={"classpath:schema.sql", "classpath:testData.sql"})
        @ExtendWith(SpringExtension.class)
public class ApplicationTest {
    @Autowired
    OperationsService operationsService;

    Commands commands;

    @PostConstruct
    public void before() {
        commands = new Commands(operationsService);
    }


    @Test
    public void testList() {
        assertThat(commands.list(Commands.CMD_AUTHOR, "")).hasSize(2).allMatch(s-> s.matches(".*Author(1|2).*"));
        assertThat(commands.list(Commands.CMD_GENRE, "")).hasSize(2).allMatch(s-> s.matches(".*Genre(1|2).*"));
        assertThat(commands.list(Commands.CMD_BOOK, "")).hasSize(2).allMatch(s-> s.matches(".*Book(1|2).*"));
        assertThat(commands.list(Commands.CMD_COMMENT, "")).hasSize(2).allMatch(s-> s.matches(".*Comment(1|2).*"));
        assertThat(commands.list(Commands.CMD_COMMENT, "1")).hasSize(2).allMatch(s-> s.matches(".*Comment(1|2).*"));
        assertThat(commands.list(Commands.CMD_COMMENT, "2")).hasSize(0);
    }

    @Test
    public void testGet() {
        assertThat(commands.get(Commands.CMD_AUTHOR, 2)).contains("Author2");
        assertThat(commands.get(Commands.CMD_AUTHOR, 20)).isEqualTo(NOT_FOUND);
        assertThat(commands.get(Commands.CMD_GENRE, 2)).contains("Genre2");
        assertThat(commands.get(Commands.CMD_GENRE, 20)).isEqualTo(NOT_FOUND);
        assertThat(commands.get(Commands.CMD_BOOK, 2)).contains("Book2");
        assertThat(commands.get(Commands.CMD_BOOK, 20)).isEqualTo(NOT_FOUND);
        assertThat(commands.get(Commands.CMD_COMMENT, 2)).contains("Comment2");
        assertThat(commands.get(Commands.CMD_COMMENT, 20)).isEqualTo(NOT_FOUND);
    }

    @Test
    public void testCreate() {
        int aId = commands.createAuthor("AuthorTest");
        assertThat(commands.get(Commands.CMD_AUTHOR, aId).contains("AuthorTest"));
        int gId = commands.createGenre("GenreTest");
        assertThat(commands.get(Commands.CMD_GENRE, gId).contains("GenreTest"));
        int bId = commands.createBook("BookTest", gId, aId);
        assertThat(commands.get(Commands.CMD_BOOK, bId).contains("BookTest"));
        int cId = commands.createComment("CommentTest", bId);
        assertThat(commands.get(Commands.CMD_COMMENT, cId).contains("CommentTest"));
        assertThat(commands.list(Commands.CMD_COMMENT, bId+"")).hasSize(1).allMatch(s-> s.matches(".*CommentTest.*"));
    }
}

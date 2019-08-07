package edu.otus.spring02.testchangelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import edu.otus.spring02.domain.Author;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import edu.otus.spring02.domain.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeLog(order = "001")
public class InitMongoDBTestDataChangeLog {

    private Genre genre1 = Genre.builder().name("Genre1").build();
    private Genre genre2 = Genre.builder().name("Genre2").build();

    private Author author1 = Author.builder().name("Author1").id("1").build();
    private Author author2 = Author.builder().name("Author2").id("2").build();

    private Comment comment1 = Comment.builder().text("Comment1").id("1").build();
    private Comment comment2 = Comment.builder().text("Comment2").id("2").build();

    private Book book1 = Book.builder().genre(genre1).author(author1).name("Book1").id("1").comments(
            Arrays.asList(comment1, comment2)
    ).build();
    private Book book2 = Book.builder().genre(genre2).author(author2).name("Book2").id("2").build();

    @ChangeSet(order = "000", id = "dropDB", author = "pankrashkinvs", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "pankrashkinvs", runAlways = true)
    public void initGenres(MongoTemplate template){
        template.save(genre1);
        template.save(genre2);
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "pankrashkinvs", runAlways = true)
    public void initAuthors(MongoTemplate template){
        template.save(author1);
        template.save(author2);
    }

    @ChangeSet(order = "003", id = "initComments", author = "pankrashkinvs", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(comment1);
        template.save(comment2);
    }

    @ChangeSet(order = "004", id = "initBooks", author = "pankrashkinvs", runAlways = true)
    public void initBooks(MongoTemplate template){
        template.save(book1);
        template.save(book2);
    }
}

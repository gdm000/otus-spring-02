package edu.otus.spring02.changelogs;

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
public class InitMongoDBDataChangeLog {

    private Genre classicGenre = Genre.builder().name("Classic literature").build();
    private Genre sciFiGenre = Genre.builder().name("Science fiction").build();

    private Author classicAuthor = Author.builder().name("Alexander Sergeyevich Pushkin").build();
    private Author sciFiAuthor = Author.builder().name("Robert Anson Heinlein").build();

    private Comment classicComment1 = Comment.builder().text("Classic is not dead").build();
    private Comment classicComment2 = Comment.builder().text("Classic is definitely not dead").build();

    private Book classicBook = Book.builder().genre(classicGenre).author(classicAuthor).name("Eugene Onegin").comments(
            Arrays.asList(classicComment1, classicComment2)
    ).build();
    private Book sciFiBook = Book.builder().genre(sciFiGenre).author(sciFiAuthor).name("Stranger in a Strange Land").build();

    @ChangeSet(order = "000", id = "dropDB", author = "pankrashkinvs", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "pankrashkinvs", runAlways = true)
    public void initGenres(MongoTemplate template){
        template.save(classicGenre);
        template.save(sciFiGenre);
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "pankrashkinvs", runAlways = true)
    public void initAuthors(MongoTemplate template){
        template.save(classicAuthor);
        template.save(sciFiAuthor);
    }

    @ChangeSet(order = "003", id = "initComments", author = "pankrashkinvs", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(classicComment1);
        template.save(classicComment2);
    }

    @ChangeSet(order = "004", id = "initBooks", author = "pankrashkinvs", runAlways = true)
    public void initBooks(MongoTemplate template){
        template.save(classicBook);
        template.save(sciFiBook);
    }
}

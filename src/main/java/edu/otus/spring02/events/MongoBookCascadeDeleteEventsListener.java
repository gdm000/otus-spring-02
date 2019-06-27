package edu.otus.spring02.events;

import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.domain.Book;
import edu.otus.spring02.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.findById(id)
                .map(Book::getComments)
                .map(comments -> comments == null ? new ArrayList<Comment>(): comments)
                .flatMapIterable(Function.identity())
                .flatMap(comment -> commentRepository.delete(comment))
                .publishOn(Schedulers.single()).subscribe();
    }
}

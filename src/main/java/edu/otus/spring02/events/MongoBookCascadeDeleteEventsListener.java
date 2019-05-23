package edu.otus.spring02.events;

import edu.otus.spring02.dao.BookRepository;
import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

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
        bookRepository.findById(id).ifPresent(book ->
            Optional.ofNullable(book.getComments())
                    .map(comments -> comments.stream())
                    .ifPresent(cStream -> cStream.forEach(comment -> commentRepository.delete(comment)))
        );
    }
}

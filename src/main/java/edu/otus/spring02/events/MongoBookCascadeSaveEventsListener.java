package edu.otus.spring02.events;

import edu.otus.spring02.dao.CommentRepository;
import edu.otus.spring02.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val book = event.getSource();
        if (book.getComments() != null) {
            book.getComments().stream().filter(e -> Objects.isNull(e.getId())).forEach(commentRepository::save);
        }
    }
}

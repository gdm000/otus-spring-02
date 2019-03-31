package edu.otus.spring02.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOOK_SEQ")
    private final int id;

    @Column(name = "name")
    private final String name;

    @OneToMany
    @JoinColumn(name = "genre_id")
    private final Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private final Author author;
}

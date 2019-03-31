package edu.otus.spring02.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTHOR_SEQ")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}

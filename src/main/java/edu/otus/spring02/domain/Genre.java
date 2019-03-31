package edu.otus.spring02.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GENRE_SEQ")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;
}

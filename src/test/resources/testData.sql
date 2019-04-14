insert into author (id, `name`) values (1, 'Author1');
insert into author (id, `name`) values (2, 'Author2');

insert into genre (id, `name`) values (1, 'Genre1');
insert into genre (id, `name`) values (2, 'Genre2');

insert into book (id, `name`, genre_id, author_id) values (1, 'Book1', 1, 1);
insert into book (id, `name`, genre_id, author_id) values (2, 'Book2', 2, 2);

insert into comment (id, `text`, book_id) values (1, 'Comment1', 1);
insert into comment (id, `text`, book_id) values (2, 'Comment2', 1);

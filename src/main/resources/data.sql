insert into author (id, `name`) values (1, 'Alexander Sergeyevich Pushkin');
insert into author (id, `name`) values (2, 'Robert Anson Heinlein');

insert into genre (id, `name`) values (1, 'Classic literature');
insert into genre (id, `name`) values (2, 'Science fiction');

insert into book (id, `name`, genre_id, author_id) values (1, 'Eugene Onegin', 1, 1);
insert into book (id, `name`, genre_id, author_id) values (2, 'Stranger in a Strange Land', 2, 2);

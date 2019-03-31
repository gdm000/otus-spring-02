DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS COMMENT;
CREATE TABLE AUTHOR(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255) NOT NULL);
CREATE TABLE GENRE(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255) NOT NULL);
CREATE TABLE BOOK(
  ID IDENTITY PRIMARY KEY,
  NAME VARCHAR(255) NOT NULL,
  GENRE_ID INT NOT NULL,
  AUTHOR_ID INT NOT NULL,
  FOREIGN KEY (GENRE_ID) REFERENCES GENRE(ID),
  FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR(ID)
);
CREATE TABLE COMMENT(
  ID IDENTITY PRIMARY KEY,
  TEXT VARCHAR(255) NOT NULL,
  BOOK_ID INT NOT NULL,
  FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID)
);


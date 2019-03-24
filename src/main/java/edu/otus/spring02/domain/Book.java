package edu.otus.spring02.domain;

public class Book {
    private int id;
    private String name;
    private int genreId;
    private int authorId;

    public Book(String name, int genreId, int authorId) {
        this.name = name;
        this.genreId = genreId;
        this.authorId = authorId;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGenreId() {
        return genreId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genreId=" + genreId +
                ", authorId=" + authorId +
                '}';
    }
}

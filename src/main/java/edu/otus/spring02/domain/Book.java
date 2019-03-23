package edu.otus.spring02.domain;

public class Book {
    private final int id;
    private final String name;
    private final int kindId;

    public Book(int id, String name, int kindId) {
        this.id = id;
        this.name = name;
        this.kindId = kindId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getKindId() {
        return kindId;
    }
}

package edu.otus.spring02.domain;

public class Kind {
    private final int id;
    private final int name;

    public Kind(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }
}

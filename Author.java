package mappe-2025-elisabethberg296-main;

public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Author: " + name;
    }
}

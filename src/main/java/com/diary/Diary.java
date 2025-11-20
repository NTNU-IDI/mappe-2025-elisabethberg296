package com.diary;

import java.util.ArrayList;

public class Diary {
    private String date;
    private String content;
    private ArrayList<Author> authors;

    public Diary(String date, String content) {
        this.date = date;
        this.content = content;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public String toString() {
        String text = "Date: " + date + "\n" +
                      "Content: " + content + "\n" +
                      "Authors: ";
        for (Author a : authors) {
            text += a.getName() + " ";
        }
        return text + "\n";
    }
}

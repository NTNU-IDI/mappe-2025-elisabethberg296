package com.diary;
import java.util.ArrayList;

public class DiaryRegistry {
    private ArrayList<Author> authors;
    private ArrayList<Diary> entries;

    public DiaryRegistry() {
        authors = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public void registerAuthor(Author author) {
        authors.add(author);
    }

    public Author findAuthor(String name) {
        for (Author a : authors) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public void registerEntry(Diary entry) {
        entries.add(entry);
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public ArrayList<Diary> getEntries() {
        return entries;
    }

    public boolean deleteEntry(int index) {
        if (index >= 0 && index < entries.size()) {
            entries.remove(index);
            return true;
        }
        return false;
    }

    // public int amountOfEntries() {
    //     int a = entries.size(); 
    //     return a;
    // }
}

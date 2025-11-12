package mappe-2025-elisabethberg296-main;

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

    public boolean deleteEntry(String date) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getDate().equalsIgnoreCase(date)) {
                entries.remove(i);
                return true;
            }
        }
        return false;
    }
}

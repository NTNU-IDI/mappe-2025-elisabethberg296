package com.diary;

import java.util.ArrayList;

/**
 * DiaryRegistry class that manages authors and diary entries.
 */
public class DiaryRegistry {

  private final ArrayList<Author> authors;
  private final ArrayList<Diary> entries;
  
  /**
   * Constructor for DiaryRegistry class.
   */
  public DiaryRegistry() {
    authors = new ArrayList<>();
    entries = new ArrayList<>();
  }

  /**
   * Registers a new author.

   * @param author The author to register.
   */
  public void registerAuthor(Author author) {
    authors.add(author);
  }

  /**
   * Finds an author by name.

   * @param name The name of the author to find.
   * @return The Author object if found, null otherwise.
   */
  public Author findAuthor(String name) {
    for (Author a : authors) {
      if (a.getName().equalsIgnoreCase(name)) {
        return a;
      }
    }
    return null;

  }

  /**
   * Registers a new diary entry.

   * @param entry The diary entry to register.
   */
  public void registerEntry(Diary entry) {
    entries.add(entry);
  }

  public ArrayList<Author> getAuthors() {
    return authors;
  }

  public ArrayList<Diary> getEntries() {
    return entries;
  }

  /**
   * Deletes a diary entry by index.

   * @param index The index of the diary entry to delete.
   * @return true if the entry was deleted, false otherwise.
   */
  public boolean deleteEntry(int index) {
    if (index >= 0 && index < entries.size()) {
      entries.remove(index);
      return true;
    }
    return false;
  }

}

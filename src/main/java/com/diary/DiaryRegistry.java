package com.diary;

import java.util.ArrayList;

/**
 * DiaryRegistry class that manages authors and diary entries.
 */
public class DiaryRegistry {

  private ArrayList<Author> authors;
  private ArrayList<Diary> entries;
  
  /**
   * Constructor for DiaryRegistry class.
   */
  public DiaryRegistry() {
    authors = new ArrayList<>();
    entries = new ArrayList<>();
  }

  /**
   * Registers a new author.
   */
  public void registerAuthor(Author author) {
    authors.add(author);
  }

  /**
   * Finds an author by name.
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
   */
  public boolean deleteEntry(int index) {
    if (index >= 0 && index < entries.size()) {
      entries.remove(index);
      return true;
    }
    return false;
  }
}

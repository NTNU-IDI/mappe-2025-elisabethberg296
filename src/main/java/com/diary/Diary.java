package com.diary;

import java.util.ArrayList;

/**
 * Diary class that represents a diary entry with date, title, contentand author. 
 */
public class Diary {
  private String date;
  private String title;
  private String content;
  private ArrayList<Author> authors;

  /**
   * Constructor for Diary class.
   */
  public Diary(String date, String title, String content) {
    this.date = date;
    this.title = title;
    this.content = content;
    this.authors = new ArrayList<>();
  }

  /**
   * Adds an author to the diary entry.
   */
  public void addAuthor(Author author) {
    if (author == null) {
      return;
    }
    authors.add(author);
  }

  /**
   * Adds an author to the diary entry by name.
   */
  public void addAuthor(String name) {
    if (name == null || name.isBlank()) {
      return;
    }
    authors.add(new Author(name));
  }

  public String getDate() {
    return date;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public ArrayList<Author> getAuthors() {
    return authors;
  }

  /**
   * Returns the diary entry as string.
   */
  public String toString() {
    StringBuilder text = new StringBuilder();
    text.append("Date: ").append(date).append("\n")
        .append("Title: ").append(title).append("\n")
        .append("Content: ").append(content).append("\n")
        .append("Author: ");
    for (Author a : authors) {
      text.append(a.getName()).append(" ");
    }
    return text.toString().trim() + "\n";
    }
}

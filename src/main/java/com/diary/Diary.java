package com.diary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Diary class that represents a diary entry with date, title, contentand author. 
 */
public class Diary {
  private final LocalDateTime date;
  private final String title;
  private final String content;
  private final ArrayList<Author> authors;

  private static final DateTimeFormatter f =
       DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  /**
   * Constructor for Diary class.
   */
  public Diary(String date, String title, String content) {
    this.date = LocalDateTime.parse(date, f);
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
  public void addAuthorByName(String name) { // 
    if (name == null || name.isBlank()) {
      return;
    }
    authors.add(new Author(name));
  }

  public LocalDateTime getDate() {
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
  @Override 
  public String toString() {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    StringBuilder text = new StringBuilder();
    text.append("Date and time: ").append(date.format(f)).append("\n")
        .append("Title: ").append(title).append("\n")
        .append("Content: ").append(content).append("\n")
        .append("Author: ");
    for (Author a : authors) {
      text.append(a.getName()).append(" ");
    }
    return text.toString().trim() + "\n";
  }
}

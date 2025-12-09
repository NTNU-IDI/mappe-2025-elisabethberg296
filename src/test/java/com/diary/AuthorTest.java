package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * AuthorTest class to test the Author class.
 */
public class AuthorTest {
    
  @Test
  public void testAuthorCreation() {
    Author author = new Author("Bernt");
    assertEquals("Bernt", author.getName());
  }
    
  @Test
  public void testAuthorToString() {
    Author author = new Author("Bob");
    assertEquals("Bob", author.toString());
  }

  @Test
  public void testAddAuthorByNameBlank() {
    Diary diary = new Diary("2025-12-08 10:00", "Title", "Content");
    diary.addAuthorByName(""); 
    diary.addAuthorByName(null); 
    assertEquals(0, diary.getAuthors().size());
  }
}

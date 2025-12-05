package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

/**
 * DiaryTest class to test the Diary class.
 */
public class DiaryTest {

  private static final DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
  @Test
  public void testDiaryCreation() {
    Diary diary = new Diary("2025-11-20 10:00", "Test title", "Test entry");
    assertEquals(LocalDateTime.parse("2025-11-20 10:00", f), diary.getDate());
    assertEquals("Test title", diary.getTitle());
    assertEquals("Test entry", diary.getContent());
  }
    
  @Test
  public void testAddAuthorByObject() {
    Diary diary = new Diary("2025-11-20 10:00", "Title",  "Test");
    Author author = new Author("Brad");
    diary.addAuthor(author);
        
    assertTrue(diary.getAuthors().contains(author));
    assertEquals(1, diary.getAuthors().size());
  }
    
  @Test
  public void testAddAuthorByString() {
    Diary diary = new Diary("2025-11-20 10:00", "Title", "Test");
    diary.addAuthorByName("Bart");
        
    assertEquals(1, diary.getAuthors().size());
    assertEquals("Bart", diary.getAuthors().get(0).getName());
  }
    
  @Test
  public void testAddMultipleAuthors() {
    Diary diary = new Diary("2025-11-20 10:00", "Title", "Test");
    diary.addAuthorByName("Bernt");
    diary.addAuthorByName("Bob");
    diary.addAuthor(new Author("Brad"));
        
    assertEquals(3, diary.getAuthors().size());
  }
    
  @Test
  public void testToString() {
    Diary diary = new Diary("2025-11-20 10:00", "Title",  "Entry");
    diary.addAuthorByName("Author1");
    String result = diary.toString();
        
    assertTrue(result.contains("2025-11-20 10:00"));
    assertTrue(result.contains("Title"));
    assertTrue(result.contains("Entry"));
    assertTrue(result.contains("Author1"));
  }
}

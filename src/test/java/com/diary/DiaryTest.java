package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DiaryTest {
    
    @Test
    public void testDiaryCreation() {
        Diary diary = new Diary("2025-11-20", "Test entry");
        assertEquals("2025-11-20", diary.getDate());
        assertEquals("Test entry", diary.getContent());
    }
    
    @Test
    public void testAddAuthorByObject() {
        Diary diary = new Diary("2025-11-20", "Test");
        Author author = new Author("Brad");
        diary.addAuthor(author);
        
        assertTrue(diary.getAuthors().contains(author));
        assertEquals(1, diary.getAuthors().size());
    }
    
    @Test
    public void testAddAuthorByString() {
        Diary diary = new Diary("2025-11-20", "Test");
        diary.addAuthor("Bart");
        
        assertEquals(1, diary.getAuthors().size());
        assertEquals("Bart", diary.getAuthors().get(0).getName());
    }
    
    @Test
    public void testAddMultipleAuthors() {
        Diary diary = new Diary("2025-11-20", "Test");
        diary.addAuthor("Bernt");
        diary.addAuthor("Bob");
        diary.addAuthor(new Author("Brad"));
        
        assertEquals(3, diary.getAuthors().size());
    }
    
    @Test
    public void testToString() {
        Diary diary = new Diary("2025-11-20", "Entry");
        diary.addAuthor("Author1");
        String result = diary.toString();
        
        assertTrue(result.contains("2025-11-20"));
        assertTrue(result.contains("Entry"));
        assertTrue(result.contains("Author1"));
    }
}

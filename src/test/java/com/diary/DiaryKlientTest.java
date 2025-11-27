package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DiaryKlientTest {
    
    @Test
    public void testIntegration() {
        DiaryRegistry registry = new DiaryRegistry();
        Author author = new Author("TestAuthor");
        registry.registerAuthor(author);
        
        Diary diary = new Diary("2025-11-20", "Test entry");
        diary.addAuthor(author);
        registry.registerEntry(diary);
        
        assertEquals(1, registry.getAuthors().size());
        assertEquals(1, registry.getEntries().size());
    }
}

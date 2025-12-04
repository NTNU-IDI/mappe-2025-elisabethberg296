package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryRegistryTest {
    
    private DiaryRegistry registry;
    
    @BeforeEach
    public void setUp() {
        registry = new DiaryRegistry();
    }
    
    @Test
    public void testRegisterAuthor() {
        Author author = new Author("Bernt");
        registry.registerAuthor(author);
        
        assertEquals(1, registry.getAuthors().size());
        assertTrue(registry.getAuthors().contains(author));
    }
    
    @Test
    public void testFindAuthor() {
        Author author = new Author("Bob");
        registry.registerAuthor(author);
        
        Author found = registry.findAuthor("Bob");
        assertNotNull(found);
        assertEquals("Bob", found.getName());
    }
    
    @Test
    public void testFindAuthorNotFound() {
        Author found = registry.findAuthor("NonExistent");
        assertNull(found);
    }
    
    @Test
    public void testRegisterEntry() {
        Diary diary = new Diary("2025-11-20", "Title",  "Test");
        registry.registerEntry(diary);
        
        assertEquals(1, registry.getEntries().size());
        assertTrue(registry.getEntries().contains(diary));
    }
    
    @Test
    public void testDeleteEntry() {
        Diary diary1 = new Diary("2025-11-20", "Title1", "Entry1");
        Diary diary2 = new Diary("2025-11-21", "Title2", "Entry2");
        registry.registerEntry(diary1);
        registry.registerEntry(diary2);
        
        boolean deleted = registry.deleteEntry(0);
        assertTrue(deleted);
        assertEquals(1, registry.getEntries().size());
        assertEquals("Entry2", registry.getEntries().get(0).getContent());
    }
    
    @Test
    public void testDeleteEntryInvalidIndex() {
        boolean deleted = registry.deleteEntry(5);
        assertFalse(deleted);
    }
}

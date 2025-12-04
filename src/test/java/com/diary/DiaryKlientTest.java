package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryKlientTest {

    private DiaryRegistry registry;
    private Author author1;
    private Author author2;

    @BeforeEach
    public void setUp() {
        registry = new DiaryRegistry();
        author1 = new Author("Alice");
        author2 = new Author("Bob");
        registry.registerAuthor(author1);
        registry.registerAuthor(author2);
    }

    @Test
    public void testIntegration() {
        Diary diary = new Diary("2025-11-20", "Test title", "Test entry");
        diary.addAuthor(author1);
        registry.registerEntry(diary);

        assertEquals(1, registry.getAuthors().size());
        assertEquals(1, registry.getEntries().size());
    }

    @Test
    public void testWithoutDateRemovesFirstLine() {
        Diary diary = new Diary("2025-11-20", "Title", "Content");
        diary.addAuthor(author1);
        
        String full = diary.toString();
        String withoutDate = withoutDate(diary);
        
        assertFalse(withoutDate.contains("Date: 2025-11-20"));
        assertTrue(withoutDate.contains("Title"));
        assertTrue(withoutDate.contains("Content"));
    }

    @Test
    public void testFindEntriesByDateSingleEntry() {
        Diary diary1 = new Diary("2025-11-15", "Title1", "Entry on 15th");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-16", "Title2", "Entry on 16th");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            if ("2025-11-15".equals(d.getDate())) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testFindEntriesByDateMultipleEntries() {
        Diary diary1 = new Diary("2025-11-15", "Title1", "Entry 1 on 15th");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-15", "Title2", "Entry 2 on 15th");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-16", "Title3", "Entry on 16th");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            if ("2025-11-15".equals(d.getDate())) {
                count++;
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void testFindEntriesByDateRangeInclusive() {
        Diary diary1 = new Diary("2025-11-10", "Title1", "Entry 1");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-15", "Title2", "Entry 2");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-20", "Title3", "Entry 3");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);
        
        Diary diary4 = new Diary("2025-11-25", "Title4", "Entry 4");
        diary4.addAuthor(author2);
        registry.registerEntry(diary4);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            String dt = d.getDate();
            if (dt.compareTo("2025-11-10") >= 0 && dt.compareTo("2025-11-20") <= 0) {
                count++;
            }
        }
        assertEquals(3, count);
    }

    @Test
    public void testFindEntriesByDateRangeExclusive() {
        Diary diary1 = new Diary("2025-11-09", "Title1", "Entry 1");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-10", "Title2", "Entry 2");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-20", "Title3", "Entry 3");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);
        
        Diary diary4 = new Diary("2025-11-21", "Title4", "Entry 4");
        diary4.addAuthor(author2);
        registry.registerEntry(diary4);

        int countOutside = 0;
        for (Diary d : registry.getEntries()) {
            String dt = d.getDate();
            if (dt.compareTo("2025-11-10") < 0 || dt.compareTo("2025-11-20") > 0) {
                countOutside++;
            }
        }
        assertEquals(2, countOutside);
    }

    private static String withoutDate(Diary d) {
        String s = d.toString();
        int nl = s.indexOf('\n');
        return (nl >= 0) ? s.substring(nl + 1).trim() : s.trim();
    }
}

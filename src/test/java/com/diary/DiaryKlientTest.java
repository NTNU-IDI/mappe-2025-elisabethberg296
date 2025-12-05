package com.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryKlientTest {

  private DiaryRegistry registry;
  private Author author1;
  private Author author2;
  private static final DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        Diary diary = new Diary("2025-11-20 00:00", "Test title", "Test entry");
        diary.addAuthor(author1);
        registry.registerEntry(diary);

        assertEquals(1, registry.getEntries().size());
    }

    @Test
    public void testWithoutDateRemovesFirstLine() {
        Diary diary = new Diary("2025-11-20 00:00", "Title", "Content");
        diary.addAuthor(author1);
        
        String full = diary.toString();
        String withoutDate = withoutDate(diary);
        
        assertFalse(withoutDate.contains("Date: 2025-11-20 00:00"));
        assertTrue(withoutDate.contains("Title"));
        assertTrue(withoutDate.contains("Content"));
    }

    @Test
    public void testFindEntriesByDateSingleEntry() {
        Diary diary1 = new Diary("2025-11-15 12:00", "Title1", "Entry on 15th");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-16 20:00", "Title2", "Entry on 16th");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            if (d.getDate().toLocalDate().equals(LocalDate.parse("2025-11-15"))) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testFindEntriesByDateMultipleEntries() {
        Diary diary1 = new Diary("2025-11-15 20:00", "Title1", "Entry 1 on 15th");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-15 00:00", "Title2", "Entry 2 on 15th");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-16 10:00", "Title3", "Entry 3 on 16th");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            if (d.getDate().toLocalDate().equals(LocalDate.parse("2025-11-15"))) {
                count++;
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void testFindEntriesByDateRangeInclusive() {
        Diary diary1 = new Diary("2025-11-10 00:00", "Title1", "Entry 1");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-15 01:00", "Title2", "Entry 2");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-20 02:00", "Title3", "Entry 3");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);
        
        Diary diary4 = new Diary("2025-11-25 03:00", "Title4", "Entry 4");
        diary4.addAuthor(author2);
        registry.registerEntry(diary4);

        int count = 0;
        for (Diary d : registry.getEntries()) {
            LocalDateTime dt = d.getDate();
            if (!dt.isBefore(LocalDateTime.parse("2025-11-10 00:00", f)) &&
                !dt.isAfter(LocalDateTime.parse("2025-11-20 23:59", f))) {
                count++;
            }
        }
        assertEquals(3, count);
    }

    @Test
    public void testFindEntriesByDateRangeExclusive() {
        Diary diary1 = new Diary("2025-11-09 10:00", "Title1", "Entry 1");
        diary1.addAuthor(author1);
        registry.registerEntry(diary1);
        
        Diary diary2 = new Diary("2025-11-10 10:00", "Title2", "Entry 2");
        diary2.addAuthor(author2);
        registry.registerEntry(diary2);
        
        Diary diary3 = new Diary("2025-11-20 10:00", "Title3", "Entry 3");
        diary3.addAuthor(author1);
        registry.registerEntry(diary3);
        
        Diary diary4 = new Diary("2025-11-21 10:00", "Title4", "Entry 4");
        diary4.addAuthor(author2);
        registry.registerEntry(diary4);

        int countOutside = 0;
        for (Diary d : registry.getEntries()) {
            LocalDateTime dt = d.getDate();
            if (dt.isBefore(LocalDateTime.parse("2025-11-10 10:00", f)) ||
                dt.isAfter(LocalDateTime.parse("2025-11-20 10:00", f))) {
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

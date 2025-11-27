package com.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AuthorTest {
    
    @Test
    public void testAuthorCreation() {
        Author author = new Author("Bernt");
        assertEquals("Bernt", author.getName());
    }
    
    @Test
    public void testAuthorToString() {
        Author author = new Author("Bob");
        assertEquals("Author: Bob", author.toString());
    }
}

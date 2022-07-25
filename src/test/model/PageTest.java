package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// tests for class Page and its methods
public class PageTest {
    Page p;

    @BeforeEach
    public void setUp() {
        p = new Page("title", "text");
    }

    @Test
    public void testPageCreation() {
        assertEquals(p.getTitle(), "title");
        assertEquals(p.getBody(), "text");
    }

    @Test
    public void testRename() {
        p.rename("rename");

        assertEquals(p.getTitle(), "rename");
        assertEquals(p.getBody(), "text");
    }

    @Test
    public void testAddText() {
        p.addText("and more text");

        assertEquals(p.getTitle(), "title");
        assertEquals(p.getBody(), "text and more text");
    }

    @Test
    public void testReplaceText() {
        p.replaceText("bye bye");

        assertEquals(p.getTitle(), "title");
        assertEquals(p.getBody(), "bye bye");
    }

    @Test
    public void testAll() {
        p.rename("retitle");
        p.addText("text text");

        assertEquals(p.getTitle(), "retitle");
        assertEquals(p.getBody(), "text text text");

        p.rename("different title");
        assertEquals(p.getTitle(), "different title");
        assertEquals(p.getBody(), "text text text");

        p.addText("text");
        assertEquals(p.getTitle(), "different title");
        assertEquals(p.getBody(), "text text text text");

        p.replaceText("no more text");
        p.addText("actually maybe some more text");
        assertEquals(p.getTitle(), "different title");
        assertEquals(p.getBody(), "no more text actually maybe some more text");
    }
}

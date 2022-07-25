package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// tests for class Character and its methods
public class CharacterTest {
    Character c;

    @BeforeEach
    public void setUp() {
        c = new Character("name", "description");
    }

    @Test
    public void testCharaCreation() {
        assertEquals(c.getName(), "name");
        assertEquals(c.getDesc(), "description");
    }

    @Test
    public void testRename() {
        c.rename("new name");

        assertEquals(c.getName(), "new name");
        assertEquals(c.getDesc(), "description");
    }

    @Test
    public void testAddMoreDesc() {
        c.addDesc("and some more description");

        assertEquals(c.getName(), "name");
        assertEquals(c.getDesc(), "description and some more description");
    }

    @Test
    public void testReplaceDesc() {
        c.replaceDesc("see ya");

        assertEquals(c.getName(), "name");
        assertEquals(c.getDesc(), "see ya");
    }

    @Test
    public void testAll() {
        c.rename("rebrand");
        c.addDesc("description description");

        assertEquals(c.getName(), "rebrand");
        assertEquals(c.getDesc(), "description description description");

        c.rename("different name");
        assertEquals(c.getName(), "different name");
        assertEquals(c.getDesc(), "description description description");

        c.addDesc("description");
        assertEquals(c.getName(), "different name");
        assertEquals(c.getDesc(), "description description description description");

        c.replaceDesc("no more description");
        c.addDesc("actually maybe some more description");
        assertEquals(c.getName(), "different name");
        assertEquals(c.getDesc(), "no more description actually maybe some more description");
    }
}

package model;

import exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

// tests for class WorldTest and its methods
public class WorldTest {
    World w;

    @BeforeEach
    public void setUp() {
        w = new World("name");
    }

    @Test
    public void testWorldCreation() {
        assertEquals(w.getName(), "name");
        assertTrue(w.getCharaLib().isEmpty());
        assertTrue(w.getDetailsLib().isEmpty());
        assertTrue(w.getStoryLib().isEmpty());
    }

    @Test
    public void testRename() {
        w.rename("new name");
        assertEquals(w.getName(), "new name");
        assertTrue(w.getCharaLib().isEmpty());
        assertTrue(w.getDetailsLib().isEmpty());
        assertTrue(w.getStoryLib().isEmpty());
    }

    @Test
    public void testRenameMultiple() {
        w.rename("new name");
        w.rename("new new name");
        assertEquals(w.getName(), "new new name");

        w.rename("even newer name");
        assertEquals(w.getName(), "even newer name");
    }
}

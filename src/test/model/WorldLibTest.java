package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// tests for class WorldLib and its methods
public class WorldLibTest {
    WorldLib worlds;
    World w1;
    World w2;
    World w3;

    @BeforeEach
    public void setUp() {
        worlds = new WorldLib();
        w1 = new World("w1");
        w2 = new World("w2");
        w3 = new World("w3");
    }

    @Test
    public void testLibCreation() {
        assertTrue(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 0);
        try {
            worlds.getWorld("anything");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.removeWorld("anything");
            fail();
        } catch (NotFoundException nF) {
        }
        assertFalse(worlds.isWorldInLib("anything"));
        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
    }

    @Test
    public void testOneWorld() {
        try {
            assertTrue(worlds.addWorld(w1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 1);
        assertTrue(worlds.isWorldInLib("w1"));
        try {
            assertEquals(worlds.getWorld("w1"), w1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w1");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            worlds.removeWorld("w1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 0);
        assertFalse(worlds.isWorldInLib("w1"));
        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.removeWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testTwoWorlds() {
        try {
            assertTrue(worlds.addWorld(w1));
            assertTrue(worlds.addWorld(w2));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 2);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertEquals(worlds.getWorldList(), "w1, w2");
        assertEquals(worlds.getWorldArrayList().size(), 2);
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            worlds.removeWorld("w1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 1);
        assertFalse(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(worlds.getWorld("w2"), w2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w2");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            worlds.removeWorld("w2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 0);
        assertFalse(worlds.isWorldInLib("w1"));
        assertFalse(worlds.isWorldInLib("w2"));
        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.getWorld("w2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.removeWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.removeWorld("w2");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreeWorlds() {
        try {
            assertTrue(worlds.addWorld(w1));
            assertTrue(worlds.addWorld(w2));
            assertTrue(worlds.addWorld(w3));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 3);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        assertEquals(worlds.getWorldList(), "w1, w2, w3");
        assertEquals(worlds.getWorldArrayList().size(), 3);
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
            assertEquals(worlds.getWorld("w3"), w3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            worlds.removeWorld("w1");
            worlds.removeWorld("w2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 1);
        assertFalse(worlds.isWorldInLib("w1"));
        assertFalse(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.getWorld("w2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(worlds.getWorld("w3"), w3);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w3");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            worlds.removeWorld("w3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 0);
        assertFalse(worlds.isWorldInLib("w1"));
        assertFalse(worlds.isWorldInLib("w2"));
        assertFalse(worlds.isWorldInLib("w3"));
        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.getWorld("w2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.getWorld("w3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.removeWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.removeWorld("w2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.removeWorld("w3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreeWorldsDiffOrder() {
        try {
            assertTrue(worlds.addWorld(w2));
            assertTrue(worlds.addWorld(w3));
            assertTrue(worlds.addWorld(w1));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 3);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        assertEquals(worlds.getWorldList(), "w2, w3, w1");
        assertEquals(worlds.getWorldArrayList().size(), 3);
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
            assertEquals(worlds.getWorld("w3"), w3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            worlds.removeWorld("w3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 2);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertFalse(worlds.isWorldInLib("w3"));
        try {
            worlds.getWorld("w3");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(worlds.getWorld("w1"), w1);
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            assertEquals(worlds.getWorld("w2"), w2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w2, w1");
        assertEquals(worlds.getWorldArrayList().size(), 2);

        try {
            worlds.removeWorld("w1");
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            worlds.removeWorld("w2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 0);
        assertFalse(worlds.isWorldInLib("w1"));
        assertFalse(worlds.isWorldInLib("w2"));
        assertFalse(worlds.isWorldInLib("w3"));
        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.getWorld("w2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.getWorld("w3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            worlds.removeWorld("w1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.removeWorld("w2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            worlds.removeWorld("w3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testSameWorld() {
        try {
            assertTrue(worlds.addWorld(w1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            worlds.addWorld(w1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            worlds.addWorld(w1);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 1);
        assertTrue(worlds.isWorldInLib("w1"));
        try {
            assertEquals(worlds.getWorld("w1"), w1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w1");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            assertTrue(worlds.addWorld(w2));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            worlds.addWorld(w2);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            worlds.addWorld(w1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        assertFalse(worlds.isEmpty());
        assertFalse(worlds.isFull());
        assertEquals(worlds.length(), 2);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "w1, w2");
        assertEquals(worlds.getWorldArrayList().size(), 2);
    }

    @Test
    public void testFullList() {
        World w4 = new World("w4");
        World w5 = new World("w5");
        World w6 = new World("w6");
        World w7 = new World("w7");
        World w8 = new World("w8");
        World w9 = new World("w9");

        try {
            assertTrue(worlds.addWorld(w1));
            assertTrue(worlds.addWorld(w2));
            assertTrue(worlds.addWorld(w3));
            assertTrue(worlds.addWorld(w4));
            assertTrue(worlds.addWorld(w5));
            assertTrue(worlds.addWorld(w6));
            assertTrue(worlds.addWorld(w7));
            assertTrue(worlds.addWorld(w8));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertTrue(worlds.isFull());
        assertEquals(worlds.length(), 8);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        assertTrue(worlds.isWorldInLib("w4"));
        assertTrue(worlds.isWorldInLib("w5"));
        assertTrue(worlds.isWorldInLib("w6"));
        assertTrue(worlds.isWorldInLib("w7"));
        assertTrue(worlds.isWorldInLib("w8"));
        assertEquals(worlds.getWorldList(), "w1, w2, w3, w4, w5, w6, w7, w8");
        assertEquals(worlds.getWorldArrayList().size(), 8);
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
            assertEquals(worlds.getWorld("w3"), w3);
            assertEquals(worlds.getWorld("w4"), w4);
            assertEquals(worlds.getWorld("w5"), w5);
            assertEquals(worlds.getWorld("w6"), w6);
            assertEquals(worlds.getWorld("w7"), w7);
            assertEquals(worlds.getWorld("w8"), w8);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            assertFalse(worlds.addWorld(w9));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertTrue(worlds.isFull());
        assertEquals(worlds.length(), 8);
        assertTrue(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        assertTrue(worlds.isWorldInLib("w4"));
        assertTrue(worlds.isWorldInLib("w5"));
        assertTrue(worlds.isWorldInLib("w6"));
        assertTrue(worlds.isWorldInLib("w7"));
        assertTrue(worlds.isWorldInLib("w8"));
        assertEquals(worlds.getWorldList(), "w1, w2, w3, w4, w5, w6, w7, w8");
        assertEquals(worlds.getWorldArrayList().size(), 8);
        try {
            assertEquals(worlds.getWorld("w1"), w1);
            assertEquals(worlds.getWorld("w2"), w2);
            assertEquals(worlds.getWorld("w3"), w3);
            assertEquals(worlds.getWorld("w4"), w4);
            assertEquals(worlds.getWorld("w5"), w5);
            assertEquals(worlds.getWorld("w6"), w6);
            assertEquals(worlds.getWorld("w7"), w7);
            assertEquals(worlds.getWorld("w8"), w8);
        } catch (NotFoundException nfe) {
            fail();
        }
        assertFalse(worlds.isWorldInLib("w9"));
        try {
            worlds.getWorld("w9");
            fail();
        } catch (NotFoundException nfe) {
        }

        try {
            worlds.removeWorld("w1");
        } catch (NotFoundException nF) {
            fail();
        }

        try {
            assertTrue(worlds.addWorld(w9));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(worlds.isEmpty());
        assertTrue(worlds.isFull());
        assertEquals(worlds.length(), 8);
        assertFalse(worlds.isWorldInLib("w1"));
        assertTrue(worlds.isWorldInLib("w2"));
        assertTrue(worlds.isWorldInLib("w3"));
        assertTrue(worlds.isWorldInLib("w4"));
        assertTrue(worlds.isWorldInLib("w5"));
        assertTrue(worlds.isWorldInLib("w6"));
        assertTrue(worlds.isWorldInLib("w7"));
        assertTrue(worlds.isWorldInLib("w8"));
        assertTrue(worlds.isWorldInLib("w9"));
        assertEquals(worlds.getWorldList(), "w2, w3, w4, w5, w6, w7, w8, w9");
        assertEquals(worlds.getWorldArrayList().size(), 8);
        try {
            assertEquals(worlds.getWorld("w2"), w2);
            assertEquals(worlds.getWorld("w3"), w3);
            assertEquals(worlds.getWorld("w4"), w4);
            assertEquals(worlds.getWorld("w5"), w5);
            assertEquals(worlds.getWorld("w6"), w6);
            assertEquals(worlds.getWorld("w7"), w7);
            assertEquals(worlds.getWorld("w8"), w8);
            assertEquals(worlds.getWorld("w9"), w9);
        } catch (NotFoundException nfe) {
            fail();
        }
        try {
            worlds.getWorld("w1");
            fail();
        } catch (NotFoundException nfe) {
        }
    }

    @Test
    void testCaseInsensitive() {
        worlds = new WorldLib();
        w1 = new World("Ahhh");
        w2 = new World("AHHH");
        w3 = new World("ahhh");

        try {
            assertTrue(worlds.addWorld(w1));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertTrue(worlds.isWorldInLib("Ahhh"));
        assertTrue(worlds.isWorldInLib("AHHH"));
        assertTrue(worlds.isWorldInLib("ahhh"));
        assertTrue(worlds.isWorldInLib("aHhH"));

        assertFalse(worlds.isWorldInLib("a h h h"));
        assertFalse(worlds.isWorldInLib("Ahh"));
        assertFalse(worlds.isWorldInLib("Ahhhh"));

        try {
            assertEquals(worlds.getWorld("Ahhh"), w1);
            assertEquals(worlds.getWorld("AHHH"), w1);
            assertEquals(worlds.getWorld("ahhh"), w1);
            assertEquals(worlds.getWorld("aHhH"), w1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(worlds.getWorldList(), "Ahhh");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            worlds.addWorld(w2);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        try {
            worlds.addWorld(w3);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        assertEquals(worlds.getWorldList(), "Ahhh");
        assertEquals(worlds.getWorldArrayList().size(), 1);

        try {
            worlds.removeWorld("ahhh");
        } catch (NotFoundException nF) {
            fail();
        }

        assertEquals(worlds.getWorldList(), "");
        assertEquals(worlds.getWorldArrayList().size(), 0);
    }
}

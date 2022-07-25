package model;

import exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// tests for class CharacterLib and its methods
public class CharacterLibTest {
    CharacterLib charas;
    Character c1;
    Character c2;
    Character c3;

    @BeforeEach
    public void setUp() {
        charas = new CharacterLib();
        c1 = new Character("c1", "d1");
        c2 = new Character("c2", "d2");
        c3 = new Character("c3", "d3");
    }

    @Test
    public void testLibCreation() {
        assertTrue(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 0);
        try {
            charas.getChara("anyone");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.removeChara("anyone");
            fail();
        } catch (NotFoundException nF) {
        }
        assertFalse(charas.isCharaInLib("anyone"));
        assertEquals(charas.getCharaList(), "");
    }

    @Test
    public void testOneChara() {
        try {
            assertTrue(charas.addCharas(c1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 1);
        assertTrue(charas.isCharaInLib("c1"));
        try {
            assertEquals(charas.getChara("c1"), c1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c1");

        try {
            charas.removeChara("c1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 0);
        assertFalse(charas.isCharaInLib("c1"));
        assertEquals(charas.getCharaList(), "");
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.removeChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testTwoCharas() {
        try {
            assertTrue(charas.addCharas(c1));
            assertTrue(charas.addCharas(c2));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 2);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertEquals(charas.getCharaList(), "c1, c2");
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            charas.removeChara("c1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 1);
        assertFalse(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(charas.getChara("c2"), c2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c2");

        try {
            charas.removeChara("c2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 0);
        assertFalse(charas.isCharaInLib("c1"));
        assertFalse(charas.isCharaInLib("c2"));
        assertEquals(charas.getCharaList(), "");
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.getChara("c2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.removeChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.removeChara("c2");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreeCharas() {
        try {
            assertTrue(charas.addCharas(c1));
            assertTrue(charas.addCharas(c2));
            assertTrue(charas.addCharas(c3));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 3);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        assertEquals(charas.getCharaList(), "c1, c2, c3");
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
            assertEquals(charas.getChara("c3"), c3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            charas.removeChara("c1");
            charas.removeChara("c2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 1);
        assertFalse(charas.isCharaInLib("c1"));
        assertFalse(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.getChara("c2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(charas.getChara("c3"), c3);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c3");

        try {
            charas.removeChara("c3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 0);
        assertFalse(charas.isCharaInLib("c1"));
        assertFalse(charas.isCharaInLib("c2"));
        assertFalse(charas.isCharaInLib("c3"));
        assertEquals(charas.getCharaList(), "");
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.getChara("c2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.getChara("c3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.removeChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.removeChara("c2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.removeChara("c3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreeCharasDiffOrder() {
        try {
            assertTrue(charas.addCharas(c2));
            assertTrue(charas.addCharas(c3));
            assertTrue(charas.addCharas(c1));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 3);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        assertEquals(charas.getCharaList(), "c2, c3, c1");
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
            assertEquals(charas.getChara("c3"), c3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            charas.removeChara("c3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 2);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertFalse(charas.isCharaInLib("c3"));
        try {
            charas.getChara("c3");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(charas.getChara("c1"), c1);
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            assertEquals(charas.getChara("c2"), c2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c2, c1");

        try {
            charas.removeChara("c1");
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            charas.removeChara("c2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 0);
        assertFalse(charas.isCharaInLib("c1"));
        assertFalse(charas.isCharaInLib("c2"));
        assertFalse(charas.isCharaInLib("c3"));
        assertEquals(charas.getCharaList(), "");
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.getChara("c2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.getChara("c3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            charas.removeChara("c1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.removeChara("c2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            charas.removeChara("c3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testSameChara() {
        try {
            assertTrue(charas.addCharas(c1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            charas.addCharas(c1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            charas.addCharas(c1);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 1);
        assertTrue(charas.isCharaInLib("c1"));
        try {
            assertEquals(charas.getChara("c1"), c1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c1");

        try {
            assertTrue(charas.addCharas(c2));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            charas.addCharas(c2);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            charas.addCharas(c1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        assertFalse(charas.isEmpty());
        assertFalse(charas.isFull());
        assertEquals(charas.length(), 2);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(charas.getCharaList(), "c1, c2");
    }

    @Test
    public void testFullList() {
        Character c4 = new Character("c4", "d4");
        Character c5 = new Character("c5", "d5");
        Character c6 = new Character("c6", "d6");
        Character c7 = new Character("c7", "d7");
        Character c8 = new Character("c8", "d8");
        Character c9 = new Character("c9", "d9");
        Character c10 = new Character("c10", "d10");
        Character c11 = new Character("c11", "d11");

        try {
            assertTrue(charas.addCharas(c1));
            assertTrue(charas.addCharas(c2));
            assertTrue(charas.addCharas(c3));
            assertTrue(charas.addCharas(c4));
            assertTrue(charas.addCharas(c5));
            assertTrue(charas.addCharas(c6));
            assertTrue(charas.addCharas(c7));
            assertTrue(charas.addCharas(c8));
            assertTrue(charas.addCharas(c9));
            assertTrue(charas.addCharas(c10));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertTrue(charas.isFull());
        assertEquals(charas.length(), 10);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        assertTrue(charas.isCharaInLib("c4"));
        assertTrue(charas.isCharaInLib("c5"));
        assertTrue(charas.isCharaInLib("c6"));
        assertTrue(charas.isCharaInLib("c7"));
        assertTrue(charas.isCharaInLib("c8"));
        assertTrue(charas.isCharaInLib("c9"));
        assertTrue(charas.isCharaInLib("c10"));
        assertEquals(charas.getCharaList(), "c1, c2, c3, c4, c5, c6, c7, c8, c9, c10");
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
            assertEquals(charas.getChara("c3"), c3);
            assertEquals(charas.getChara("c4"), c4);
            assertEquals(charas.getChara("c5"), c5);
            assertEquals(charas.getChara("c6"), c6);
            assertEquals(charas.getChara("c7"), c7);
            assertEquals(charas.getChara("c8"), c8);
            assertEquals(charas.getChara("c9"), c9);
            assertEquals(charas.getChara("c10"), c10);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            assertFalse(charas.addCharas(c11));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertTrue(charas.isFull());
        assertEquals(charas.length(), 10);
        assertTrue(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        assertTrue(charas.isCharaInLib("c4"));
        assertTrue(charas.isCharaInLib("c5"));
        assertTrue(charas.isCharaInLib("c6"));
        assertTrue(charas.isCharaInLib("c7"));
        assertTrue(charas.isCharaInLib("c8"));
        assertTrue(charas.isCharaInLib("c9"));
        assertTrue(charas.isCharaInLib("c10"));
        assertEquals(charas.getCharaList(), "c1, c2, c3, c4, c5, c6, c7, c8, c9, c10");
        try {
            assertEquals(charas.getChara("c1"), c1);
            assertEquals(charas.getChara("c2"), c2);
            assertEquals(charas.getChara("c3"), c3);
            assertEquals(charas.getChara("c4"), c4);
            assertEquals(charas.getChara("c5"), c5);
            assertEquals(charas.getChara("c6"), c6);
            assertEquals(charas.getChara("c7"), c7);
            assertEquals(charas.getChara("c8"), c8);
            assertEquals(charas.getChara("c9"), c9);
            assertEquals(charas.getChara("c10"), c10);
        } catch (NotFoundException nfe) {
            fail();
        }
        assertFalse(charas.isCharaInLib("c11"));
        try {
            charas.getChara("c11");
            fail();
        } catch (NotFoundException nfe) {
        }

        try {
            charas.removeChara("c1");
        } catch (NotFoundException nF) {
            fail();
        }

        try {
            assertTrue(charas.addCharas(c11));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(charas.isEmpty());
        assertTrue(charas.isFull());
        assertEquals(charas.length(), 10);
        assertFalse(charas.isCharaInLib("c1"));
        assertTrue(charas.isCharaInLib("c2"));
        assertTrue(charas.isCharaInLib("c3"));
        assertTrue(charas.isCharaInLib("c4"));
        assertTrue(charas.isCharaInLib("c5"));
        assertTrue(charas.isCharaInLib("c6"));
        assertTrue(charas.isCharaInLib("c7"));
        assertTrue(charas.isCharaInLib("c8"));
        assertTrue(charas.isCharaInLib("c9"));
        assertTrue(charas.isCharaInLib("c10"));
        assertTrue(charas.isCharaInLib("c11"));
        assertEquals(charas.getCharaList(), "c2, c3, c4, c5, c6, c7, c8, c9, c10, c11");
        try {
            assertEquals(charas.getChara("c11"), c11);
            assertEquals(charas.getChara("c2"), c2);
            assertEquals(charas.getChara("c3"), c3);
            assertEquals(charas.getChara("c4"), c4);
            assertEquals(charas.getChara("c5"), c5);
            assertEquals(charas.getChara("c6"), c6);
            assertEquals(charas.getChara("c7"), c7);
            assertEquals(charas.getChara("c8"), c8);
            assertEquals(charas.getChara("c9"), c9);
            assertEquals(charas.getChara("c10"), c10);
        } catch (NotFoundException nfe) {
            fail();
        }
        try {
            charas.getChara("c1");
            fail();
        } catch (NotFoundException nfe) {
        }
    }
}

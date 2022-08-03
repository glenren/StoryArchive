package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// tests for class PageLib and its methods
public class PageLibTest {
    PageLib pages;
    Page p1;
    Page p2;
    Page p3;

    @BeforeEach
    public void setUp() {
        pages = new PageLib();
        p1 = new Page("p1", "d1");
        p2 = new Page("p2", "d2");
        p3 = new Page("p3", "d3");
    }

    @Test
    public void testLibCreation() {
        assertTrue(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 0);
        try {
            pages.getPage("anything");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.removePage("anything");
            fail();
        } catch (NotFoundException nF) {
        }
        assertFalse(pages.isPageInLib("anything"));
        assertEquals(pages.getPageList(), "");
    }

    @Test
    public void testOnePage() {
        try {
            assertTrue(pages.addPage(p1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 1);
        assertTrue(pages.isPageInLib("p1"));
        try {
            assertEquals(pages.getPage("p1"), p1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p1");

        try {
            pages.removePage("p1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 0);
        assertFalse(pages.isPageInLib("p1"));
        assertEquals(pages.getPageList(), "");
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.removePage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testTwoPages() {
        try {
            assertTrue(pages.addPage(p1));
            assertTrue(pages.addPage(p2));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 2);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertEquals(pages.getPageList(), "p1, p2");
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            pages.removePage("p1");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 1);
        assertFalse(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(pages.getPage("p2"), p2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p2");

        try {
            pages.removePage("p2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 0);
        assertFalse(pages.isPageInLib("p1"));
        assertFalse(pages.isPageInLib("p2"));
        assertEquals(pages.getPageList(), "");
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.getPage("p2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.removePage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.removePage("p2");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreePages() {
        try {
            assertTrue(pages.addPage(p1));
            assertTrue(pages.addPage(p2));
            assertTrue(pages.addPage(p3));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 3);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        assertEquals(pages.getPageList(), "p1, p2, p3");
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
            assertEquals(pages.getPage("p3"), p3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            pages.removePage("p1");
            pages.removePage("p2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 1);
        assertFalse(pages.isPageInLib("p1"));
        assertFalse(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.getPage("p2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(pages.getPage("p3"), p3);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p3");

        try {
            pages.removePage("p3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 0);
        assertFalse(pages.isPageInLib("p1"));
        assertFalse(pages.isPageInLib("p2"));
        assertFalse(pages.isPageInLib("p3"));
        assertEquals(pages.getPageList(), "");
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.getPage("p2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.getPage("p3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.removePage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.removePage("p2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.removePage("p3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testThreePagesDiffOrder() {
        try {
            assertTrue(pages.addPage(p2));
            assertTrue(pages.addPage(p3));
            assertTrue(pages.addPage(p1));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 3);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        assertEquals(pages.getPageList(), "p2, p3, p1");
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
            assertEquals(pages.getPage("p3"), p3);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            pages.removePage("p3");
        } catch (NotFoundException nF) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 2);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertFalse(pages.isPageInLib("p3"));
        try {
            pages.getPage("p3");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            assertEquals(pages.getPage("p1"), p1);
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            assertEquals(pages.getPage("p2"), p2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p2, p1");

        try {
            pages.removePage("p1");
        } catch (NotFoundException nF) {
            fail();
        }
        try {
            pages.removePage("p2");
        } catch (NotFoundException nF) {
            fail();
        }

        assertTrue(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 0);
        assertFalse(pages.isPageInLib("p1"));
        assertFalse(pages.isPageInLib("p2"));
        assertFalse(pages.isPageInLib("p3"));
        assertEquals(pages.getPageList(), "");
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.getPage("p2");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.getPage("p3");
            fail();
        } catch (NotFoundException nfe) {
        }
        try {
            pages.removePage("p1");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.removePage("p2");
            fail();
        } catch (NotFoundException nF) {
        }
        try {
            pages.removePage("p3");
            fail();
        } catch (NotFoundException nF) {
        }
    }

    @Test
    public void testSamePage() {
        try {
            assertTrue(pages.addPage(p1));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            pages.addPage(p1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            pages.addPage(p1);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 1);
        assertTrue(pages.isPageInLib("p1"));
        try {
            assertEquals(pages.getPage("p1"), p1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p1");

        try {
            assertTrue(pages.addPage(p2));
        } catch (AlreadyExistsException aE) {
            fail();
        }
        try {
            pages.addPage(p2);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        try {
            pages.addPage(p1);
            fail();
        } catch (AlreadyExistsException aE) {
        }
        assertFalse(pages.isEmpty());
        assertFalse(pages.isFull());
        assertEquals(pages.length(), 2);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "p1, p2");
    }

    @Test
    public void testFullList() {
        Page p4 = new Page("p4", "d4");
        Page p5 = new Page("p5", "d5");
        Page p6 = new Page("p6", "d6");
        Page p7 = new Page("p7", "d7");
        Page p8 = new Page("p8", "d8");
        Page p9 = new Page("p9", "d9");
        Page p10 = new Page("p10", "d10");
        Page p11 = new Page("p11", "d11");

        try {
            assertTrue(pages.addPage(p1));
            assertTrue(pages.addPage(p2));
            assertTrue(pages.addPage(p3));
            assertTrue(pages.addPage(p4));
            assertTrue(pages.addPage(p5));
            assertTrue(pages.addPage(p6));
            assertTrue(pages.addPage(p7));
            assertTrue(pages.addPage(p8));
            assertTrue(pages.addPage(p9));
            assertTrue(pages.addPage(p10));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertTrue(pages.isFull());
        assertEquals(pages.length(), 10);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        assertTrue(pages.isPageInLib("p4"));
        assertTrue(pages.isPageInLib("p5"));
        assertTrue(pages.isPageInLib("p6"));
        assertTrue(pages.isPageInLib("p7"));
        assertTrue(pages.isPageInLib("p8"));
        assertTrue(pages.isPageInLib("p9"));
        assertTrue(pages.isPageInLib("p10"));
        assertEquals(pages.getPageList(), "p1, p2, p3, p4, p5, p6, p7, p8, p9, p10");
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
            assertEquals(pages.getPage("p3"), p3);
            assertEquals(pages.getPage("p4"), p4);
            assertEquals(pages.getPage("p5"), p5);
            assertEquals(pages.getPage("p6"), p6);
            assertEquals(pages.getPage("p7"), p7);
            assertEquals(pages.getPage("p8"), p8);
            assertEquals(pages.getPage("p9"), p9);
            assertEquals(pages.getPage("p10"), p10);
        } catch (NotFoundException nfe) {
            fail();
        }

        try {
            assertFalse(pages.addPage(p11));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertTrue(pages.isFull());
        assertEquals(pages.length(), 10);
        assertTrue(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        assertTrue(pages.isPageInLib("p4"));
        assertTrue(pages.isPageInLib("p5"));
        assertTrue(pages.isPageInLib("p6"));
        assertTrue(pages.isPageInLib("p7"));
        assertTrue(pages.isPageInLib("p8"));
        assertTrue(pages.isPageInLib("p9"));
        assertTrue(pages.isPageInLib("p10"));
        assertEquals(pages.getPageList(), "p1, p2, p3, p4, p5, p6, p7, p8, p9, p10");
        try {
            assertEquals(pages.getPage("p1"), p1);
            assertEquals(pages.getPage("p2"), p2);
            assertEquals(pages.getPage("p3"), p3);
            assertEquals(pages.getPage("p4"), p4);
            assertEquals(pages.getPage("p5"), p5);
            assertEquals(pages.getPage("p6"), p6);
            assertEquals(pages.getPage("p7"), p7);
            assertEquals(pages.getPage("p8"), p8);
            assertEquals(pages.getPage("p9"), p9);
            assertEquals(pages.getPage("p10"), p10);
        } catch (NotFoundException nfe) {
            fail();
        }
        assertFalse(pages.isPageInLib("p11"));
        try {
            pages.getPage("p11");
            fail();
        } catch (NotFoundException nfe) {
        }

        try {
            pages.removePage("p1");
        } catch (NotFoundException nF) {
            fail();
        }

        try {
            assertTrue(pages.addPage(p11));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertFalse(pages.isEmpty());
        assertTrue(pages.isFull());
        assertEquals(pages.length(), 10);
        assertFalse(pages.isPageInLib("p1"));
        assertTrue(pages.isPageInLib("p2"));
        assertTrue(pages.isPageInLib("p3"));
        assertTrue(pages.isPageInLib("p4"));
        assertTrue(pages.isPageInLib("p5"));
        assertTrue(pages.isPageInLib("p6"));
        assertTrue(pages.isPageInLib("p7"));
        assertTrue(pages.isPageInLib("p8"));
        assertTrue(pages.isPageInLib("p9"));
        assertTrue(pages.isPageInLib("p10"));
        assertTrue(pages.isPageInLib("p11"));
        assertEquals(pages.getPageList(), "p2, p3, p4, p5, p6, p7, p8, p9, p10, p11");
        try {
            assertEquals(pages.getPage("p11"), p11);
            assertEquals(pages.getPage("p2"), p2);
            assertEquals(pages.getPage("p3"), p3);
            assertEquals(pages.getPage("p4"), p4);
            assertEquals(pages.getPage("p5"), p5);
            assertEquals(pages.getPage("p6"), p6);
            assertEquals(pages.getPage("p7"), p7);
            assertEquals(pages.getPage("p8"), p8);
            assertEquals(pages.getPage("p9"), p9);
            assertEquals(pages.getPage("p10"), p10);
        } catch (NotFoundException nfe) {
            fail();
        }
        try {
            pages.getPage("p1");
            fail();
        } catch (NotFoundException nfe) {
        }
    }

    @Test
    void testCaseInsensitive() {
        pages = new PageLib();
        p1 = new Page("Ahhh", "p1");
        p2 = new Page("AHHH", "p2");
        p3 = new Page("ahhh", "p3");

        try {
            assertTrue(pages.addPage(p1));
        } catch (AlreadyExistsException aE) {
            fail();
        }

        assertTrue(pages.isPageInLib("Ahhh"));
        assertTrue(pages.isPageInLib("AHHH"));
        assertTrue(pages.isPageInLib("ahhh"));
        assertTrue(pages.isPageInLib("aHhH"));

        assertFalse(pages.isPageInLib("a h h h"));
        assertFalse(pages.isPageInLib("Ahh"));
        assertFalse(pages.isPageInLib("Ahhhh"));

        try {
            assertEquals(pages.getPage("Ahhh"), p1);
            assertEquals(pages.getPage("AHHH"), p1);
            assertEquals(pages.getPage("ahhh"), p1);
            assertEquals(pages.getPage("aHhH"), p1);
        } catch (NotFoundException nF) {
            fail();
        }
        assertEquals(pages.getPageList(), "Ahhh");

        try {
            pages.addPage(p2);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        try {
            pages.addPage(p3);
            fail();
        } catch (AlreadyExistsException aE) {
        }

        assertEquals(pages.getPageList(), "Ahhh");

        try {
            pages.removePage("ahhh");
        } catch (NotFoundException nF) {
            fail();
        }

        assertEquals(pages.getPageList(), "");
    }
}

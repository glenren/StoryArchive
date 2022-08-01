package persistence;

import exceptions.NotFoundException;
import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// base code: JsonReaderTest in JSON demo file for project
public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorldLib worlds = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyArchive() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyArchive.json");
        try {
            WorldLib worlds = reader.read();
            assertEquals("", worlds.getWorldList());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderArchiveOneWorld() {
        JsonReader reader = new JsonReader("./data/testReaderArchiveOneWorld.json");
        try {
            WorldLib worlds = reader.read();
            assertEquals("Arcaenus", worlds.getWorldList());
            try {
                World arcaenus = worlds.getWorld("Arcaenus");
                PageLib arcaenusDetails = arcaenus.getDetailsLib();
                PageLib arcaenusStory = arcaenus.getStoryLib();
                CharacterLib arcaenusCharas = arcaenus.getCharaLib();
                assertEquals("Victor Moon, Ivenna Brightheart", arcaenusCharas.getCharaList());
                assertEquals("Geography, People", arcaenusDetails.getPageList());
                assertEquals("Chapter 1, Chapter 2", arcaenusStory.getPageList());
            } catch (NotFoundException notFoundException) {
                fail("Couldn't find world.");
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderArchiveTwoWorlds() {
        JsonReader reader = new JsonReader("./data/testReaderArchiveTwoWorlds.json");
        try {
            WorldLib worlds = reader.read();
            assertEquals("Arcaenus, Earth", worlds.getWorldList());
            try {
                World arcaenus = worlds.getWorld("Arcaenus");
                PageLib arcaenusDetails = arcaenus.getDetailsLib();
                PageLib arcaenusStory = arcaenus.getStoryLib();
                CharacterLib arcaenusCharas = arcaenus.getCharaLib();
                assertEquals("Victor Moon, Ivenna Brightheart", arcaenusCharas.getCharaList());
                assertEquals("Geography, People", arcaenusDetails.getPageList());
                assertEquals("Chapter 1, Chapter 2", arcaenusStory.getPageList());

                World earth = worlds.getWorld("Earth");
                PageLib earthDetails = earth.getDetailsLib();
                PageLib earthStory = earth.getStoryLib();
                CharacterLib earthCharas = earth.getCharaLib();
                assertEquals("Nyx, Owen", earthCharas.getCharaList());
                assertEquals("Vancouver, Chicago", earthDetails.getPageList());
                assertEquals("No Story", earthStory.getPageList());
            } catch (NotFoundException notFoundException) {
                fail("Couldn't find world.");
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderImpossibleScenario() {
        JsonReader reader = new JsonReader("./data/testReaderImpossibleScenario.json");
        try {
            WorldLib worlds = reader.read();
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (RuntimeException e) {
            // pass
        }
    }

    @Test
    void testReaderImpossibleCharas() {
        JsonReader reader = new JsonReader("./data/testReaderImpossibleCharas.json");
        try {
            WorldLib worlds = reader.read();
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (RuntimeException e) {
            // pass
        }
    }

    @Test
    void testReaderImpossibleDetails() {
        JsonReader reader = new JsonReader("./data/testReaderImpossibleDetails.json");
        try {
            WorldLib worlds = reader.read();
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (RuntimeException e) {
            // pass
        }
    }

    @Test
    void testReaderImpossibleStory() {
        JsonReader reader = new JsonReader("./data/testReaderImpossibleStory.json");
        try {
            WorldLib worlds = reader.read();
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (RuntimeException e) {
            // pass
        }
    }
}

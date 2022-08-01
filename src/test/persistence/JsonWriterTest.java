package persistence;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import model.*;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// base code: JsonWriterTest in JSON demo file for project
class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorldLib worlds = new WorldLib();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyArchive() {
        try {
            WorldLib worlds = new WorldLib();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyArchive.json");
            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyArchive.json");
            worlds = reader.read();
            assertEquals("", worlds.getWorldList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneWorld() {
        try {
            WorldLib worlds = new WorldLib();
            World arcaenus = new World("Arcaenus");
            PageLib arcaenusDetails = arcaenus.getDetailsLib();
            PageLib arcaenusStory = arcaenus.getStoryLib();
            CharacterLib arcaenusCharas = arcaenus.getCharaLib();
            Character victorMoon = new Character("Victor Moon", "Mage");
            Character ivennaBrightheart = new Character("Ivenna Brightheart", "Craftsman");
            Page geography = new Page("Geography", "Magic Island");
            Page people = new Page("People", "Mages and Craftsmen");
            Page chap1 = new Page("Chapter 1", "text");
            Page chap2 = new Page("Chapter 2", "text");
            try {
                worlds.addWorld(arcaenus);
                arcaenusDetails.addPage(geography);
                arcaenusDetails.addPage(people);
                arcaenusCharas.addCharas(victorMoon);
                arcaenusCharas.addCharas(ivennaBrightheart);
                arcaenusStory.addPage(chap1);
                arcaenusStory.addPage(chap2);
            } catch (AlreadyExistsException alreadyExistsException) {
                fail("Exception should not have been thrown");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterArchiveOneWorld.json");
            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterArchiveOneWorld.json");
            worlds = reader.read();
            assertEquals("Arcaenus", worlds.getWorldList());
            try {
                arcaenus = worlds.getWorld("Arcaenus");
            } catch (NotFoundException e) {
                fail("Exception should not have been thrown");
            }
            arcaenusDetails = arcaenus.getDetailsLib();
            arcaenusStory = arcaenus.getStoryLib();
            arcaenusCharas = arcaenus.getCharaLib();
            assertEquals("Victor Moon, Ivenna Brightheart", arcaenusCharas.getCharaList());
            assertEquals("Geography, People", arcaenusDetails.getPageList());
            assertEquals("Chapter 1, Chapter 2", arcaenusStory.getPageList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterArchiveTwoWorlds() {
        try {
            WorldLib worlds = new WorldLib();
            World arcaenus = new World("Arcaenus");
            PageLib arcaenusDetails = arcaenus.getDetailsLib();
            PageLib arcaenusStory = arcaenus.getStoryLib();
            CharacterLib arcaenusCharas = arcaenus.getCharaLib();
            Character victorMoon = new Character("Victor Moon", "Mage");
            Character ivennaBrightheart = new Character("Ivenna Brightheart", "Craftsman");
            Page geography = new Page("Geography", "Magic Island");
            Page people = new Page("People", "Mages and Craftsmen");
            Page chap1 = new Page("Chapter 1", "text");
            Page chap2 = new Page("Chapter 2", "text");
            try {
                worlds.addWorld(arcaenus);
                arcaenusDetails.addPage(geography);
                arcaenusDetails.addPage(people);
                arcaenusCharas.addCharas(victorMoon);
                arcaenusCharas.addCharas(ivennaBrightheart);
                arcaenusStory.addPage(chap1);
                arcaenusStory.addPage(chap2);
            } catch (AlreadyExistsException alreadyExistsException) {
                fail("Exception should not have been thrown");
            }

            World earth = new World("Earth");
            PageLib earthDetails = earth.getDetailsLib();
            PageLib earthStory = earth.getStoryLib();
            CharacterLib earthCharas = earth.getCharaLib();
            Character nyx = new Character("Nyx", "Bestie");
            Character owen = new Character("Owen", "Bagel");
            Page vancouver = new Page("Vancouver", "Needs more A/C");
            Page chicago = new Page("Chicago", "Not a state?");
            Page noStory = new Page("No Story", "No Text");
            try {
                worlds.addWorld(earth);
                earthDetails.addPage(vancouver);
                earthDetails.addPage(chicago);
                earthCharas.addCharas(nyx);
                earthCharas.addCharas(owen);
                earthStory.addPage(noStory);
            } catch (AlreadyExistsException alreadyExistsException) {
                fail("Exception should not have been thrown");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterArchiveTwoWorlds.json");
            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterArchiveTwoWorlds.json");
            worlds = reader.read();
            assertEquals("Arcaenus, Earth", worlds.getWorldList());
            try {
                arcaenus = worlds.getWorld("Arcaenus");
                earth = worlds.getWorld("Earth");
            } catch (NotFoundException e) {
                fail("Exception should not have been thrown");
            }
            arcaenusDetails = arcaenus.getDetailsLib();
            arcaenusStory = arcaenus.getStoryLib();
            arcaenusCharas = arcaenus.getCharaLib();

            earthDetails = earth.getDetailsLib();
            earthStory = earth.getStoryLib();
            earthCharas = earth.getCharaLib();

            assertEquals("Victor Moon, Ivenna Brightheart", arcaenusCharas.getCharaList());
            assertEquals("Geography, People", arcaenusDetails.getPageList());
            assertEquals("Chapter 1, Chapter 2", arcaenusStory.getPageList());

            assertEquals("Nyx, Owen", earthCharas.getCharaList());
            assertEquals("Vancouver, Chicago", earthDetails.getPageList());
            assertEquals("No Story", earthStory.getPageList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
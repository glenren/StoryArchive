package ui;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import model.*;
import model.Character;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Story Archive Application
// base code credit: TellerApp
public class StoryArchiveApp {
    private static final String JSON_STORE = "./data/archive.json";
    private WorldLib worlds;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Story Archive application
    public StoryArchiveApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runStoryArchive();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input
    private void runStoryArchive() {
        boolean keepGoing = true;
        String command;

        start();

        while (keepGoing) {
            optionsMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThank you for using Story Archive. We hope to see you again!");
    }

    // MODIFIES: this
    // EFFECTS: processes the user's inputted command
    private void processCommand(String command) {
        switch (command) {
            case "1":
                createNewWorld();
                break;
            case "2":
                editExistingWorld();
                break;
            case "3":
                viewExistingWorld();
                break;
            case "4":
                saveArchive();
                break;
            case "5":
                loadArchive();
                break;
            default:
                invalid();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new world library
    private void start() {
        worlds = new WorldLib();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

// ------------ below here are the text formatting helper methods ------------

    // EFFECTS: displays menu of options to user
    private void optionsMenu() {
        System.out.println("\nWhat would you like to do?");
        printOption(1, "Create a new world");
        printOption(2, "Edit an existing world");
        printOption(3, "View an existing world");
        printOption(4, "Save this Archive to file");
        printOption(5, "Load an Archive from file");
        printOption("Q", "Quit");
    }

    // EFFECTS: sets up the number option formatting
    private void printOption(int i, String text) {
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        System.out.println("\t[" + i + "] " + text + ".");
    }

    // EFFECTS: sets up the letter option formatting
    private void printOption(String key, String text) {
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        System.out.println("\t[" + key + "] " + text + ".");
    }

    // EFFECTS: provides the yes or no menu
    private void yesOrNo() {
        printOption(1, "Proceed");
        printOption(2, "Go back");
    }

    // EFFECTS: provides the full text
    private void full(String things) {
        System.out.println("You have reached the maximum amount of " + things + ".");
    }

    // EFFECTS: provides the already exists text
    private void already(String thing) {
        if (thing.equals("world") || thing.equals("character")) {
            System.out.println("There is already a " + thing + "with this name.");
        } else {
            System.out.println("There is already a " + thing + "with this title.");
        }
    }

    // EFFECTS: provides the invalid selection text
    private void invalid() {
        System.out.println("Please select a valid option.");
    }

    // EFFECTS: provides the return to menu text
    private void returnMenu() {
        System.out.println("Returning to menu.");
    }

    // EFFECTS: provides the thing not found text
    private void notFound(String thing) {
        if (thing.equals("world") || thing.equals("character")) {
            System.out.println("There is no " + thing + " found with the inputted name.");
        } else {
            System.out.println("There is no " + thing + " found with the inputted title.");
        }
    }

    // EFFECTS: provides the no existing things text
    private void notExist(String things) {
        System.out.print("You have no existing " + things + ".");
    }

    // EFFECTS: provides the name/title entering text
    private void enterThe(String thing) {
        if (thing.equals("world") || thing.equals("character")) {
            System.out.print("Enter the name of your new " + thing + ": ");
        } else {
            System.out.print("Enter the title of your new " + thing + ": ");
        }
    }

    // EFFECTS: provides the name/title entering text
    private void enterThe(String thing, String action) {
        if (thing.equals("world") || thing.equals("character")) {
            System.out.println("\nEnter the name of the " + thing + " you would like to " + action + ": ");
        } else {
            System.out.println("\nEnter the title of the " + thing + " you would like to " + action + ": ");
        }
    }

    // EFFECTS: provides the creation confirmation menu
    private void createNew(String thing, String name) {
        if (thing.equals("character") || thing.equals("world")) {
            System.out.println("\nCreate a new " + thing + " named " + name + "?");
        } else {
            System.out.println("\nCreate a new " + thing + " titled " + name + "?");
        }
        yesOrNo();
    }

    // EFFECTS: provides the editing menu
    private void partOfLibMenu(String worldName, String thing) {
        if (thing.equals("character")) {
            System.out.println("\nHow would you like to edit " + worldName + "'s characters?");
        } else if (thing.equals("chapter")) {
            System.out.println("\nWhat part of " + worldName + "'s story would you like to edit?");
        } else {
            System.out.println("\nWhat part of " + worldName + "'s details would you like to edit?");
        }
        partOfLibMenu(thing);
    }

    // EFFECTS: provides the editing menu options
    private void partOfLibMenu(String thing) {
        printOption(1, "Create new " + thing);
        printOption(2, "Edit existing " + thing);
        printOption(3, "Go back to World editor");
        printOption(4, "Return to main menu");
    }

    private void worldMenu(String worldName, String action) {
        if (action.equals("edit")) {
            System.out.println("\nWhat part of " + worldName + " would you like to " + action + "?");
            printOption(1, "Rename " + worldName);
            printOption(2, action + " " + worldName + "'s world details");
            printOption(3, action + " " + worldName + "'s characters");
            printOption(4, action + " " + worldName + "'s story chapters");
            printOption(5, "Delete " + worldName);
            printOption(6, "Return to main menu");
        } else {
            System.out.println("\nWhat part of " + worldName + " would you like to " + action + "?");
            printOption(1, action + " " + worldName + "'s world details");
            printOption(2, action + " " + worldName + "'s characters");
            printOption(3, action + " " + worldName + "'s story chapters");
            printOption(4, "Return to main menu");
        }
    }

    // EFFECTS: provides the editing character/page options
    private void editThingMenu(String name, String thing) {
        System.out.println("\nHow would you like to edit " + name + "?");
        if (thing.equals("character")) {
            printOption(1, "Rename " + name);
            printOption(2, "Add to " + name + "'s description");
            printOption(3, "Replace " + name + "'s description");
        } else {
            printOption(1, "Re-title " + name);
            printOption(2, "Add to " + name + "'s text");
            printOption(3, "Replace " + name + "'s text");
        }
        printOption(4, "Delete " + name);
        printOption(5, "Return to main menu");
    }

    // EFFECTS: provides the post-viewing menu
    private void nextView(String things) {
        System.out.println("\nWhat would you like to do next?");
        printOption(1, "View more " + things);
        printOption(2, "Go back to world viewing menu");
        printOption(3, "Return to main menu");
    }

// ------------ below here are the action methods ------------

    // EFFECTS: checks if new world is full, and if isn't then sends it to creating a new world with given name
    private void createNewWorld() {
        if (worlds.isFull()) {
            full("worlds");
        } else {
            enterThe("world");
            String name = input.next();
            createNewWorld(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the new world creation menu where users can create new worlds
    private void createNewWorld(String name) {
        createNew("world", name);
        String command = input.next();
        if (command.equals("1")) {
            World newWorld = new World(name);
            try {
                worlds.addWorld(newWorld);
                editWorld(newWorld);
            } catch (AlreadyExistsException exc) {
                already("world");
                createNewWorld();
            }
        } else if (command.equals("2")) {
            createNewWorld();
        } else {
            invalid();
            createNewWorld();
        }
    }

    // EFFECTS: provides the menu of existing worlds for user to select and edit
    private void editExistingWorld() {
        if (worlds.isEmpty()) {
            notExist("worlds");
        } else {
            System.out.println("\nYour existing worlds:");
            System.out.println(worlds.getWorldList());
            enterThe("world", "edit");
            String name = input.next();
            try {
                editWorld(worlds.getWorld(name));
            } catch (NotFoundException exc) {
                notFound("world");
                editExistingWorld();
            }
        }
    }

    // EFFECTS: provides the menu of existing worlds for user to select and view
    private void viewExistingWorld() {
        if (worlds.isEmpty()) {
            notExist("worlds");
        } else {
            System.out.println("\nYour existing worlds:");
            System.out.println(worlds.getWorldList());
            enterThe("world", "view");
            String name = input.next();
            try {
                viewWorld(worlds.getWorld(name));
            } catch (NotFoundException exc) {
                notFound("world");
                editExistingWorld();
            }
        }
    }

    // EFFECTS: provides the world editing menu for a specific world
    private void editWorld(World world) {
        worldMenu(world.getName(), "edit");
        String command = input.next();
        if (command.equals("1")) {
            renameWorld(world);
        } else if (command.equals("2")) {
            editDetails(world.getDetailsLib(), world);
        } else if (command.equals("3")) {
            editCharas(world.getCharaLib(), world);
        } else if (command.equals("4")) {
            editStory(world.getStoryLib(), world);
        } else if (command.equals("5")) {
            removeWorld(world);
        } else if (command.equals("6")) {
            returnMenu();
        } else {
            invalid();
            editWorld(world);
        }
    }

    // EFFECTS: provides the world renaming menu for a specific world
    private void renameWorld(World world) {
        System.out.print("Change the name of " + world.getName() + ": ");
        String text = input.next();

        System.out.println("The new name will be: " + text + ".");
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            world.rename(text);
        } else if (command.equals("2")) {
            renameWorld(world);
        } else {
            invalid();
            renameWorld(world);
        }
    }

    // EFFECTS: provides the world removing menu for a specific world
    private void removeWorld(World world) {
        System.out.println("Permanently remove " + world.getName() + "from Archive?");
        System.out.println("(This action cannot be undone.)");
        yesOrNo();
        String command = input.next();

        if (command.equals("1")) {
            try {
                worlds.removeWorld(world.getName());
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
            String worldName = world.getName().substring(0, 1).toUpperCase() + world.getName().substring(1);
            System.out.println(worldName + "has been permanently removed from Archive.");
        } else if (command.equals("2")) {
            editWorld(world);
        } else {
            invalid();
            removeWorld(world);
        }
    }

    // EFFECTS: provides the world viewing menu for a specific world
    private void viewWorld(World world) {
        worldMenu(world.getName(), "view");

        String command = input.next();

        switch (command) {
            case "1":
                viewDetails(world.getDetailsLib(), world);
                break;
            case "2":
                viewCharas(world.getCharaLib(), world);
                break;
            case "3":
                viewStory(world.getStoryLib(), world);
                break;
            case "4":
                returnMenu();
                break;
            default:
                invalid();
                viewWorld(world);
                break;
        }
    }

    // EFFECTS: provides a world details library editing menu
    private void editDetails(PageLib details, World world) {
        partOfLibMenu(world.getName(), "page");

        String command = input.next();

        switch (command) {
            case "1":
                createNewDetailsPage(details);
                break;
            case "2":
                editExistingDetails(details);
                break;
            case "3":
                editWorld(world);
                break;
            case "4":
                returnMenu();
                break;
            default:
                invalid();
                editDetails(details, world);
                break;
        }
    }

    // EFFECTS: provides a character library editing menu
    private void editCharas(CharacterLib charas, World world) {
        partOfLibMenu(world.getName(), "character");

        String command = input.next();

        switch (command) {
            case "1":
                createNewCharacter(charas);
                break;
            case "2":
                editExistingCharacter(charas);
                break;
            case "3":
                editWorld(world);
                break;
            case "4":
                returnMenu();
                break;
            default:
                invalid();
                editWorld(world);
                break;
        }
    }

    // EFFECTS: provides a story editing menu
    private void editStory(PageLib story, World world) {
        partOfLibMenu(world.getName(), "chapter");

        String command = input.next();

        switch (command) {
            case "1":
                createNewStoryChapter(story);
                break;
            case "2":
                editExistingChapter(story);
                break;
            case "3":
                editWorld(world);
                break;
            case "4":
                returnMenu();
                break;
            default:
                invalid();
                editWorld(world);
                break;
        }
    }

    // EFFECTS: provides the menu for the creation of a new details page
    private void createNewDetailsPage(PageLib details) {
        if (details.isFull()) {
            full("pages");
        } else {
            enterThe("page");
            String name = input.next();
            createNewDetailsPage(details, name);
        }
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the creation of a new details page
    private void createNewDetailsPage(PageLib details, String name) {
        createNew("page", name);

        String command = input.next();

        if (command.equals("1")) {
            Page newDetail = new Page(name, "");
            try {
                details.addPage(newDetail);
                replaceDescription(newDetail);
            } catch (AlreadyExistsException exc) {
                already("page");
                createNewDetailsPage(details);
            }
        } else if (command.equals("2")) {
            createNewDetailsPage(details);
        } else {
            invalid();
            createNewDetailsPage(details);
        }
    }

    // EFFECTS: provides the menu for the creation of a new chapter
    private void createNewStoryChapter(PageLib story) {
        if (story.isFull()) {
            full("chapters");
        } else {
            enterThe("chapter");
            String name = input.next();
            createNewStoryChapter(story, name);
        }
    }

    // MODIFIES: story
    // EFFECTS: provides the menu for the creation of a new chapter
    private void createNewStoryChapter(PageLib story, String name) {
        createNew("chapter", name);

        String command = input.next();

        if (command.equals("1")) {
            Page newChapter = new Page(name, "");
            try {
                story.addPage(newChapter);
                replaceDescription(newChapter);
            } catch (AlreadyExistsException exc) {
                already("chapter");
                createNewDetailsPage(story);
            }
        } else if (command.equals("2")) {
            createNewStoryChapter(story);
        } else {
            invalid();
            createNewStoryChapter(story);
        }
    }

    // EFFECTS: provides the menu for the creation of a new character
    private void createNewCharacter(CharacterLib charas) {
        if (charas.isFull()) {
            full("characters");
        } else {
            enterThe("character");
            String name = input.next();
            createNewCharacter(charas, name);
        }
    }

    // MODIFIES: charas
    // EFFECTS: provides the menu for the creation of a new character
    private void createNewCharacter(CharacterLib charas, String name) {
        createNew("character", name);

        String command = input.next();

        if (command.equals("1")) {
            Character newChara = new Character(name, "");
            try {
                charas.addChara(newChara);
                replaceDescription(newChara);
            } catch (AlreadyExistsException exc) {
                already("character");
                createNewCharacter(charas);
            }
        } else if (command.equals("2")) {
            createNewCharacter(charas);
        } else {
            invalid();
            createNewCharacter(charas);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for adding to a page's description
    private void addDescription(Page page) {
        System.out.print("Add text to " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println((page.getBody() + " " + text));
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            page.addText(text);
        } else if (command.equals("2")) {
            addDescription(page);
        } else {
            invalid();
            addDescription(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for adding to a character's description
    private void addDescription(Character chara) {
        System.out.print("Add text to " + chara.getName() + "'s description: ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println((chara.getDesc() + " " + text));
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            chara.addDesc(text);
        } else if (command.equals("2")) {
            addDescription(chara);
        } else {
            invalid();
            addDescription(chara);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for changing a page's description
    private void replaceDescription(Page page) {
        System.out.print("Change the text of " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println(text);
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            page.replaceText(text);
        } else if (command.equals("2")) {
            replaceDescription(page);
        } else {
            invalid();
            replaceDescription(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for changing a character's description
    private void replaceDescription(Character chara) {
        System.out.print("Change the text of " + chara.getName() + "'s description: ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println(text);
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            chara.replaceDesc(text);
        } else if (command.equals("2")) {
            replaceDescription(chara);
        } else {
            invalid();
            replaceDescription(chara);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for changing a page's title
    private void renamePage(Page page) {
        System.out.print("Change the title of " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("The new title will be: " + text + ".");
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            page.rename(text);
        } else if (command.equals("2")) {
            renamePage(page);
        } else {
            invalid();
            renamePage(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for changing a character's name
    private void renameCharacter(Character chara) {
        System.out.print("Changing the name of " + chara.getName() + ": ");
        String text = input.next();

        System.out.println("The new name will be: " + text + ".");
        yesOrNo();

        String command = input.next();

        if (command.equals("1")) {
            chara.rename(text);
        } else if (command.equals("2")) {
            renameCharacter(chara);
        } else {
            invalid();
            renameCharacter(chara);
        }
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the editing of existing detail pages
    private void editExistingDetails(PageLib details) {
        if (details.isEmpty()) {
            notExist("world detail pages");
        } else {
            System.out.println("\nYour existing pages:");
            System.out.println(details.getPageList());
            enterThe("page", "edit");
            String name = input.next();
            try {
                editPage(details, details.getPage(name));
            } catch (NotFoundException exc) {
                notFound("page");
                editExistingDetails(details);
            }
        }
    }


    // EFFECTS: provides the menu for the viewing of existing detail pages
    private void viewDetails(PageLib details, World world) {
        if (details.isEmpty()) {
            notExist("world detail pages");
        } else {
            System.out.println("\nYour existing pages:");
            System.out.println(details.getPageList());
            enterThe("page", "view");
            String name = input.next();
            try {
                viewPage(details.getPage(name));
            } catch (NotFoundException exc) {
                notFound("page");
                viewDetails(details, world);
            }
            viewNextDetails(details, world);
        }
    }

    // EFFECTS: provides the menu after viewing a detail
    private void viewNextDetails(PageLib details, World world) {
        nextView("details");
        String answer = input.next();
        switch (answer) {
            case "1":
                viewDetails(details, world);
                break;
            case "2":
                viewWorld(world);
                break;
            case "3":
                returnMenu();
                break;
            default:
                invalid();
                viewNextDetails(details, world);
                break;
        }
    }

    // EFFECTS: provides the menu after viewing a story chapter
    private void viewNextStory(PageLib story, World world) {
        nextView("chapters");
        String answer = input.next();
        switch (answer) {
            case "1":
                viewStory(story, world);
                break;
            case "2":
                viewWorld(world);
                break;
            case "3":
                returnMenu();
                break;
            default:
                invalid();
                viewNextStory(story, world);
                break;
        }
    }

    // EFFECTS: provides the menu after viewing a character
    private void viewNextChara(CharacterLib charas, World world) {
        nextView("characters");
        String answer = input.next();
        switch (answer) {
            case "1":
                viewCharas(charas, world);
                break;
            case "2":
                viewWorld(world);
                break;
            case "3":
                returnMenu();
                break;
            default:
                invalid();
                viewNextChara(charas, world);
                break;
        }
    }

    // MODIFIES: charas
    // EFFECTS: provides the menu for the editing of existing characters
    private void editExistingCharacter(CharacterLib charas) {
        if (charas.isEmpty()) {
            notExist("characters");
        } else {
            System.out.println("\nYour existing characters:");
            System.out.println(charas.getCharaList());
            enterThe("character", "edit");
            String name = input.next();
            try {
                editCharacter(charas, charas.getChara(name));
            } catch (NotFoundException exc) {
                notFound("character");
                editExistingCharacter(charas);
            }
        }
    }

    // EFFECTS: provides the menu for the viewing of existing characters
    private void viewCharas(CharacterLib charas, World world) {
        if (charas.isEmpty()) {
            notExist("characters");
        } else {
            System.out.println("\nYour existing characters:");
            System.out.println(charas.getCharaList());
            enterThe("character", "view");
            String name = input.next();
            try {
                viewCharacter(charas.getChara(name));
            } catch (NotFoundException exc) {
                notFound("character");
                viewCharas(charas, world);
            }
            viewNextChara(charas, world);
        }
    }

    // MODIFIES: story
    // EFFECTS: provides the menu for the editing of existing chapters
    private void editExistingChapter(PageLib story) {
        if (story.isEmpty()) {
            notExist("chapters");
        } else {
            System.out.println("\nYour existing chapters:");
            System.out.println(story.getPageList());
            enterThe("chapter", "edit");
            String name = input.next();
            try {
                editPage(story, story.getPage(name));
            } catch (NotFoundException exc) {
                notFound("chapter");
                editExistingChapter(story);
            }
        }
    }

    // EFFECTS: provides the menu for the viewing of existing chapters
    private void viewStory(PageLib story, World world) {
        if (story.isEmpty()) {
            notExist("chapters");
        } else {
            System.out.println("\nYour existing chapters:");
            System.out.println(story.getPageList());
            enterThe("chapter", "view");
            String name = input.next();
            try {
                viewPage(story.getPage(name));
            } catch (NotFoundException exc) {
                notFound("chapter");
                viewStory(story, world);
            }
            viewNextStory(story, world);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for the editing of a page
    private void editPage(PageLib pages, Page page) {
        editThingMenu(page.getTitle(), "page");

        String command = input.next();

        switch (command) {
            case "1":
                renamePage(page);
                break;
            case "2":
                addDescription(page);
                break;
            case "3":
                replaceDescription(page);
                break;
            case "4":
                removePage(pages, page);
                break;
            case "5":
                returnMenu();
                break;
            default:
                invalid();
                editPage(pages, page);
                break;
        }
    }

    // EFFECTS: provides the page removing menu for a specific page
    private void removePage(PageLib pages, Page page) {
        System.out.println("Permanently remove " + page.getTitle() + "from Archive?");
        System.out.println("(This action cannot be undone.)");
        yesOrNo();
        String command = input.next();

        if (command.equals("1")) {
            try {
                pages.removePage(page.getTitle());
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
            String pageTitle = page.getTitle().substring(0, 1).toUpperCase() + page.getTitle().substring(1);
            System.out.println(pageTitle + "has been permanently removed from Archive.");
        } else if (command.equals("2")) {
            editPage(pages, page);
        } else {
            invalid();
            removePage(pages, page);
        }
    }

    // EFFECTS: provides the menu for the viewing of a page
    private void viewPage(Page page) {
        System.out.println("\n" + page.getTitle());
        System.out.println(page.getBody());
    }

    // EFFECTS: provides the menu for the viewing of a page
    private void viewCharacter(Character chara) {
        System.out.println("\n" + chara.getName());
        System.out.println(chara.getDesc());
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for the editing of a character
    private void editCharacter(CharacterLib charas, Character chara) {
        editThingMenu(chara.getName(), "character");

        String command = input.next();

        switch (command) {
            case "1":
                renameCharacter(chara);
                break;
            case "2":
                addDescription(chara);
                break;
            case "3":
                replaceDescription(chara);
                break;
            case "4":
                removeCharacter(charas, chara);
                break;
            case "5":
                returnMenu();
                break;
            default:
                invalid();
                editCharacter(charas, chara);
                break;
        }
    }

    // EFFECTS: provides the character removing menu for a specific character
    private void removeCharacter(CharacterLib charas, Character chara) {
        System.out.println("Permanently remove " + chara.getName() + "from Archive?");
        System.out.println("(This action cannot be undone.)");
        yesOrNo();
        String command = input.next();

        if (command.equals("1")) {
            try {
                charas.removeChara(chara.getName());
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
            String pageTitle = chara.getName().substring(0, 1).toUpperCase() + chara.getName().substring(1);
            System.out.println(pageTitle + "has been permanently removed from Archive.");
        } else if (command.equals("2")) {
            editCharacter(charas, chara);
        } else {
            invalid();
            removeCharacter(charas, chara);
        }
    }

    // EFFECTS: saves the current archive to file
    private void saveArchive() {
        try {
            jsonWriter.open();
            jsonWriter.write(worlds);
            jsonWriter.close();
            System.out.println("Saved current Story Archive to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads archive from file
    private void loadArchive() {
        try {
            worlds = jsonReader.read();
            System.out.println("Loaded up Story Archive from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

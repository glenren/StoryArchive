package ui;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import model.*;
import model.Character;

import java.util.Scanner;

// Story Archive Application
// base code credit: TellerApp
public class StoryArchiveApp {
    private WorldLib worlds;
    private Scanner input;

    // EFFECTS: runs the Story Archive application
    public StoryArchiveApp() {
        runStoryArchive();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input
    private void runStoryArchive() {
        boolean keepGoing = true;
        String command = null;

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
        if (command.equals("1")) {
            createNewWorld();
        } else if (command.equals("2")) {
            editExistingWorld();
        } else if (command.equals("3")) {
            viewExistingWorld();
        } else {
            System.out.println("Please select a valid option.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new world library
    private void start() {
        worlds = new WorldLib();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void optionsMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\t[1] Create a new world.");
        System.out.println("\t[2] Edit an existing world.");
        System.out.println("\t[3] View an existing world.");
        System.out.println("\t[Q] Quit.");
    }

    // EFFECTS: checks if new world is full, and if isn't then sends it to creating a new world with given name
    private void createNewWorld() {
        if (worlds.isFull()) {
            System.out.println("You have reached the maximum amount of worlds.");
        } else {
            System.out.print("Enter the name of your new world: ");
            String name = input.next();
            createNewWorld(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the new world creation menu where users can create new worlds
    private void createNewWorld(String name) {
        System.out.println("\nCreate a new world named " + name + "?");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");
        String command = input.next();
        if (command.equals("1")) {
            World newWorld = new World(name);
            try {
                worlds.addWorld(newWorld);
                editWorld(newWorld);
            } catch (AlreadyExistsException exc) {
                System.out.println("There is already a world with this name.");
                createNewWorld();
            }
        } else if (command.equals("2")) {
            createNewWorld();
        } else {
            System.out.println("Please select a valid option.");
            createNewWorld();
        }
    }

    // EFFECTS: provides the menu of existing worlds for user to select and edit
    private void editExistingWorld() {
        if (worlds.isEmpty()) {
            System.out.print("You have no existing worlds.");
        } else {
            System.out.println("\nYour existing worlds:");
            System.out.println(worlds.getWorldList());
            System.out.println("\nEnter the name of the world you would like to edit: ");
            String name = input.next();
            try {
                editWorld(worlds.getWorld(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no world found with the inputted name.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                editExistingWorld();
            }
        }
    }

    // EFFECTS: provides the menu of existing worlds for user to select and view
    private void viewExistingWorld() {
        if (worlds.isEmpty()) {
            System.out.print("You have no existing worlds.");
        } else {
            System.out.println("\nYour existing worlds:");
            System.out.println(worlds.getWorldList());
            System.out.println("\nEnter the name of the world you would like to view: ");
            String name = input.next();
            try {
                viewWorld(worlds.getWorld(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no world found with the inputted name.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                editExistingWorld();
            }
        }
    }

    // EFFECTS: provides the world editing menu for a specific world
    private void editWorld(World world) {
        System.out.println("\nWhat part of " + world.getName() + " would you like to edit?");
        System.out.println("\t[1] Edit world details.");
        System.out.println("\t[2] Edit characters.");
        System.out.println("\t[3] Edit story chapters.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            editDetails(world.getDetailsLib(), world);
        } else if (command.equals("2")) {
            editCharas(world.getCharaLib(), world);
        } else if (command.equals("3")) {
            editStory(world.getStoryLib(), world);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editWorld(world);
        }
    }

    // EFFECTS: provides the world viewing menu for a specific world
    private void viewWorld(World world) {
        System.out.println("\nWhat part of " + world.getName() + " would you like to view?");
        System.out.println("\t[1] View world details.");
        System.out.println("\t[2] View characters.");
        System.out.println("\t[3] View story chapters.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            viewDetails(world.getDetailsLib(), world);
        } else if (command.equals("2")) {
            viewCharas(world.getCharaLib(), world);
        } else if (command.equals("3")) {
            viewStory(world.getStoryLib(), world);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            viewWorld(world);
        }
    }

    // EFFECTS: provides a world details library editing menu
    private void editDetails(PageLib details, World world) {
        System.out.println("\nWhat part of " + world.getName() + "'s details would you like to edit?");
        System.out.println("\t[1] Create new page.");
        System.out.println("\t[2] Edit existing page.");
        System.out.println("\t[3] Go back to World editor.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            createNewDetailsPage(details);
        } else if (command.equals("2")) {
            editExistingDetails(details);
        } else if (command.equals("3")) {
            editWorld(world);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editDetails(details, world);
        }
    }

    // EFFECTS: provides a character library editing menu
    private void editCharas(CharacterLib charas, World world) {
        System.out.println("\nHow would you like to edit " + world.getName() + "'s characters?");
        System.out.println("\t[1] Create new character.");
        System.out.println("\t[2] Edit existing character.");
        System.out.println("\t[3] Go back to World editor.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            createNewCharacter(charas);
        } else if (command.equals("2")) {
            editExistingCharacter(charas);
        } else if (command.equals("3")) {
            editWorld(world);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editWorld(world);
        }
    }

    // EFFECTS: provides a story editing menu
    private void editStory(PageLib story, World world) {
        System.out.println("\nWhat part of " + world.getName() + "'s story would you like to edit?");
        System.out.println("\t[1] Create new chapter.");
        System.out.println("\t[2] Edit existing chapter.");
        System.out.println("\t[3] Go back to World editor.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            createNewStoryChapter(story);
        } else if (command.equals("2")) {
            editExistingChapter(story);
        } else if (command.equals("3")) {
            editWorld(world);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editWorld(world);
        }
    }

    // EFFECTS: provides the menu for the creation of a new details page
    public void createNewDetailsPage(PageLib details) {
        if (details.isFull()) {
            System.out.print("You have reached the maximum amount of pages.");
        } else {
            System.out.print("Enter the title of your new page: ");
            String name = input.next();
            createNewDetailsPage(details, name);
        }
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the creation of a new details page
    public void createNewDetailsPage(PageLib details, String name) {
        System.out.println("\nCreate a new page titled " + name + "?");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            Page newDetail = new Page(name, "");
            try {
                details.addPage(newDetail);
                replaceDescription(newDetail);
            } catch (AlreadyExistsException exc) {
                System.out.println("There is already a page with this title.");
                createNewDetailsPage(details);
            }
        } else if (command.equals("2")) {
            createNewDetailsPage(details);
        } else {
            System.out.println("Please select a valid option.");
            createNewDetailsPage(details);
        }
    }

    // EFFECTS: provides the menu for the creation of a new chapter
    public void createNewStoryChapter(PageLib story) {
        if (story.isFull()) {
            System.out.print("You have reached the maximum amount of chapters.");
        } else {
            System.out.print("Enter the title of your new chapter: ");
            String name = input.next();
            createNewStoryChapter(story, name);
        }
    }

    // MODIFIES: story
    // EFFECTS: provides the menu for the creation of a new chapter
    public void createNewStoryChapter(PageLib story, String name) {
        System.out.println("\nCreate a new chapter titled " + name + "?");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            Page newChapter = new Page(name, "");
            try {
                story.addPage(newChapter);
                replaceDescription(newChapter);
            } catch (AlreadyExistsException exc) {
                System.out.println("There is already a chapter with this title.");
                createNewDetailsPage(story);
            }
        } else if (command.equals("2")) {
            createNewStoryChapter(story);
        } else {
            System.out.println("Please select a valid option.");
            createNewStoryChapter(story);
        }
    }

    // EFFECTS: provides the menu for the creation of a new character
    public void createNewCharacter(CharacterLib charas) {
        if (charas.isFull()) {
            System.out.print("You have reached the maximum amount of characters.");
        } else {
            System.out.print("Enter the name of your new character: ");
            String name = input.next();
            createNewCharacter(charas, name);
        }
    }

    // MODIFIES: charas
    // EFFECTS: provides the menu for the creation of a new character
    public void createNewCharacter(CharacterLib charas, String name) {
        System.out.println("\nCreate a new character named " + name + "?");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            Character newChara = new Character(name, "");
            try {
                charas.addCharas(newChara);
                replaceDescription(newChara);
            } catch (AlreadyExistsException exc) {
                System.out.println("There is already a character with this name.");
                createNewCharacter(charas);
            }
        } else if (command.equals("2")) {
            createNewCharacter(charas);
        } else {
            System.out.println("Please select a valid option.");
            createNewCharacter(charas);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for adding to a page's description
    public void addDescription(Page page) {
        System.out.print("Add text to " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println((page.getBody() + " " + text));
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            page.addText(text);
        } else if (command.equals("2")) {
            addDescription(page);
        } else {
            System.out.println("Please select a valid option.");
            addDescription(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for adding to a character's description
    public void addDescription(Character chara) {
        System.out.print("Add text to " + chara.getName() + "'s description: ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println((chara.getDesc() + " " + text));
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            chara.addDesc(text);
        } else if (command.equals("2")) {
            addDescription(chara);
        } else {
            System.out.println("Please select a valid option.");
            addDescription(chara);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for changing a page's description
    public void replaceDescription(Page page) {
        System.out.print("Change the text of " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println(text);
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            page.replaceText(text);
        } else if (command.equals("2")) {
            replaceDescription(page);
        } else {
            System.out.println("Please select a valid option.");
            replaceDescription(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for changing a character's description
    public void replaceDescription(Character chara) {
        System.out.print("Change the text of " + chara.getName() + "'s description: ");
        String text = input.next();

        System.out.println("Confirm your submission: ");
        System.out.println(text);
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            chara.replaceDesc(text);
        } else if (command.equals("2")) {
            replaceDescription(chara);
        } else {
            System.out.println("Please select a valid option.");
            replaceDescription(chara);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for changing a page's title
    public void renamePage(Page page) {
        System.out.print("Change the title of " + page.getTitle() + ": ");
        String text = input.next();

        System.out.println("The new title will be: " + text + ".");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            page.rename(text);
        } else if (command.equals("2")) {
            renamePage(page);
        } else {
            System.out.println("Please select a valid option.");
            renamePage(page);
        }
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for changing a character's name
    public void renameCharacter(Character chara) {
        System.out.print("Changing the name of " + chara.getName() + ": ");
        String text = input.next();

        System.out.println("The new name will be: " + text + ".");
        System.out.println("\t[1] Proceed.");
        System.out.println("\t[2] Go back.");

        String command = input.next();

        if (command.equals("1")) {
            chara.rename(text);
        } else if (command.equals("2")) {
            renameCharacter(chara);
        } else {
            System.out.println("Please select a valid option.");
            renameCharacter(chara);
        }
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the editing of existing detail pages
    public void editExistingDetails(PageLib details) {
        if (details.isEmpty()) {
            System.out.print("You have no existing world detail pages.");
        } else {
            System.out.println("\nYour existing pages:");
            System.out.println(details.getPageList());
            System.out.println("\nEnter the title of the page you would like to edit: ");
            String name = input.next();
            try {
                editPage(details.getPage(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no page found with the inputted title.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                editExistingDetails(details);
            }
        }
    }


    // EFFECTS: provides the menu for the viewing of existing detail pages
    public void viewDetails(PageLib details, World world) {
        if (details.isEmpty()) {
            System.out.print("You have no existing world detail pages to view.");
        } else {
            System.out.println("\nYour existing pages:");
            System.out.println(details.getPageList());
            System.out.println("\nEnter the title of the page you would like to view: ");
            String name = input.next();
            try {
                viewPage(details.getPage(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no page found with the inputted title.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                viewDetails(details, world);
            }
            viewNextDetails(details, world);
        }
    }

    // EFFECTS: provides the menu after viewing a detail
    public void viewNextDetails(PageLib details, World world) {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("\t[1] View more world details.");
        System.out.println("\t[2] Go back.");
        System.out.println("\t[3] Return to main menu.");
        String answer = input.next();
        if (answer.equals("1")) {
            viewDetails(details, world);
        } else if (answer.equals("2")) {
            viewWorld(world);
        } else if (answer.equals("3")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            viewNextDetails(details, world);
        }
    }

    // EFFECTS: provides the menu after viewing a story chapter
    public void viewNextStory(PageLib story, World world) {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("\t[1] View more chapters.");
        System.out.println("\t[2] Go back.");
        System.out.println("\t[3] Return to main menu.");
        String answer = input.next();
        if (answer.equals("1")) {
            viewStory(story, world);
        } else if (answer.equals("2")) {
            viewWorld(world);
        } else if (answer.equals("3")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            viewNextStory(story, world);
        }
    }

    // EFFECTS: provides the menu after viewing a character
    public void viewNextChara(CharacterLib charas, World world) {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("\t[1] View more characters.");
        System.out.println("\t[2] Go back.");
        System.out.println("\t[3] Return to main menu.");
        String answer = input.next();
        if (answer.equals("1")) {
            viewCharas(charas, world);
        } else if (answer.equals("2")) {
            viewWorld(world);
        } else if (answer.equals("3")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            viewNextChara(charas, world);
        }
    }

    // MODIFIES: charas
    // EFFECTS: provides the menu for the editing of existing characters
    public void editExistingCharacter(CharacterLib charas) {
        if (charas.isEmpty()) {
            System.out.print("You have no existing characters.");
        } else {
            System.out.println("\nYour existing characters:");
            System.out.println(charas.getCharaList());
            System.out.println("\nEnter the name of the character you would like to edit: ");
            String name = input.next();
            try {
                editCharacter(charas.getChara(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no character found with the inputted name.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                editExistingCharacter(charas);
            }
        }
    }

    // EFFECTS: provides the menu for the viewing of existing characters
    public void viewCharas(CharacterLib charas, World world) {
        if (charas.isEmpty()) {
            System.out.print("You have no existing characters.");
        } else {
            System.out.println("\nYour existing characters:");
            System.out.println(charas.getCharaList());
            System.out.println("\nEnter the name of the character you would like to view: ");
            String name = input.next();
            try {
                viewCharacter(charas.getChara(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no character found with the inputted name.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                viewCharas(charas, world);
            }
            viewNextChara(charas, world);
        }
    }

    // MODIFIES: story
    // EFFECTS: provides the menu for the editing of existing chapters
    public void editExistingChapter(PageLib story) {
        if (story.isEmpty()) {
            System.out.print("You have no existing chapters.");
        } else {
            System.out.println("\nYour existing chapters:");
            System.out.println(story.getPageList());
            System.out.println("\nEnter the title of the chapter you would like to edit: ");
            String name = input.next();
            try {
                editPage(story.getPage(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no chapter found with the inputted title.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                editExistingChapter(story);
            }
        }
    }

    // EFFECTS: provides the menu for the viewing of existing chapters
    public void viewStory(PageLib story, World world) {
        if (story.isEmpty()) {
            System.out.print("You have no existing chapters.");
        } else {
            System.out.println("\nYour existing chapters:");
            System.out.println(story.getPageList());
            System.out.println("\nEnter the title of the chapter you would like to view: ");
            String name = input.next();
            try {
                viewPage(story.getPage(name));
            } catch (NotFoundException exc) {
                System.out.println("There is no chapter found with the inputted title.");
                System.out.println("(Hint: make sure your capitalization is correct!)");
                viewStory(story, world);
            }
            viewNextStory(story, world);
        }
    }

    // MODIFIES: page
    // EFFECTS: provides the menu for the editing of a page
    public void editPage(Page page) {
        System.out.println("\nHow would you like to edit " + page.getTitle() + "?");
        System.out.println("\t[1] Rename title.");
        System.out.println("\t[2] Add to text.");
        System.out.println("\t[3] Replace text.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            renamePage(page);
        } else if (command.equals("2")) {
            addDescription(page);
        } else if (command.equals("3")) {
            replaceDescription(page);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editPage(page);
        }
    }

    // EFFECTS: provides the menu for the viewing of a page
    public void viewPage(Page page) {
        System.out.println("\n" + page.getTitle());
        System.out.println(page.getBody());
    }

    // EFFECTS: provides the menu for the viewing of a page
    public void viewCharacter(Character chara) {
        System.out.println("\n" + chara.getName());
        System.out.println(chara.getDesc());
    }

    // MODIFIES: chara
    // EFFECTS: provides the menu for the editing of a character
    public void editCharacter(Character chara) {
        System.out.println("\nHow would you like to edit " + chara.getName() + "?");
        System.out.println("\t[1] Rename character.");
        System.out.println("\t[2] Add to description text.");
        System.out.println("\t[3] Replace description text.");
        System.out.println("\t[4] Return to main menu.");

        String command = input.next();

        if (command.equals("1")) {
            renameCharacter(chara);
        } else if (command.equals("2")) {
            addDescription(chara);
        } else if (command.equals("3")) {
            replaceDescription(chara);
        } else if (command.equals("4")) {
            System.out.println("Returning to menu.");
        } else {
            System.out.println("Please select a valid option.");
            editCharacter(chara);
        }
    }
}

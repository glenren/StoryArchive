package ui;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import model.*;
import model.Character;
import model.Event;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Story Archive Application
// base code credit: TellerApp but also now it should be unrecognizable lol
public class StoryArchiveApp {
    private static final String JSON_STORE = "./data/archive.json";
    public static final int MAX_SIZE = 8;
    private WorldLib worlds;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static String newLine = "<br/>";
    JFrame frame;
    JPanel panel;
    JLabel label;
    JPanel iconPanel;
    JPanel textPanel;

    // EFFECTS: initiates the Story Archive application
    public StoryArchiveApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        worlds = new WorldLib();
        initiateFrame();
        initiateTopPanels();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 100, 10, 100));
        panel.setLayout(new GridLayout(MAX_SIZE, 1));
        panel.setVisible(true);
        addToFrame(panel);
        openingMenu();
    }

    // MODIFIES: this
    // EFFECTS: initiates the top two panels (iconPanel and textPanel)
    private void initiateTopPanels() {
        iconPanel = new JPanel();
        textPanel = new JPanel();
        iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 20, 10));
        iconPanel.setLayout(new GridBagLayout());
        textPanel.setLayout(new GridBagLayout());
        addToFrameSouth(iconPanel);
        addToFrameNorth(textPanel);
        ImageIcon myIcon = new ImageIcon("./data/StoryArchive.png");
        Image img = myIcon.getImage().getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(img);
        JLabel icon = new JLabel();
        icon.setIcon(myIcon);
        iconPanel.add(icon);
        label = new JLabel();
        textPanel.add(label);
        iconPanel.setVisible(true);
        textPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initiates the frame
    private void initiateFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                frame.setVisible(true);
                resetPanel();
                newLabel("Are you sure you want to quit the application?");
                JButton proceed = printButton("Proceed");
                proceed.addActionListener(e -> leaveScreen());
                JButton goBack = printButton("Go back");
                goBack.addActionListener(e -> mainMenu());
            }
        });
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Story Archive");
    }

    // MODIFIES: this
    // EFFECTS: presents the exit screen
    private void leaveScreen() {
        resetPanel();
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
        newLabel("Thank you for using Story Archive, we hope to see you again!");
        JButton exit = printButton("Exit");
        exit.addActionListener(event -> System.exit(0));
    }

    // MODIFIES: this
    // EFFECTS: adds component to the frame
    private void addToFrame(Component c) {
        frame.add(c);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds component to the frame at NORTH
    private void addToFrameNorth(Component c) {
        frame.add(c, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds component to the frame at SOUTH
    private void addToFrameSouth(Component c) {
        frame.add(c, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this.label to given text
    private void newLabel(String text) {
        label.setText("<html>" + text + "</html>");
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this.label to have text added to it
    private void addLabel(String text) {
        String oldText = label.getText().substring(0, label.getText().length() - 8);
        label.setText(oldText + newLine + text + "</html>");
    }

    // MODIFIES: this
    // EFFECTS: deletes all components from this.panel
    private void resetPanel() {
        panel.removeAll();
        panel.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: initiates the opening menu
    private void openingMenu() {
        resetPanel();
        newLabel("Welcome to Story Archive!");
        JButton load = printButton("Load an Archive from file");
        load.addActionListener(e -> loadArchive());
        JButton skip = printButton("Skip");
        skip.addActionListener(e -> mainMenu());
        frame.pack();
        frame.setVisible(true);
        panel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: gives the main menu buttons
    private void mainMenu() {
        resetPanel();
        newLabel("What would you like to do?");
        loadButtons();
    }

    // MODIFIES: this
    // EFFECTS: loads menu buttons
    private void loadButtons() {
        JButton newWorld = printButton("Create a new world");
        newWorld.addActionListener(e -> createNewWorld());

        JButton editWorld = printButton("Edit an existing world");
        editWorld.addActionListener(e -> editExistingWorld());

        JButton viewWorld = printButton("View an existing world");
        viewWorld.addActionListener(e -> viewExistingWorld());

        JButton save = printButton("Save this Archive to file");
        save.addActionListener(e -> saveArchive());

        JButton load = printButton("Load an Archive from file");
        load.addActionListener(e -> loadArchive());
    }

    // MODIFIES: this
    // EFFECTS: adds the return to main menu button to panel
    private void returnMenuButton() {
        JButton menu = printButton("Return to Main Menu");
        menu.addActionListener(e -> mainMenu());
    }

    // MODIFIES: this
    // EFFECTS: creates a return to world view menu button and adds to panel
    private void worldViewButton(World world) {
        JButton worldView = printButton("Go back to world viewing menu");
        worldView.addActionListener(e -> viewWorld(world));
    }

    // MODIFIES: this
    // EFFECTS: creates a button with given text and adds it to panel
    private JButton printButton(String text) {
        JButton button = new JButton(text);
        panel.add(button);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates an edit world button with world name and adds it to panel
    private void editWorldButton(String name, World world) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> editWorld(world));
    }

    // MODIFIES: this
    // EFFECTS: creates a view world button with world name and adds it to panel
    private void viewWorldButton(String name, World world) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> viewWorld(world));
    }

    // MODIFIES: this
    // EFFECTS: creates an edit page button with page title and adds it to panel
    private void editPageButton(String name, PageLib pages, Page page) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> editPage(pages, page));
    }

    // MODIFIES: this
    // EFFECTS: creates a view detail button with page title and adds it to panel
    private void viewDetailPageButton(String name, PageLib pages, Page page, World world) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> viewDetailPage(pages, page, world));
    }

    // MODIFIES: this
    // EFFECTS: creates a view detail button with page title and adds it to panel
    private void viewStoryPageButton(String name, PageLib pages, Page page, World world) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> viewStoryPage(pages, page, world));
    }

    // MODIFIES: this
    // EFFECTS: creates an edit character button with character name and adds it to panel
    private void editCharacterButton(String name, CharacterLib charas, Character chara) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> editCharacter(charas, chara));
    }

    // MODIFIES: this
    // EFFECTS: creates a view character button with character name and adds it to panel
    private void viewCharacterButton(String name, CharacterLib charas, Character chara, World world) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(e -> viewCharacter(charas, chara, world));
    }

    // MODIFIES: this
    // EFFECTS: provides action for when capacity has been reached
    private void full(String things) {
        newLabel("You have reached the maximum amount of " + things + ".");
        resetPanel();
        returnMenu();
    }

    // EFFECTS: provides the already exists text
    private void already(String thing) {
        resetPanel();
        if (thing.equals("world") || thing.equals("character")) {
            newLabel("There is already a " + thing + " with this name.");
        } else {
            newLabel("There is already a " + thing + " with this title.");
        }
    }

    // EFFECTS: provides the return to menu text
    private void returnMenu() {
        resetPanel();
        addLabel("Returning to menu.");
        JButton ok = printButton("Ok");
        ok.addActionListener(e -> mainMenu());
    }

    // EFFECTS: provides the no existing things text
    private void notExist(String things) {
        resetPanel();
        newLabel("You have no existing " + things + ".");
        returnMenu();
    }

    // EFFECTS: provides the name/title entering text
    private String enterThe(String thing) {
        if (thing.equals("world") || thing.equals("character")) {
            return "Enter the name of your new " + thing + ": ";
        } else {
            return "Enter the title of your new " + thing + ": ";
        }
    }

    // EFFECTS: provides the creation confirmation text
    private String createNew(String thing, String name) {
        if (thing.equals("character") || thing.equals("world")) {
            return "Create a new " + thing + " named " + name + "?";
        } else {
            return "Create a new " + thing + " titled " + name + "?";
        }
    }

    // EFFECTS: provides the editing menu text
    private String partOfLibMenu(String worldName, String thing) {
        if (thing.equals("character")) {
            return "How would you like to edit " + worldName + "'s characters?";
        } else if (thing.equals("chapter")) {
            return "What part of " + worldName + "'s story would you like to edit?";
        } else {
            return "What part of " + worldName + "'s details would you like to edit?";
        }
    }

// ------------ below here are the action methods ------------

    // MODIFIES: this
    // EFFECTS: checks if new world is full, and if isn't then sends it to creating a new world with given name
    private void createNewWorld() {
        resetPanel();
        if (worlds.isFull()) {
            full("worlds");
        } else {
            newLabel(enterThe("world"));
            TextField textField = new TextField();
            panel.add(textField);
            textField.addActionListener(e -> {
                String name = textField.getText();
                createNewWorld(name);
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the new world creation menu where users can create new worlds
    private void createNewWorld(String name) {
        resetPanel();
        newLabel(createNew("world", name));
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            World newWorld = new World(name);
            try {
                worlds.addWorld(newWorld);
                editWorld(newWorld);
            } catch (AlreadyExistsException exc) {
                already("world");
                JButton ok = printButton("Ok");
                ok.addActionListener(event -> createNewWorld());
            }
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> createNewWorld());
    }

    // MODIFIES: this
    // EFFECTS: provides the menu of existing worlds for user to select and edit
    private void editExistingWorld() {
        resetPanel();
        if (worlds.isEmpty()) {
            notExist("worlds");
        } else {
            newLabel("Select the world you would like to edit:");
            for (World w : worlds.getWorldArrayList()) {
                editWorldButton(w.getName(), w);
            }
        }
    }

    // EFFECTS: provides the menu of existing worlds for user to select and view
    private void viewExistingWorld() {
        resetPanel();
        if (worlds.isEmpty()) {
            notExist("worlds");
        } else {
            newLabel("Select the world you would like to view:");
            for (World w : worlds.getWorldArrayList()) {
                viewWorldButton(w.getName(), w);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the world editing menu for a specific world
    private void editWorld(World world) {
        resetPanel();
        String worldName = world.getName();
        newLabel("What part of " + worldName + " would you like to edit?");

        JButton rename = printButton("Rename " + worldName);
        rename.addActionListener(e -> renameWorld(world));

        JButton details = printButton("Edit " + worldName + "'s world details");
        details.addActionListener(e -> editDetails(world.getDetailsLib(), world));

        JButton charas = printButton("Edit " + worldName + "'s characters");
        charas.addActionListener(e -> editCharas(world.getCharaLib(), world));

        JButton story = printButton("Edit " + worldName + "'s story chapters");
        story.addActionListener(e -> editStory(world.getStoryLib(), world));

        JButton delete = printButton("Delete " + worldName);
        delete.addActionListener(e -> removeWorld(world));

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides the world renaming menu for a specific world
    private void renameWorld(World world) {
        resetPanel();
        newLabel("Change the name of " + world.getName() + ": ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> {
            String name = textField.getText();
            renamedWorld(world, name);
        });
    }

    // MODIFIES: this
    // EFFECTS: provides the world renaming menu for a specific world
    private void renamedWorld(World world, String name) {
        resetPanel();
        newLabel("The new name will be: " + name + ".");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            world.rename(name);
            newLabel("Successfully renamed your world to " + name + ".");
            editWorld(world);
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> renameWorld(world));
    }

    // MODIFIES: this
    // EFFECTS: provides the world removing menu for a specific world
    private void removeWorld(World world) {
        resetPanel();
        newLabel("Permanently remove " + world.getName()
                + " from Archive?" + newLine + "(This action cannot be undone.)");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            try {
                worlds.removeWorld(world.getName());
            } catch (NotFoundException exception) {
                throw new RuntimeException(exception);
            }
            String worldName = world.getName().substring(0, 1).toUpperCase() + world.getName().substring(1);
            newLabel(worldName + " has been permanently removed from Archive.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> editWorld(world));
    }

    // MODIFIES: this
    // EFFECTS: provides the world viewing menu for a specific world
    private void viewWorld(World world) {
        resetPanel();
        String worldName = world.getName();
        newLabel("What part of " + worldName + " would you like to view?");

        JButton details = printButton("View " + worldName + "'s world details");
        details.addActionListener(e -> viewDetails(world.getDetailsLib(), world));

        JButton charas = printButton("View " + worldName + "'s characters");
        charas.addActionListener(e -> viewCharas(world.getCharaLib(), world));

        JButton story = printButton("View " + worldName + "'s story chapters");
        story.addActionListener(e -> viewStory(world.getStoryLib(), world));

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides a world details library editing menu
    private void editDetails(PageLib details, World world) {
        resetPanel();
        newLabel(partOfLibMenu(world.getName(), "page"));

        JButton newPage = printButton("Create new page");
        newPage.addActionListener(e -> createNewDetailsPage(details));

        JButton editPage = printButton("Edit existing page");
        editPage.addActionListener(e -> editExistingDetails(details));

        JButton worldEdit = printButton("Go back to World editor");
        worldEdit.addActionListener(e -> editWorld(world));

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides a character library editing menu
    private void editCharas(CharacterLib charas, World world) {
        resetPanel();
        newLabel(partOfLibMenu(world.getName(), "character"));

        JButton newOne = printButton("Create new character");
        newOne.addActionListener(e -> createNewCharacter(charas));

        JButton editOne = printButton("Edit existing character");
        editOne.addActionListener(e -> editExistingCharacter(charas));

        JButton worldEdit = printButton("Go back to World editor");
        worldEdit.addActionListener(e -> editWorld(world));

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides a story editing menu
    private void editStory(PageLib story, World world) {
        resetPanel();
        newLabel(partOfLibMenu(world.getName(), "chapter"));

        JButton newOne = printButton("Create new chapter");
        newOne.addActionListener(e -> createNewStoryChapter(story));

        JButton editOne = printButton("Edit existing chapter");
        editOne.addActionListener(e -> editExistingChapter(story));

        JButton worldEdit = printButton("Go back to World editor");
        worldEdit.addActionListener(e -> editWorld(world));

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the creation of a new details page
    private void createNewDetailsPage(PageLib details) {
        resetPanel();
        if (details.isFull()) {
            full("pages");
        } else {
            newLabel(enterThe("page"));
            TextField textField = new TextField();
            panel.add(textField);
            textField.addActionListener(e -> {
                String name = textField.getText();
                createNewDetailsPage(details, name);
            });
        }
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the creation of a new details page
    private void createNewDetailsPage(PageLib details, String name) {
        resetPanel();
        newLabel(createNew("page", name));

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            Page newDetail = new Page(name, "");
            try {
                details.addPage(newDetail);
                replaceDescription(newDetail);
            } catch (AlreadyExistsException exc) {
                already("page");
                JButton ok = printButton("Ok");
                ok.addActionListener(event -> createNewDetailsPage(details));
            }
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> createNewDetailsPage(details));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the creation of a new chapter
    private void createNewStoryChapter(PageLib story) {
        resetPanel();
        if (story.isFull()) {
            full("chapters");
        } else {
            newLabel(enterThe("chapter"));
            TextField textField = new TextField();
            panel.add(textField);
            textField.addActionListener(e -> {
                String name = textField.getText();
                createNewStoryChapter(story, name);
            });
        }
    }

    // MODIFIES: this, story
    // EFFECTS: provides the menu for the creation of a new chapter
    private void createNewStoryChapter(PageLib story, String name) {
        resetPanel();
        newLabel(createNew("chapter", name));

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            Page newChapter = new Page(name, "");
            try {
                story.addPage(newChapter);
                replaceDescription(newChapter);
            } catch (AlreadyExistsException exc) {
                already("chapter");
                JButton ok = printButton("Ok");
                ok.addActionListener(event -> createNewDetailsPage(story));
            }
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> createNewStoryChapter(story));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the creation of a new character
    private void createNewCharacter(CharacterLib charas) {
        resetPanel();
        if (charas.isFull()) {
            full("characters");
        } else {
            newLabel(enterThe("character"));
            TextField textField = new TextField();
            panel.add(textField);
            textField.addActionListener(e -> {
                String name = textField.getText();
                createNewCharacter(charas, name);
            });
        }
    }

    // MODIFIES: this, charas
    // EFFECTS: provides the menu for the creation of a new character
    private void createNewCharacter(CharacterLib charas, String name) {
        resetPanel();
        newLabel(createNew("character", name));

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            Character newChara = new Character(name, "");
            try {
                charas.addChara(newChara);
                replaceDescription(newChara);
            } catch (AlreadyExistsException exc) {
                already("character");
                JButton ok = printButton("Ok");
                ok.addActionListener(event -> createNewCharacter(charas));
            }
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> createNewCharacter(charas));
    }

    // MODIFIES: this, page
    // EFFECTS: provides the menu for adding to a page's description
    private void addDescription(Page page) {
        resetPanel();
        newLabel("Add text to " + page.getTitle() + ": ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> addedDescription(page, textField.getText()));
    }

    // MODIFIES: this, chara
    // EFFECTS: provides the menu for adding to a character's description
    private void addDescription(Character chara) {
        resetPanel();
        newLabel("Add text to " + chara.getName() + "'s description: ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> addedDescription(chara, textField.getText()));
    }

    // MODIFIES: this, page
    // EFFECTS: provides the menu for adding to a page's description once text was submitted
    private void addedDescription(Page page, String text) {
        resetPanel();
        newLabel("Confirm your submission: " + newLine + page.getBody() + " " + text);

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            page.addText(text);
            resetPanel();
            newLabel("Description updated.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> addDescription(page));
    }

    // MODIFIES: this, chara
    // EFFECTS: provides the menu for adding to a chara's description once text was submitted
    private void addedDescription(Character chara, String text) {
        resetPanel();
        newLabel("Confirm your submission: " + newLine + chara.getDesc() + " " + text);

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            chara.addDesc(text);
            resetPanel();
            newLabel("Description updated.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> addDescription(chara));
    }

    // MODIFIES: this, page
    // EFFECTS: provides the menu for changing a page's description
    private void replaceDescription(Page page) {
        resetPanel();
        newLabel("Change the text of " + page.getTitle() + ": ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> replacedDescription(page, textField.getText()));
    }

    // MODIFIES: this, chara
    // EFFECTS: provides the menu for changing a character's description
    private void replaceDescription(Character chara) {
        resetPanel();
        newLabel("Change the text of " + chara.getName() + "'s description: ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> replacedDescription(chara, textField.getText()));
    }

    // MODIFIES: this, page
    // EFFECTS: provides the menu for replacing a page's description once text was submitted
    private void replacedDescription(Page page, String text) {
        resetPanel();
        newLabel("Confirm your submission: " + " " + text);

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            page.replaceText(text);
            resetPanel();
            newLabel("Description updated.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> replaceDescription(page));
    }

    // MODIFIES: this, chara
    // EFFECTS: provides the menu for replacing a chara's description once text was submitted
    private void replacedDescription(Character chara, String text) {
        resetPanel();
        newLabel("Confirm your submission: " + text);

        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            chara.replaceDesc(text);
            resetPanel();
            newLabel("Description updated.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> replaceDescription(chara));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for changing a page's title
    private void renamePage(Page page) {
        resetPanel();
        newLabel("Change the title of " + page.getTitle() + ": ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> {
            String name = textField.getText();
            renamedPage(page, name);
        });
    }

    // MODIFIES: this, page
    // EFFECTS: provides the page renaming menu for a specific page
    private void renamedPage(Page page, String text) {
        resetPanel();
        newLabel("The new title will be: " + text + ".");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            page.rename(text);
            newLabel("Successfully retitled your page to " + text + ".");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> renamePage(page));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for changing a character's name
    private void renameCharacter(Character chara) {
        resetPanel();
        newLabel("Changing the name of " + chara.getName() + ": ");
        TextField textField = new TextField();
        panel.add(textField);
        textField.addActionListener(e -> {
            String name = textField.getText();
            renamedChara(chara, name);
        });
    }

    // MODIFIES: this, chara
    // EFFECTS: provides the character renaming menu for a specific character
    private void renamedChara(Character chara, String text) {
        resetPanel();
        newLabel("The new name will be: " + text + ".");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            chara.rename(text);
            newLabel("Successfully renamed your character to " + text + ".");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> renameCharacter(chara));
    }

    // MODIFIES: details
    // EFFECTS: provides the menu for the editing of existing detail pages
    private void editExistingDetails(PageLib details) {
        resetPanel();
        if (details.isEmpty()) {
            notExist("world detail pages");
        } else {
            newLabel("Select the world detail page you would like to edit:");
            for (Page p : details.getPageArrayList()) {
                editPageButton(p.getTitle(), details, p);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of existing detail pages
    private void viewDetails(PageLib details, World world) {
        resetPanel();
        if (details.isEmpty()) {
            notExist("world detail pages");
        } else {
            newLabel("Select the world detail page you would like to view:");
            for (Page p : details.getPageArrayList()) {
                viewDetailPageButton(p.getTitle(), details, p, world);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu after viewing a detail
    private void viewNextDetails(PageLib details, World world) {
        resetPanel();
        newLabel("What would you like to do next?");
        JButton more = printButton("View more details");
        more.addActionListener(e -> viewDetails(details, world));

        worldViewButton(world);

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides the menu after viewing a story chapter
    private void viewNextStory(PageLib story, World world) {
        resetPanel();
        newLabel("What would you like to do next?");

        JButton more = printButton("View more chapters");
        more.addActionListener(e -> viewStory(story, world));

        worldViewButton(world);

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides the menu after viewing a character
    private void viewNextChara(CharacterLib charas, World world) {
        resetPanel();
        newLabel("What would you like to do next?");

        JButton more = printButton("View more characters");
        more.addActionListener(e -> viewCharas(charas, world));

        worldViewButton(world);

        returnMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the editing of existing characters
    private void editExistingCharacter(CharacterLib charas) {
        resetPanel();
        if (charas.isEmpty()) {
            notExist("characters");
        } else {
            newLabel("Select the character you would like to edit:");
            for (Character c : charas.getCharaArrayList()) {
                editCharacterButton(c.getName(), charas, c);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of existing characters
    private void viewCharas(CharacterLib charas, World world) {
        resetPanel();
        if (charas.isEmpty()) {
            notExist("characters");
        } else {
            newLabel("Select the character you would like to view:");
            for (Character c : charas.getCharaArrayList()) {
                viewCharacterButton(c.getName(), charas, c, world);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the editing of existing chapters
    private void editExistingChapter(PageLib story) {
        resetPanel();
        if (story.isEmpty()) {
            notExist("chapters");
        } else {
            newLabel("Select the chapter you would like to edit:");
            for (Page p : story.getPageArrayList()) {
                editPageButton(p.getTitle(), story, p);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of existing chapters
    private void viewStory(PageLib story, World world) {
        resetPanel();
        if (story.isEmpty()) {
            notExist("chapters");
        } else {
            newLabel("Select the chapter you would like to view:");
            for (Page p : story.getPageArrayList()) {
                viewStoryPageButton(p.getTitle(), story, p, world);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the editing of a page
    private void editPage(PageLib pages, Page page) {
        resetPanel();
        String name = page.getTitle();
        newLabel("How would you like to edit " + name + "?");

        JButton reTitle = printButton("Re-title " + name);
        reTitle.addActionListener(e -> renamePage(page));

        JButton addDesc = printButton("Add to " + name + "'s text");
        addDesc.addActionListener(e -> addDescription(page));

        JButton repDesc = printButton("Replace " + name + "'s text");
        repDesc.addActionListener(e -> replaceDescription(page));

        JButton delete = printButton("Delete " + name);
        delete.addActionListener(e -> removePage(pages, page));

        returnMenuButton();
    }

    // MODIFIES: this, pages
    // EFFECTS: provides the page removing menu for a specific page
    private void removePage(PageLib pages, Page page) {
        resetPanel();
        newLabel("Permanently remove " + page.getTitle()
                + " from Archive?" + newLine + "(This action cannot be undone.)");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            try {
                pages.removePage(page.getTitle());
            } catch (NotFoundException exception) {
                throw new RuntimeException(exception);
            }
            String pageTitle = page.getTitle().substring(0, 1).toUpperCase() + page.getTitle().substring(1);
            newLabel(pageTitle + " has been permanently removed from Archive.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> editPage(pages, page));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of a story page
    private void viewStoryPage(PageLib story, Page chapter, World world) {
        resetPanel();
        newLabel(chapter.getTitle() + ": " + newLine + chapter.getBody());
        JButton ok = printButton("Proceed");
        ok.addActionListener(e -> viewNextStory(story, world));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of a detail page
    private void viewDetailPage(PageLib details, Page detail, World world) {
        resetPanel();
        newLabel(detail.getTitle() + ": " + newLine + detail.getBody());
        JButton ok = printButton("Proceed");
        ok.addActionListener(e -> viewNextDetails(details, world));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the viewing of a character
    private void viewCharacter(CharacterLib charas, Character chara, World world) {
        resetPanel();
        newLabel(chara.getName() + ": " + newLine + chara.getDesc());
        JButton ok = printButton("Proceed");
        ok.addActionListener(e -> viewNextChara(charas, world));
    }

    // MODIFIES: this
    // EFFECTS: provides the menu for the editing of a character
    private void editCharacter(CharacterLib charas, Character chara) {
        resetPanel();
        String name = chara.getName();
        newLabel("How would you like to edit " + name + "?");

        JButton reTitle = printButton("Rename " + name);
        reTitle.addActionListener(e -> renameCharacter(chara));

        JButton addDesc = printButton("Add to " + name + "'s description");
        addDesc.addActionListener(e -> addDescription(chara));

        JButton repDesc = printButton("Replace " + name + "'s description");
        repDesc.addActionListener(e -> replaceDescription(chara));

        JButton delete = printButton("Delete " + name);
        delete.addActionListener(e -> removeCharacter(charas, chara));

        returnMenuButton();
    }

    // MODIFIES: this, charas
    // EFFECTS: provides the character removing menu for a specific character
    private void removeCharacter(CharacterLib charas, Character chara) {
        resetPanel();
        newLabel("Permanently remove " + chara.getName()
                + " from Archive?" + newLine + "(This action cannot be undone.)");
        JButton proceed = printButton("Proceed");
        proceed.addActionListener(e -> {
            try {
                charas.removeChara(chara.getName());
            } catch (NotFoundException exception) {
                throw new RuntimeException(exception);
            }
            String charaName = chara.getName().substring(0, 1).toUpperCase() + chara.getName().substring(1);
            newLabel(charaName + " has been permanently removed from Archive.");
            returnMenu();
        });
        JButton goBack = printButton("Go back");
        goBack.addActionListener(e -> editCharacter(charas, chara));
    }

    // MODIFIES: this
    // EFFECTS: saves the current archive to file
    private void saveArchive() {
        resetPanel();
        try {
            jsonWriter.open();
            jsonWriter.write(worlds);
            jsonWriter.close();
            newLabel("Saved current Story Archive to " + JSON_STORE + ".");
            returnMenu();
        } catch (FileNotFoundException e) {
            newLabel("Unable to write to file: " + JSON_STORE + ".");
            returnMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads archive from file
    private void loadArchive() {
        resetPanel();
        try {
            worlds = jsonReader.read();
            newLabel("Loaded up Story Archive from " + JSON_STORE + ".");
            returnMenu();
        } catch (IOException e) {
            newLabel("Unable to read from file: " + JSON_STORE + ".");
            returnMenu();
        }
    }
}

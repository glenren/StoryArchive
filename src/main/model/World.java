package model;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;

// a story world
public class World {
    private String name;
    private CharacterLib characters;
    private PageLib story;
    private PageLib details;

    // EFFECTS: creates a new world named name with empty libraries
    public World(String name) {
        this.name = name;
        characters = new CharacterLib();
        story = new PageLib();
        details = new PageLib();
    }

    // EFFECTS: returns the name of the world
    public String getName() {
        return name;
    }

    // EFFECTS: returns the character library of the world
    public CharacterLib getCharaLib() {
        return characters;
    }

    // EFFECTS: returns the story library of the world
    public PageLib getStoryLib() {
        return story;
    }

    // EFFECTS: returns the details library of the world
    public PageLib getDetailsLib() {
        return details;
    }

    // MODIFIES: this
    // EFFECTS: changes world name to name
    public void rename(String name) {
        this.name = name;
    }
}

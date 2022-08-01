package model;

import org.json.JSONArray;
import org.json.JSONObject;

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

    // EFFECTS: returns this world as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("characters", charaToJson());
        json.put("story", storyToJson());
        json.put("details", detailsToJson());
        return json;
    }

    // EFFECTS: returns characters in this WorldLib as a JSON array
    private JSONArray charaToJson() {
        return characters.toJson();
    }

    // EFFECTS: returns story in this WorldLib as a JSON array
    private JSONArray storyToJson() {
        return story.toJson();
    }

    // EFFECTS: returns details in this WorldLib as a JSON array
    private JSONArray detailsToJson() {
        return details.toJson();
    }
}

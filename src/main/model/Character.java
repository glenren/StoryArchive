package model;

import org.json.JSONObject;

// represents a character within a story world
public class Character {
    private String name;
    private String description;

    // EFFECTS: creates a new character with name name and description desc
    public Character(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

    // EFFECTS: returns character name
    public String getName() {
        return name;
    }

    // EFFECTS: returns character description
    public String getDesc() {
        return description;
    }

    // MODIFIES: this
    // EFFECTS: changes character's name to name
    public void rename(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds text to character's existing description
    public void addDesc(String text) {
        String newDesc = description + " " + text;
        this.description = newDesc;
    }

    // MODIFIES: this
    // EFFECTS: replaces character's existing description with newDesc
    public void replaceDesc(String newDesc) {
        this.description = newDesc;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("desc", description);
        return json;
    }
}

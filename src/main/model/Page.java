package model;

import org.json.JSONObject;

// a page of information with a title and body text
public class Page {
    private String title;
    private String body;

    // EFFECTS: creates a new page with title title and body text
    public Page(String title, String text) {
        this.title = title;
        this.body = text;
    }

    // EFFECTS: returns page title
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns page body
    public String getBody() {
        return body;
    }

    // MODIFIES: this
    // EFFECTS: renames page title to newTitle
    public void rename(String newTitle) {
        this.title = newTitle;
    }

    // MODIFIES: this
    // EFFECTS: adds new text into page body
    public void addText(String text) {
        String newBody = body + " " + text;
        this.body = newBody;
    }

    // MODIFIES: this
    // EFFECTS: replaces old body with text
    public void replaceText(String text) {
        this.body = text;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("body", body);
        return json;
    }
}

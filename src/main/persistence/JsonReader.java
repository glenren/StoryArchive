package persistence;

import exceptions.AlreadyExistsException;
import model.*;
import model.Character;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// base code: JsonReader in JSON demo file for project
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads world library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorldLib read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorldLib(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder universeBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> universeBuilder.append(s));
        }

        return universeBuilder.toString();
    }

    // EFFECTS: parses worldLib from JSON object and returns it
    private WorldLib parseWorldLib(JSONObject jsonObject) {
        WorldLib worldLib = new WorldLib();
        addWorld(worldLib, jsonObject);
        return worldLib;
    }

    // MODIFIES: worldLib
    // EFFECTS: parses world files from JSON object and adds them to worldLib
    private void addWorld(WorldLib worldLib, JSONObject obj) {
        JSONArray jsonArray = obj.getJSONArray("worlds");
        for (Object json : jsonArray) {
            JSONObject worldObj = (JSONObject) json;
            String name = worldObj.getString("name");
            JSONArray details = worldObj.getJSONArray("details");
            JSONArray story = worldObj.getJSONArray("story");
            JSONArray characters = worldObj.getJSONArray("characters");
            World world = new World(name);
            try {
                worldLib.addWorld(world);
            } catch (AlreadyExistsException e) {
                throw new RuntimeException(e);
            }
            addPages(world.getDetailsLib(), details);
            addPages(world.getStoryLib(), story);
            addCharas(world.getCharaLib(), characters);
        }
    }

    // MODIFIES: pages
    // EFFECTS: parses page from jsonArray and adds them to pages
    private void addPages(PageLib pages, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextPage = (JSONObject) json;
            addPage(pages, nextPage);
        }
    }

    // MODIFIES: pages
    // EFFECTS: parses page from pageObj and adds it to pages
    private void addPage(PageLib pages, JSONObject pageObj) {
        String title = pageObj.getString("title");
        String body = pageObj.getString("body");
        Page page = new Page(title, body);
        try {
            pages.addPage(page);
        } catch (AlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: characters
    // EFFECTS: parses character from jsonArray and adds them to characters
    private void addCharas(CharacterLib charas, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextChara = (JSONObject) json;
            addChara(charas, nextChara);
        }
    }

    // MODIFIES: characters
    // EFFECTS: parses chara from charaObj and adds it to characters
    private void addChara(CharacterLib charas, JSONObject charaObj) {
        String name = charaObj.getString("name");
        String desc = charaObj.getString("desc");
        Character chara = new Character(name, desc);
        try {
            charas.addCharas(chara);
        } catch (AlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }
}

package model;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// a collection of all the user's created worlds
// base code: Lab 5 IncidentQueue
public class WorldLib {
    public static final int MAX_SIZE = 10;

    private ArrayList<World> worlds;

    // EFFECTS: creates an empty world library
    public WorldLib() {
        this.worlds = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a world to worlds if worlds isn't full and return true, false if full
    public boolean addWorld(World newWorld) throws AlreadyExistsException {
        if (isWorldInLib(newWorld.getName())) {
            throw new AlreadyExistsException();
        } else {
            boolean notFull = !isFull();
            if (notFull) {
                worlds.add(newWorld);
                return true;
            } else {
                return false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes world named name from worlds
    public void removeWorld(String name) throws NotFoundException {
        if (!isWorldInLib(name)) {
            throw new NotFoundException();
        } else {
            World remWorld = getWorld(name);
            worlds.remove(remWorld);
        }
    }

    // EFFECTS: searches world library for a matching world name to name and returns it
    public World getWorld(String name) throws NotFoundException {
        if (!isWorldInLib(name)) {
            throw new NotFoundException();
        } else {
            World found = new World("No");
            for (World world : worlds) {
                if (world.getName().equals(name)) {
                    found = world;
                }
            }
            return found;
        }
    }

    // EFFECTS: searches world library for a matching world name to name
    //          and returns true if it exists, false otherwise
    public boolean isWorldInLib(String name) {
        World found = new World("No");
        for (World world : worlds) {
            if (world.getName().equals(name)) {
                found = world;
            }
        }
        return found.getName().equals(name);
    }

    // EFFECTS: returns the amount of worlds currently in the world library
    public int length() {
        return worlds.size();
    }

    // EFFECTS: returns true if worlds is empty, false otherwise
    public boolean isEmpty() {
        return worlds.isEmpty();
    }

    // EFFECTS: returns true if worlds is full, false otherwise
    public boolean isFull() {
        return length() == MAX_SIZE;
    }

    // EFFECTS: produces a string of all the names of worlds in world library
    public String getWorldList() {
        String list = "";
        for (World world : worlds) {
            if (list.equals("")) {
                list = world.getName();
            } else {
                list = list + ", " + world.getName();
            }
        }
        return list;
    }

    // EFFECTS: returns this WorldLib as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("worlds", worldsToJson());
        return json;
    }

    // EFFECTS: returns worlds in this WorldLib as a JSON array
    private JSONArray worldsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (World w : worlds) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}

package model;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import org.json.JSONArray;

import java.util.ArrayList;

// a collection of characters
public class CharacterLib {
    public static final int MAX_SIZE = 10;

    private ArrayList<Character> charas;

    // EFFECTS: creates a character library (a list of characters)
    public CharacterLib() {
        this.charas = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a character to charas if charas isn't full and return true, false if full
    public boolean addChara(Character newChara) throws AlreadyExistsException {
        if (isCharaInLib(newChara.getName())) {
            throw new AlreadyExistsException();
        } else {
            boolean notFull = !isFull();
            if (notFull) {
                charas.add(newChara);
                return true;
            } else {
                return false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes character named name from characters
    public void removeChara(String name) throws NotFoundException {
        if (!isCharaInLib(name)) {
            throw new NotFoundException();
        } else {
            Character remChara = getChara(name);
            charas.remove(remChara);
        }
    }

    // EFFECTS: searches character library for a character with matching name to name and returns it
    public Character getChara(String name) throws NotFoundException {
        name = name.toLowerCase();
        if (!isCharaInLib(name)) {
            throw new NotFoundException();
        } else {
            Character found = new Character("No", "No");
            for (Character chara : charas) {
                if (chara.getName().toLowerCase().equals(name)) {
                    found = chara;
                }
            }
            return found;
        }
    }

    // EFFECTS: searches character library for a character with matching name to name
    //          and returns true if it exists, false otherwise
    public boolean isCharaInLib(String name) {
        name = name.toLowerCase();
        Boolean found = false;
        for (Character chara : charas) {
            if (chara.getName().toLowerCase().equals(name)) {
                found = true;
            }
        }
        return found;
    }

    // EFFECTS: returns the amount of characters currently in the character library
    public int length() {
        return charas.size();
    }

    // EFFECTS: returns true if charas is empty, false otherwise
    public boolean isEmpty() {
        return charas.isEmpty();
    }

    // EFFECTS: returns true if charas is full, false otherwise
    public boolean isFull() {
        return length() == MAX_SIZE;
    }

    // EFFECTS: produces a string of all the names of characters in character library
    public String getCharaList() {
        String list = "";
        for (Character chara : charas) {
            if (list.equals("")) {
                list = chara.getName();
            } else {
                list = list + ", " + chara.getName();
            }
        }
        return list;
    }

    // EFFECTS: returns characters in charas as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Character c : charas) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}

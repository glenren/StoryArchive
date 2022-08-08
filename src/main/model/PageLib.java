package model;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import org.json.JSONArray;

import java.util.ArrayList;

// a collection of pages
// base code: WorldLib which has base code Lab 5 Incident Queue
public class PageLib {
    public static final int MAX_SIZE = 10;

    private ArrayList<Page> pages;

    // EFFECTS: creates an empty page library
    public PageLib() {
        this.pages = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a page to pages if pages isn't full and return true, false if full
    public boolean addPage(Page newPage) throws AlreadyExistsException {
        if (isPageInLib(newPage.getTitle())) {
            throw new AlreadyExistsException();
        } else {
            boolean notFull = !isFull();
            if (notFull) {
                pages.add(newPage);
                return true;
            } else {
                return false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes page titled title from pages
    public void removePage(String title) throws NotFoundException {
        if (!isPageInLib(title)) {
            throw new NotFoundException();
        } else {
            Page remPage = getPage(title);
            pages.remove(remPage);
        }
    }

    // EFFECTS: searches pages for a page with matching title to title and returns it
    public Page getPage(String title) throws NotFoundException {
        title = title.toLowerCase();
        if (!isPageInLib(title)) {
            throw new NotFoundException();
        } else {
            Page found = new Page("No", "No");
            for (Page page : pages) {
                if (page.getTitle().toLowerCase().equals(title)) {
                    found = page;
                }
            }
            return found;
        }
    }

    // EFFECTS: searches pages for a page with matching title to title
    //          and returns true if it exists, false otherwise
    public boolean isPageInLib(String title) {
        title = title.toLowerCase();
        Boolean found = false;
        for (Page page : pages) {
            if (page.getTitle().toLowerCase().equals(title)) {
                found = true;
            }
        }
        return found;
    }

    // EFFECTS: returns the amount of pages currently in the page library
    public int length() {
        return pages.size();
    }

    // EFFECTS: returns true if pages is empty, false otherwise
    public boolean isEmpty() {
        return pages.isEmpty();
    }

    // EFFECTS: returns true if pages is full, false otherwise
    public boolean isFull() {
        return length() == MAX_SIZE;
    }

    // EFFECTS: produces a string of all the names of pages in page library
    public String getPageList() {
        String list = "";
        for (Page page : pages) {
            if (list.equals("")) {
                list = page.getTitle();
            } else {
                list = list + ", " + page.getTitle();
            }
        }
        return list;
    }

    // EFFECTS: produces an arraylist of all the pages in page library
    public ArrayList<Page> getPageArrayList() {
        ArrayList<Page> pages = new ArrayList<>();
        pages.addAll(this.pages);
        return pages;
    }

    // EFFECTS: returns pages in pages as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Page p : pages) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}

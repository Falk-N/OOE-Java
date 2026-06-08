package todoapp;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    //Attributes
    private int id;
    private String name;
    private List<ListEntry> entries;


    //Constructor
    public ToDoList (int id, String name) {
        this.id = id;
        this.name = name;
        this.entries = new ArrayList<>();
    }


    //Set

    public void setName (String name) {
        this.name = name;
    }

    public void addEntry (ListEntry entry) {
        entries.add(entry);
    }

    public void removeEntry (int entryId) {
        for (int i = 0; i < entries.size(); i++) {
            if(entries.get(i).getId() == entryId) {
                entries.remove(i);
                break;
            }
        }
    }


    //Get

    public int getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public List<ListEntry> getEntries () {
        return this.entries;
    }
}
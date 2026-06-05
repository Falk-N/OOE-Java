package todoapp;

import java.util.ArrayList;
import java.util.List;

public class List {
    //Attributes
    private int id;
    private String name;
    private List<ListEntry> entries;


    //Constructor
    public void ListEntry (int id, String name) {
        this.id = id;
        this.name = name;
        this.entries = new ArrayList<>();
    }


    //Set

    public void setName (String name) {
        this.name = name;
    }

    public void addEntry (ListEntry entry) {

    }

    public void removeEntry (int entryId) {

    }


    //Get

    public int getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public List<ListEntry> getList () {
        return this.entries;
    }
}
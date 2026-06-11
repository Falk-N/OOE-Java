package todoapp;

import java.util.ArrayList;

public class ToDoList {
    //Attributes
    private String name;
    private ArrayList<ListEntry> entries;

    //Constructor
    public ToDoList(String name) {
        this.name = name;
        this.entries = new ArrayList<>();
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }

    public void addEntry(ListEntry entry) {
        entries.add(entry);
    }

    //Getter
    public String getName() {
        return this.name;
    }

    public ArrayList<ListEntry> getEntries() {
        return this.entries;
    }
}
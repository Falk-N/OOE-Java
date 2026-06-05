package todoapp;

public class ListEntry {
    //Attributes
    private int id;
    private String title;
    private boolean completed;


    //Constructor
    public void ListEntry (int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }


    //Set

    public void setTitle (String title) {
        this.title = title;
    }

    public void setCompleted (boolean completed) {
        this.completed = completed;
    }


    //Get

    public int getId () {
        return this.id;
    }

    public String getTitle () {
        return this.title;
    }

    public boolean getCompleted () {
        return this.completed;
    }
}
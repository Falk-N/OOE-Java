package todoapp;

public class ListEntry {
    //Attributes
    private String title;
    private boolean completed;

    //Constructor
    public ListEntry(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    //Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    //Getter
    public String getTitle() {
        return this.title;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    @Override
    public String toString() {
        return this.title;
    }
}

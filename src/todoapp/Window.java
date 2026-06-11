package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Window extends JFrame {

    private ToDoList currentList;
    private List<ToDoList> lists = new ArrayList<>();
    private JPanel overviewContainer;
    private JLabel listName;
    private JPanel listContainer;

    public Window() {
        setSize(600, 400);
        setTitle("TODO-App");

        JPanel pc = new JPanel();
        JPanel pl = new JPanel();
        JPanel pr = new JPanel();

        BorderLayout b = new BorderLayout();

        pc.setLayout(b);
        pl.setLayout(new BoxLayout(pl, BoxLayout.PAGE_AXIS));
        pr.setLayout(new BoxLayout(pr, BoxLayout.PAGE_AXIS));


        JButton buttonAddTask = new JButton("Aufgabe erstellen");
        buttonAddTask.addActionListener(e -> addTask());

        JButton buttonDeleteList = new JButton("Liste löschen");
        buttonDeleteList.addActionListener(e -> deleteList());

        JButton createListButton = new JButton("Liste erstellen");
        createListButton.addActionListener(e -> createList());

        overviewContainer = new JPanel();
        overviewContainer.setLayout(new BoxLayout(overviewContainer, BoxLayout.Y_AXIS));

        JScrollPane overviewScroll = new JScrollPane(overviewContainer);

        pl.add(createListButton, BorderLayout.NORTH);
        pl.add(overviewScroll, BorderLayout.CENTER);

        add(pl, BorderLayout.LINE_START);

        renderOverview();

        pr.add(buttonAddTask);
        pr.add(buttonDeleteList);
        add(pr, BorderLayout.LINE_END);

        listName = new JLabel ("Nichts ausgewählt");

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        JScrollPane listScroll = new JScrollPane(listContainer);

        pc.add(listName, BorderLayout.NORTH);
        pc.add(listScroll, BorderLayout.CENTER);

        add(pc, BorderLayout.CENTER);

        renderList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void renderList () {
        listContainer.removeAll();

        if(currentList == null) {
            listContainer.revalidate();
            listContainer.repaint();
            return;
        }

        List<ListEntry> sortedEntries = new ArrayList<>(currentList.getEntries());
        sortedEntries.sort((first, second) -> Boolean.compare(first.getCompleted(), second.getCompleted()));

        for (ListEntry entry : sortedEntries) {
            JPanel listEntry = new JPanel(new BorderLayout());
            listEntry.setMinimumSize(new Dimension(0, 40));
            listEntry.setPreferredSize(new Dimension(0, 40));
            listEntry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            listEntry.setAlignmentX(LEFT_ALIGNMENT);

            JLabel title = new JLabel(entry.getTitle());
            JButton delete = new JButton("X");
            JButton done = new JButton("✓");

            listEntry.add(title, BorderLayout.CENTER);
            listEntry.add(delete, BorderLayout.EAST);
            listEntry.add(done, BorderLayout.WEST);

            delete.addActionListener(e -> {
                if ((JOptionPane.showConfirmDialog(this, "Aufgabe löschen?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION)) == 0) {
                    currentList.getEntries().remove(entry);
                    renderList();
                }
            });

            done.addActionListener(e -> {
                entry.setCompleted(!entry.getCompleted());
                renderList();
            });

            if(entry.getCompleted()) {
                listEntry.setBackground(Color.GRAY);
            }

            listContainer.add(listEntry);
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

    private void renderOverview() {
        overviewContainer.removeAll();

        for(ToDoList list : lists) {
            JPanel listPanel = new JPanel(new BorderLayout());
            listPanel.setMinimumSize(new Dimension(0, 40));
            listPanel.setPreferredSize(new Dimension(0, 40));
            listPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            listPanel.setAlignmentX(LEFT_ALIGNMENT);

            JButton selectButton = new JButton(list.getName());
            JButton deleteButton = new JButton("X");

            selectButton.addActionListener(e -> {
                currentList = list;
                listName.setText(list.getName());
                renderList();
            });

            //delete button

            listPanel.add(selectButton, BorderLayout.CENTER);
            listPanel.add(deleteButton, BorderLayout.EAST);
            overviewContainer.add(listPanel);
        }

        overviewContainer.revalidate();
        overviewContainer.repaint();
    }

    public void createList () {
        String name =JOptionPane.showInputDialog (this, "Listenname:");
        if(name == null || name.isBlank()) {
            return;
        }

        int id = lists.size() + 1;

        ToDoList newList = new ToDoList(id, name);
        lists.add(newList);
        renderOverview();
    }

    public void addTask() {
        if(currentList == null) {
            JOptionPane.showMessageDialog (this,"Keine Liste ausgewählt!", "Achtung", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String s = (String)JOptionPane.showInputDialog(this, "", "Aufgabe erstellen", JOptionPane.PLAIN_MESSAGE, null, null, "Neue Aufgabe");
        if (s != null) {
            currentList.addEntry(new ListEntry(1, s));
        }

        renderList();

    }

    public void deleteList() {
        System.out.println(currentList.getEntries());

    }

}

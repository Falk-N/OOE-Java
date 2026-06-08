package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Window extends JFrame {

    private ToDoList list;
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
        pl.setLayout(b);
        pr.setLayout(new BoxLayout(pr, BoxLayout.PAGE_AXIS));


        JButton buttonAddTask = new JButton("Aufgabe erstellen");
        buttonAddTask.addActionListener(e -> addTask());

        JButton buttonDeleteList = new JButton("Liste löschen");
        buttonDeleteList.addActionListener(e -> deleteList());



        list = new ToDoList(1, "List 1");


        //Listeneintrge zu JList-Komponente hinzufügen
        DefaultListModel<ListEntry> model = new DefaultListModel<>();
        for (ListEntry entry : list.getEntries()) {
            model.addElement(entry);
        }

        JPanel p = new JPanel();
        pl.add(p);
        add(pl, BorderLayout.LINE_START);

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

        List<ListEntry> sortedEntries = new ArrayList<>(list.getEntries());
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
                    list.getEntries().remove(entry);
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

    public void addTask() {
        String s = (String)JOptionPane.showInputDialog(this, "", "Aufgabe erstellen", JOptionPane.PLAIN_MESSAGE, null, null, "Neue Aufgabe");
        if (s != null) {
            list.addEntry(new ListEntry(1, s));
        }

        renderList();

    }

    public void deleteList() {
        System.out.println(list.getEntries());

    }

}

package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;

public class Window extends JFrame {

    private ToDoList list;
    private JLabel listName;
    private JPanel listContainer;

    public Window() {
        setSize(600, 400);

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
        list.addEntry(new ListEntry(1, "Milch kaufen"));
        list.addEntry(new ListEntry(2, "Hausaufgaben machen"));


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

        for (ListEntry entry : list.getEntries()) {
            JPanel listEntry = new JPanel(new BorderLayout());

            JLabel title = new JLabel(entry.getTitle());
            JButton delete = new JButton("X");

            listEntry.add(title, BorderLayout.CENTER);
            listEntry.add(delete, BorderLayout.EAST);

            delete.addActionListener(e -> {
                list.getEntries().remove(entry);
                renderList();
            });

            listContainer.add(listEntry);
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

    public void addTask() {
        String s = (String)JOptionPane.showInputDialog(this, "", "Neue Aufgabe", JOptionPane.PLAIN_MESSAGE, null, null, "Neue Aufgabe");
        if (s != null) {
            list.addEntry(new ListEntry(1, s));
        }

        System.out.println(list.getEntries());

    }

    public void deleteList() {
        System.out.println(list.getEntries());

    }

}

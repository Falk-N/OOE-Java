package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Window extends JFrame {
    private ToDoList currentList;
    private List<ToDoList> lists = new ArrayList<>();
    private JPanel overviewContainer;
    private JLabel labelListName;
    private JPanel listContainer;
    private String fileName;

    public Window() {
        super("ToDo-Liste");

        //Dateiname zum Speichern der Listen
        fileName = "tasks.txt";

        //Initiale Liste erstellen
        lists.add(new ToDoList("Liste 1"));
        currentList = lists.get(0);

        //Listeneintrge zu JList-Komponente hinzufügen
        DefaultListModel<ListEntry> model = new DefaultListModel<>();
        for (ListEntry entry : currentList.getEntries()) {
            model.addElement(entry);
        }

        //Panel Links
        JPanel pl = new JPanel();
        pl.setLayout(new BoxLayout(pl, BoxLayout.PAGE_AXIS));

        JButton createListButton = new JButton("Liste erstellen");
        createListButton.addActionListener(e -> createList());
        pl.add(createListButton, BorderLayout.NORTH);

        overviewContainer = new JPanel();
        overviewContainer.setLayout(new BoxLayout(overviewContainer, BoxLayout.Y_AXIS));
        JScrollPane overviewScroll = new JScrollPane(overviewContainer);
        pl.add(overviewScroll, BorderLayout.CENTER);

        getContentPane().add(pl, BorderLayout.LINE_START);

        //Panel Mitte
        JPanel pc = new JPanel();
        pc.setLayout(new BorderLayout());

        labelListName = new JLabel();
        pc.add(labelListName, BorderLayout.NORTH);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        JScrollPane listScroll = new JScrollPane(listContainer);
        pc.add(listScroll, BorderLayout.CENTER);

        getContentPane().add(pc, BorderLayout.CENTER);

        //Panel Rechts
        JPanel pr = new JPanel();
        pr.setLayout(new BoxLayout(pr, BoxLayout.PAGE_AXIS));

        JButton buttonAddTask = new JButton("Aufgabe erstellen");
        buttonAddTask.addActionListener(e -> addTask());
        pr.add(buttonAddTask);

        JButton buttonExportList = new JButton("Liste exportieren");
        buttonExportList.addActionListener(e -> exportList());
        pr.add(buttonExportList);

        JButton buttonImportList = new JButton("Liste importieren");
        buttonImportList.addActionListener(e -> importList());
        pr.add(buttonImportList);

        getContentPane().add(pr, BorderLayout.LINE_END);

        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        renderOverview();
        renderList();
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

        if (currentList != null) {
            labelListName.setText(currentList.getName());
        } else {
            labelListName.setText("Nichts ausgewählt");
        }
        

        for(ToDoList list : lists) {
            JPanel listPanel = new JPanel(new BorderLayout());
            listPanel.setMinimumSize(new Dimension(0, 40));
            listPanel.setPreferredSize(new Dimension(0, 40));
            listPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            listPanel.setAlignmentX(LEFT_ALIGNMENT);

            JButton selectButton = new JButton(list.getName());

            if(list == currentList) {
                selectButton.setBackground(Color.GRAY);
            }
            JButton deleteButton = new JButton("X");

            selectButton.addActionListener(e -> {
                currentList = list;
                renderList();
                renderOverview();
            });

            deleteButton.addActionListener(e -> {
                currentList = null;
                lists.remove(list);
                renderList();
                renderOverview();
            });

            listPanel.add(selectButton, BorderLayout.CENTER);
            listPanel.add(deleteButton, BorderLayout.EAST);
            overviewContainer.add(listPanel);
        }

        overviewContainer.revalidate();
        overviewContainer.repaint();
    }

    public void createList() {
        String name = (String)JOptionPane.showInputDialog(
            this, "Listenname:", "Neue Liste", JOptionPane.QUESTION_MESSAGE, null, null, "Liste");

        if(name == null || name.isBlank()) {
            return;
        }

        lists.add(new ToDoList(name));

        renderOverview();
    }

    public void addTask() {
        String title = (String)JOptionPane.showInputDialog(
            this, "Titel:", "Neue Aufgabe", JOptionPane.QUESTION_MESSAGE, null, null, "Aufgabe");
        
        if (title == null || title.isBlank()) {
            return;
        }

        currentList.addEntry(new ListEntry(title, false));

        renderList();
    }

    public void exportList() {
        if ((JOptionPane.showConfirmDialog(
            this, "Alter Stand wird gelöscht!", "Liste exportieren?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) == 0) {
            
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

                for (ListEntry entry : currentList.getEntries()) {
                    writer.println(entry.getTitle() + "," + entry.getCompleted());
                }

                writer.close();

            } catch (IOException e) {System.err.println(e);}
        }
    }

    public void importList() {
        if (currentList == null) {
            JOptionPane.showMessageDialog(
                this, "Keine Liste ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);

        } else {
            String s = "";
            String[] t;
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));

                while ((s = reader.readLine()) != null) {
                    t = s.split(",");
                    currentList.addEntry(new ListEntry(t[0], Boolean.parseBoolean(t[1])));
                }

                reader.close();

            } catch (IOException e) {System.err.println(e);}

            renderList();

        }
    }
}
package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;

public class Window extends JFrame {

    public Window() {
        setSize(600, 400);

        JPanel pc = new JPanel();
        JPanel pl = new JPanel();
        JPanel pr = new JPanel();

        BorderLayout b = new BorderLayout();

        pc.setLayout(b);
        pl.setLayout(b);
        pr.setLayout(b);

        ToDoList list = new ToDoList(1, "List 1");
        list.addEntry(new ListEntry(1, "Milch kaufen"));
        list.addEntry(new ListEntry(2, "Hausaufgaben machen"));


        //Listeneintrge zu JList-Komponente hinzufügen
        DefaultListModel<ListEntry> model = new DefaultListModel<>();
        for (ListEntry entry : list.getEntries()) {
            model.addElement(entry);
        }

        JList<ListEntry> jList1 = new JList<>(model);






        JPanel p = new JPanel();
        pl.add(p);
        add(pl, BorderLayout.LINE_START);

        JPanel p2 = new JPanel();
        pr.add(p2);
        add(pr, BorderLayout.LINE_END);


        pc.add(new JScrollPane(jList1), BorderLayout.CENTER);
        add(pc, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}

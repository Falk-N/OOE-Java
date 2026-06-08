package todoapp;

import javax.swing.*;
import java.awt.BorderLayout;

class Window extends JFrame {

    Window() {
        setSize(600, 400);
        BorderLayout b = new BorderLayout();

        JPanel pc = new JPanel();
        pc.setLayout(b);

        JPanel pl = new JPanel();
        pl.setLayout(b);

        JPanel pr = new JPanel();
        pr.setLayout(b);



        ToDoList list = new ToDoList(1, "name");
        list.addEntry(new ListEntry(1, "Milch kaufen"));
        list.addEntry(new ListEntry(2, "Hausaufgaben machen"));

        DefaultListModel<ListEntry> model = new DefaultListModel<>();
        for (ListEntry entry : list.getEntries()) {
            model.addElement(entry);
        }


        

        JList<ListEntry> jList1 = new JList<>(model);
        JList<ListEntry> jList2 = new JList<>(model);
        JList<ListEntry> jList3 = new JList<>(model);

        pc.add(new JScrollPane(jList1), BorderLayout.CENTER);
        add(pc, BorderLayout.CENTER);

        pl.add(new JScrollPane(jList2), BorderLayout.CENTER);
        add(pl, BorderLayout.LINE_START);

        pr.add(new JScrollPane(jList3), BorderLayout.CENTER);
        add(pr, BorderLayout.LINE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}

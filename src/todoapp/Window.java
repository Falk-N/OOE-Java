package todoapp;

import javax.swing.*;

class Window extends JFrame {

    Window() {
        System.setProperty("sun.java2d.uiScale", "2.0");
        setSize(600, 400);
        String week[]= { "Monday","Tuesday","Wednesday", "Thursday","Friday","Saturday","Sunday"};


        JList l = new JList(week);

        add(l);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
package todoapp;

import javax.swing.*;
import java.awt.event.*;

class Window extends JFrame {

    Window() {
        setSize(600, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

}
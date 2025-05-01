package scripts.gui;

import java.awt.Color;
import javax.swing.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        createMainComponents();
    }    

    public void createMainComponents() {
        this.setBackground(Color.white);

        JButton b = new JButton("test");
        b.setFocusPainted(false);
        b.setBackground(Color.white);
        this.add(b); 
    }
    
}

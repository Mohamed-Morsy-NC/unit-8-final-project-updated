package scripts.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPanel extends JPanel implements ActionListener {
    private JButton startBtn;
    private JButton helpBtn;
    private JButton exitBtn;
    private JButton debugBtn;
    //... more later

    private HelpPanel helpPanel = new HelpPanel();
    private static GamePanel gamePanel = new GamePanel();
    private static PausePanel pausePanel = new PausePanel();
    private JPanel menuPanel = new JPanel();

    private static CardLayout cardLayout = new CardLayout();
    private static JPanel cardPanel = new JPanel(cardLayout);
    

    public void createMainComponents() {
        this.setLayout(new BorderLayout());
        
        menuPanel.setBackground(Color.green);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        cardPanel.add(helpPanel, "Help");
        cardPanel.add(gamePanel, "Game");
        cardPanel.add(pausePanel, "Pause");

        startBtn = createButton("START");
        helpBtn = createButton("HELP");
        exitBtn = createButton("QUIT");
        debugBtn = createButton("DEBUG MODE");

        // Align Buttons
        alignButtons();

        this.add(menuPanel, BorderLayout.WEST);
        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(helpPanel, "Help");
        cardPanel.add(gamePanel, "Game");
        cardPanel.add(pausePanel, "Pause");
        this.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Menu");
    }

    private void alignButtons() {
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(startBtn);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(helpBtn);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(exitBtn);
         menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(debugBtn);
        menuPanel.add(Box.createVerticalGlue());   

    }

    private JButton createButton(String txt) {
        JButton b = new JButton(txt);
        b.setFocusPainted(false);
        b.setBackground(Color.white);
        b.addActionListener(this);
        b.setVisible(true);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setFont(MainFrame.UNIVERSAL_FONT);
        b.setMaximumSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_MAX);
        b.setPreferredSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_PREFERRED);
        return b;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceObject = e.getSource();

        if (sourceObject == exitBtn) {
            System.exit(0);
        }
        else if (sourceObject == startBtn) {
            cardLayout.show(cardPanel, "Game");
            System.out.println("Game Panel Active");
            gamePanel.requestFocusInWindow();
        }
        else if (sourceObject == debugBtn) {
            MainFrame.debugModeOn = !MainFrame.debugModeOn;

            if (MainFrame.debugModeOn) {
                menuPanel.setBackground(Color.red);
                menuPanel.repaint();
            }
            else {
                menuPanel.setBackground(Color.green);
                menuPanel.repaint();
            }
        }
        else if (sourceObject == helpBtn) {
            cardLayout.show(cardPanel, "Help");
            System.out.println("Help Panel Active");
        }
    }

    public static void returnToMain() {
        cardLayout.show(cardPanel, "Menu");
    }

    public static void returnToGame() {
        cardLayout.show(cardPanel, "Game");
        gamePanel.requestFocusInWindow();
    }

    public static void goToPause() {
        cardLayout.show(cardPanel, "Pause");
        pausePanel.requestFocusInWindow();
    }
    
}

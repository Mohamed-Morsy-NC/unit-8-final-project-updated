package scripts.gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
    private Dimension SCREEN_DIMENSION = new Dimension(1920, 1080);
    private boolean debugModeOn;
    private int currentPanelIndex = 0; // 0 is mainPanel

    protected static CopyOnWriteArrayList<JPanel> panelScreens = new CopyOnWriteArrayList<>();
    static MainPanel mainPanel = new MainPanel();
    // static PausePanel pausePanel = new PausePanel();
    // static ReplaysPanel replaysPanel = new ReplaysPanel();
    // static GamePanel gamePanel = new GamePanel();
    // static HelpPanel helpPanel = new HelpPanel();

    public MainFrame() {
        panelScreens.add(mainPanel);
        // panelScreens.add(pausePanel);
        // panelScreens.add(replaysPanel);
        // panelScreens.add(gamePanel);
        // panelScreens.add(helpPanel);
    }

    public static void init(MainFrame GUI) {
        SwingUtilities.invokeLater(() -> GUI.createFrame(GUI));
    }

    public void createFrame(Object semaphore) {
        this.setTitle("");
        this.setSize(SCREEN_DIMENSION);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        addMenuBar();

        // Set up MainPanel
        // this.setVisible(true);
        // setVisiblePanel(currentPanelIndex);
        // System.out.println("CURRENT PANEL INDEX: " + currentPanelIndex);

        mainPanel.createMainComponents();
        mainPanel.setVisible(true);
        this.add(mainPanel);
        
        this.setVisible(true);

        // Set up app icon
        try {
            this.setIconImage(ImageIO.read(new File("assets/game_assets/8_ball_game_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addMenuBar() {

    }

    // public static void setVisiblePanel(int panelIndex) {
    //     removeAllPanels();

    //     switch (panelIndex) {
    //         case 0 -> {
    //             mainPanel.setVisible(true);
    //             break;
    //         }
    //         case 1 -> {
    //             helpPanel.setVisible(true);
    //             break;
    //         }
    //         case 2 -> {
    //             replaysPanel.setVisible(true);
    //             break;
    //         }
    //         case 3 -> {
    //             gamePanel.setVisible(true);
    //             break;
    //         }
    //         case 4 -> {
    //             pausePanel.setVisible(true);
    //             break;
    //         }
    //         default -> {
    //             throw new IllegalArgumentException("No panel is currently visible. Invalid index.");
    //         }
    //     }
    // }

    private static void removeAllPanels() {
        for (JPanel p : panelScreens) {
            p.setVisible(false);
        }
    }

}

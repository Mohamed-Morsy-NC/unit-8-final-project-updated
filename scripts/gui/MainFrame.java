package scripts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
    private Dimension SCREEN_DIMENSION = new Dimension(1920, 1080);
    private boolean debugModeOn;
    private int currentPanelIndex = 0; // 0 is mainPanel

    private ArrayList<JPanel> panelScreens = new ArrayList<>();
    MainPanel mainPanel = new MainPanel();
    PausePanel pausePanel = new PausePanel();
    ReplaysPanel replaysPanel = new ReplaysPanel();
    GamePanel gamePanel = new GamePanel();
    HelpPanel helpPanel = new HelpPanel();

    public MainFrame() {
        panelScreens.add(mainPanel);
        panelScreens.add(pausePanel);
        panelScreens.add(replaysPanel);
        panelScreens.add(gamePanel);
        panelScreens.add(helpPanel);
    }

    public static void init() {
        System.out.println("Game Frame initialized.");

        MainFrame GUI = new MainFrame();
        SwingUtilities.invokeLater(() -> GUI.createFrame(GUI));
    }

    public void createFrame(Object semaphore) {
        this.setTitle("");
        this.setSize(SCREEN_DIMENSION);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up titlebar
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setLayout(new GridBagLayout());
        JButton exitBtn = new JButton("X");
        this.add(exitBtn, new GridBagConstraints());
        this.setLocationRelativeTo(null);

        // Helpful link: https://coderanch.com/t/344113/java/close-minimize-button-frame
        

        addMenuBar();

        // Set up MainPanel
        this.setVisible(true);
        setVisiblePanel(currentPanelIndex);

        // Set up app icon
        try {
            this.setIconImage(ImageIO.read(new File("assets/game_assets/8_ball_game_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addMenuBar() {

    }

    private void setVisiblePanel(int panelIndex) {
        removeAllPanels();

        switch (panelIndex) {
            case 0 -> {
                this.add(mainPanel);
                mainPanel.setVisible(true);
                break;
            }
            case 1 -> {
                this.add(helpPanel);
                helpPanel.setVisible(true);
                break;
            }
            case 2 -> {
                this.add(replaysPanel);
                replaysPanel.setVisible(true);
                break;
            }
            case 3 -> {
                this.add(gamePanel);
                gamePanel.setVisible(true);
                break;
            }
            case 4 -> {
                this.add(pausePanel);
                pausePanel.setVisible(true);
                break;
            }
            default -> {
                throw new IllegalArgumentException("No panel is currently visible. Invalid index.");
            }
        }
    }

    private void removeAllPanels() {
        for (JPanel p : panelScreens) {
            p.setVisible(false);
            this.remove(p);
        }
    }

}

package scripts.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final Dimension SCREEN_DIMENSION = new Dimension(1920, 1080);
    protected static final Dimension UNIVERSAL_BUTTON_DIMENSION_MAX = new Dimension(350, 75);
    protected static final Dimension UNIVERSAL_BUTTON_DIMENSION_PREFERRED = new Dimension(250, 75);
    protected static final Font UNIVERSAL_FONT = new Font("Arial", Font.BOLD, 18);
    public static boolean pauseState = false;
    protected static boolean debugModeOn = false;
    static MainPanel mainPanel = new MainPanel();

    public static void init(MainFrame GUI) {
        SwingUtilities.invokeLater(() -> GUI.createFrame(GUI));
    }

    public void createFrame(Object semaphore) {
        this.setTitle("");
        this.setSize(SCREEN_DIMENSION);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // addMenuBar();

        mainPanel.createMainComponents();
        mainPanel.setVisible(true);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);

        // Set up app icon
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/assets/game_assets/8_ball_game_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GamePanel.startBGMusic();
    }
}
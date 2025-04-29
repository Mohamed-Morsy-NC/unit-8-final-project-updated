package scripts.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private final ArrayList<Ball> gameBalls = new ArrayList<>();

    public GamePanel() {
        System.out.println("Game Panel initialized.");

        gameBalls.add(new Ball(this, "assets/game_assets/7 ball_vertical.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball b : gameBalls) {
            if (b.isActive()) b.drawSprite(g);
        }
    }
}

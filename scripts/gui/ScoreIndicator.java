package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;


public class ScoreIndicator extends JComponent {
    private static int playerScore = 0;
    private static int aiScore = 0;

    public static void updatePlayerScore() {
        playerScore++;
    }

    public static void updateAIScore() {
        aiScore++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.white);
        g.drawString("PLAYER SCORE: " + playerScore, 50, 250);
        g.drawString("AI SCORE: " + aiScore, 50, 150);
    }
    
}

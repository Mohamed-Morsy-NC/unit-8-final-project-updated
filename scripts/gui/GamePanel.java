package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    private final ArrayList<Ball> gameBalls = new ArrayList<>();

    public GamePanel() {
        System.out.println("Game Panel initialized.");
        initProperties();

        gameBalls.add(new Ball(this, "assets/game_assets/OneIdle.png", 1, 1));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P) {
            System.out.println("pause on/off");
            updatePauseState();
        }

        if (key == KeyEvent.VK_0) {
            Ball b = gameBalls.get(0); 
            b.setSprite(b.getSprite().setAnimState("assets/game_assets/OneU&D.png", 1, 4));
            b.setupAnimator();
            this.repaint();
        }

        
        if (MainFrame.pauseState) {
            MainPanel.goToPause();
            System.out.println("pause panel");
        }
    }

   public synchronized void updatePauseState() {
        MainFrame.pauseState = !MainFrame.pauseState;
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyReleased(KeyEvent e) {
    
   }

    private void initProperties() {
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball b : gameBalls) {
            if (b.isActive()) b.drawSprite(g);
        }
    }
}

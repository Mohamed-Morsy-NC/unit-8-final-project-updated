package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

    private final ArrayList<Ball> gameBalls = new ArrayList<>();
    private MouseMotionAdapter mouseMotionAdapter;
    private MouseAdapter mouseAdapter; // for clicks

    private BufferedImage bgImg;
    private GameUIElement cueStick;

    public GamePanel() {
        System.out.println("Game Panel initialized.");
        initProperties();

        gameBalls.add(new Ball(this, "assets/game_assets/OneIdle.png", 1, 1));
        cueStick = new GameUIElement(this, "assets/game_assets/cue_stick.png", 1, 1, 1);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P) {
            System.out.println("pause on/off");
            updatePauseState();
        }

        // if (key == KeyEvent.VK_0) {
        //     Ball b = gameBalls.get(0); 
        //     b.setSprite(b.getSprite().setAnimState("assets/game_assets/OneU&D.png", 1, 4));
        //     b.setupAnimator();
        //     this.repaint();
        // }
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

        mouseMotionAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                cueStick.setPosX(e.getX() - cueStick.getWidth()/2);
                cueStick.setPosY(e.getY() - cueStick.getHeight()/2);
            }

            // private void saySomething(String eventDesc, MouseEvent e) {
            //     System.out.println(e.getX() + ", " + e.getY());
            // }
        };

        this.addMouseMotionListener(mouseMotionAdapter);
        this.addMouseListener(mouseAdapter);
    } 

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            bgImg = ImageIO.read(new File("assets/game_assets/Background Board.png"));
        } catch (Exception e) {
        }
        
        g.drawImage(bgImg, 0, 0, this);

        cueStick.drawSprite(g);
        for (Ball b : gameBalls) {
            if (b.isActive()) {
                b.drawSprite(g);
            }
        }
    }
}

package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GamePanel extends JPanel implements KeyListener {

    private final ArrayList<Ball> gameBalls = new ArrayList<>();
    private MouseMotionAdapter mouseMotionAdapter;
    private MouseAdapter mouseAdapter; // for clicks

    private BufferedImage bgImg;
    private GameUIElement cueStick;
    protected static JSlider shotStrengthSlider;
    private static final int STRENGTH_MIN = 0;
    private static final int STRENGTH_MAX = 1000;
    private static final int STRENGTH_INIT = 10;
    private boolean isHoldingDown = false;
    private boolean lockSlider = false;

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
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        mouseMotionAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                cueStick.setPosX(e.getX() - cueStick.getWidth()/2);
                cueStick.setPosY(e.getY() - cueStick.getHeight()/2);
            }
        };

        this.addMouseMotionListener(mouseMotionAdapter);
        this.addMouseListener(mouseAdapter);
    
        shotStrengthSlider = new JSlider(JSlider.VERTICAL, STRENGTH_MIN, STRENGTH_MAX, STRENGTH_INIT);
        shotStrengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("Strength updated.");
            }
        });

        Hashtable strengthLabelTable = new Hashtable();
        strengthLabelTable.put(0, new JLabel("a"));
        strengthLabelTable.put(STRENGTH_INIT, new JLabel("b"));
        strengthLabelTable.put(STRENGTH_MAX, new JLabel("c"));
        shotStrengthSlider.setLabelTable(strengthLabelTable);
        shotStrengthSlider.setPaintLabels(true);
        shotStrengthSlider.setPaintTicks(false);
        shotStrengthSlider.setEnabled(false);
        shotStrengthSlider.setOpaque(true);

        this.add(shotStrengthSlider);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1)
                    isHoldingDown = true;
                
                if (e.getButton() == 3) {
                    lockSlider = true;
                    isHoldingDown = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isHoldingDown = false;
            }
        });

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

        if (isHoldingDown) {
            shotStrengthSlider.setValue(shotStrengthSlider.getValue() + 5);
        }
        else if (lockSlider && !isHoldingDown) {
            Timer t = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shotStrengthSlider.setValue(STRENGTH_MIN);
                    lockSlider = false;        
                }
            });

            t.setRepeats(false);
            t.start();
        }
        else {
            shotStrengthSlider.setValue(shotStrengthSlider.getValue() - 10);
        }

        for (Ball b : gameBalls) {
            if (b.isActive()) b.drawSprite(g);
        }
    }
}

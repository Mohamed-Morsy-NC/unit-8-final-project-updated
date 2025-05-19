package scripts.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import scripts.backend.AudioHandler;

// if (key == KeyEvent.VK_0) {
//     Ball b = gameBalls.get(0); 
//     b.setSprite(b.getSprite().setAnimState("assets/game_assets/OneU&D.png", 1, 4));
//     b.setupAnimator();
//     this.repaint();
// }
public class GamePanel extends JPanel implements KeyListener {

    protected static ArrayList<Ball> gameBalls = new ArrayList<>();
    private ArrayList<Point> pocketPositions = new ArrayList<>();
    private ArrayList<Ellipse2D.Double> pocketBounds = new ArrayList<>();
    protected static boolean justHit = false;
    private MouseMotionAdapter mouseMotionAdapter;
    private MouseAdapter mouseAdapter; // for clicks
    private boolean isPlayerTurn = true;

    private double xShift = 0;
    private double yShift = 0;
    protected static Rectangle tableDimensions = new Rectangle(340, 250, 1185, 550);

    private BufferedImage bgImg;
    private GameUIElement cueStick;
    private ScoreIndicator scoreIndicator;

    public static JSlider shotStrengthSlider;
    private static final int STRENGTH_MIN = 0;
    private static final int STRENGTH_MAX = 1000;
    private static final int STRENGTH_INIT = 350;
    private boolean isHoldingDown = false;
    private boolean lockSlider = false;
    private Timer resetTimer;

    private AudioHandler sfxHandler;
    private static AudioHandler bgMusicHandler;

    private BufferedImage lockedCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(lockedCursorImage, new Point(0, 0), "blank cursor");
    private Ball cueBall;

    private int cueX;
    private int cueY;

    public GamePanel() {
        System.out.println("Game Panel initialized.");
        initProperties();

        cueBall = new Ball(this, "assets/game_assets/cueball.png", 1, 1);
        cueBall.setPosX(450);
        cueBall.setPosY(450);

        Ball eightBall = new Ball(this, "assets/game_assets/EightIdle.png", 1, 1);
        eightBall.setPosX(500);
        eightBall.setPosY(350);

        Ball sevenBall = new Ball(this, "assets/game_assets/SevenIdle.png", 1, 1);
        sevenBall.setPosX(650);
        sevenBall.setPosY(450);

        Ball sixBall = new Ball(this, "assets/game_assets/SixIdle.png", 1, 1);
        sixBall.setPosX(700);
        sixBall.setPosY(250);

        gameBalls.add(cueBall);
        gameBalls.add(eightBall);
        gameBalls.add(sixBall);
        gameBalls.add(sevenBall);

        cueStick = new GameUIElement(this, "assets/game_assets/cue_stick.png", 1, 1, 1);
    }

    public static void startBGMusic() {
        try {
            bgMusicHandler = new AudioHandler("assets/audio/bgmusic.wav");
            bgMusicHandler.playAudioLooped();
        } catch (Exception e) {
            System.out.println("Background audio failed to load: " + e.getMessage());
        }
    }

    public static void endBGMusic() {
        try {
            bgMusicHandler.stopAudio();
        } catch (Exception e) {
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE) {
            System.out.println("pause on/off");
            updatePauseState();
        } else if (key == KeyEvent.VK_D) {
            System.out.println("Debug mode on/off");
            MainFrame.debugModeOn = !MainFrame.debugModeOn;
        } else if (key == KeyEvent.VK_Q) {
            System.out.println("quit game");
            System.exit(0);
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
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        scoreIndicator = new ScoreIndicator();

        // Pocket positions
        pocketPositions.add(new Point(350, 260));
        pocketPositions.add(new Point(930, 260));
        pocketPositions.add(new Point(1510, 260));
        pocketPositions.add(new Point(350, 780));
        pocketPositions.add(new Point(930, 780));
        pocketPositions.add(new Point(1510, 780));

        for (Point p : pocketPositions) {
            Ellipse2D.Double pocket = new Ellipse2D.Double(p.x - 50, p.y - 50, 100, 100);
            pocketBounds.add(pocket);
        }

        mouseMotionAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.println("X: " + e.getX() + ", Y: " + e.getY());
                cueX = e.getX();
                cueY = e.getY() - cueStick.getHeight() / 2;
                cueStick.setPosX(cueX);
                cueStick.setPosY(cueY);

                checkDistance(e.getX(), e.getY());
                repaint();
            }
        };

        this.addMouseMotionListener(mouseMotionAdapter);
        this.addMouseListener(mouseAdapter);

        shotStrengthSlider = new JSlider(JSlider.VERTICAL, STRENGTH_MIN, STRENGTH_MAX, STRENGTH_MIN);
        shotStrengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // System.out.println("Strength updated.");
            }
        });

        Hashtable strengthLabelTable = new Hashtable();
        strengthLabelTable.put(0, new JLabel("weak"));
        strengthLabelTable.put(STRENGTH_INIT, new JLabel("medium"));
        strengthLabelTable.put(STRENGTH_MAX - 250, new JLabel("powerful"));
        strengthLabelTable.put(STRENGTH_MAX, new JLabel("TOO strong"));
        shotStrengthSlider.setLabelTable(strengthLabelTable);
        shotStrengthSlider.setPaintLabels(true);
        shotStrengthSlider.setPaintTicks(false);
        shotStrengthSlider.setEnabled(false);
        shotStrengthSlider.setOpaque(true);

        this.add(shotStrengthSlider);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    isHoldingDown = true;
                }

                repaint();

                // if (e.getButton() == 3) {
                //     lockSlider = true;
                //     isHoldingDown = false;
                // } 
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isHoldingDown = false;
                int hitValue = shotStrengthSlider.getValue();
                double hitAngle = cueStick.getDrawAngle(); // this is the problem

                cueBall.moveBall((int) (hitValue * Math.cos(hitAngle)), (int) (hitValue * Math.sin(hitAngle)));
                justHit = true;
                isPlayerTurn = false;

                resetTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        justHit = false;
                        checkDistance(cueX, cueY);
                    }
                });
                resetTimer.setRepeats(false);
                resetTimer.start();
            }
        });

    }

    private void checkDistance(int x, int y) {
        double distance = Math.sqrt(Math.pow(x - cueBall.getPosX(), 2) + Math.pow(y - cueBall.getPosY(), 2));

        if (distance <= 250) {
            cueStick.setVisibility(true);
        } else {
            cueStick.setVisibility(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            bgImg = ImageIO.read(getClass().getResourceAsStream("/assets/game_assets/Background Board.png"));
        } catch (Exception e) {
        }
        g.drawImage(bgImg, 0, 0, this);

        String turnString = "Current Turn: ";
        if (isPlayerTurn) {
            turnString += "PLAYER";
        } else {
            turnString += "AI";
        }

        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.white);
        g.drawString(turnString, 50, 50);

        if (MainFrame.debugModeOn) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setStroke(new BasicStroke(3));
            g2D.drawRect(340, 250, 1185, 550);

            for (Point p : pocketPositions) {
                g.setColor(Color.gray);
                g.drawOval(p.x - 50, p.y - 50, 100, 100);
            }
        }

        if (isPlayerTurn) {
            if (isHoldingDown) {
                shotStrengthSlider.setValue(shotStrengthSlider.getValue() + 5);

                cueStick.setIsHolding(true);
                this.setCursor(blankCursor);
            }

            cueStick.paintComponent(g);
        } else {
            cueStick.setIsHolding(false);
            this.setCursor(Cursor.getDefaultCursor());
            shotStrengthSlider.setValue(shotStrengthSlider.getValue() - 10);
        }

        for (Ball b : gameBalls) {
            if (b.isActive()) {
                b.drawSprite(g);
                if (b.checkCollision()) {
                    try {
                        sfxHandler = new AudioHandler("assets/audio/collision.wav");
                        sfxHandler.playAudio();
                    } catch (Exception e) {
                    }
                }

                for (Ellipse2D.Double p : pocketBounds) {
                    if (p.contains(b.getPosX(), b.getPosY())) {
                        b.setActiveStatus(false);

                        // will need to check WHO pocketed later
                        scoreIndicator.updatePlayerScore();
                        try {
                            sfxHandler = new AudioHandler("assets/audio/pocketed.wav");
                            sfxHandler.playAudio();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }

        Iterator<Ball> iterator = gameBalls.iterator();
        while (iterator.hasNext()) {
            Ball b = iterator.next();
            if (!b.isActive()) {
                iterator.remove(); // remove balls that have been pocketed
            }
        }

        scoreIndicator.paintComponent(g);

        if (!isPlayerTurn) {
            Timer turnTimer = new Timer(2500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isPlayerTurn = true;
                }
            });

            turnTimer.setRepeats(false);
            turnTimer.start();
        }
    }
}

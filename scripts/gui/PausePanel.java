package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;

public class PausePanel extends GameOverPanel implements KeyListener {
    private JButton returnBtn;
    private JButton quitBtn;
    
    public PausePanel() {
        initProperties();
        createComponents();
    }

    private void createComponents() {
        returnBtn = new JButton("GO BACK TO MAIN");
        returnBtn.setAlignmentX(CENTER_ALIGNMENT);
        returnBtn.setFocusPainted(false);
        returnBtn.setBackground(Color.white);
        returnBtn.setMaximumSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_MAX);
        returnBtn.setPreferredSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_PREFERRED);
        returnBtn.setFont(MainFrame.UNIVERSAL_FONT);
        returnBtn.addActionListener((e) -> {
            MainPanel.returnToMain();
        });
        this.add(returnBtn);

        quitBtn = new JButton("QUIT");
        quitBtn.setAlignmentX(CENTER_ALIGNMENT);
        quitBtn.setFocusPainted(false);
        quitBtn.setBackground(Color.white);
        quitBtn.setMaximumSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_MAX);
        quitBtn.setPreferredSize(MainFrame.UNIVERSAL_BUTTON_DIMENSION_PREFERRED);
        quitBtn.setFont(MainFrame.UNIVERSAL_FONT);
        quitBtn.addActionListener((e) -> {
            System.exit(0);
        });
        this.add(quitBtn);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(e.getKeyCode());
        if (key == KeyEvent.VK_P) {
            System.out.println("pause on/off");
            updatePauseState();

            if (!MainFrame.pauseState) {
                MainPanel.returnToGame();
            }
        }
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   public synchronized void updatePauseState() {
        MainFrame.pauseState = !MainFrame.pauseState;   
   }

   @Override
   public void keyReleased(KeyEvent e) {
    
   }

    private void initProperties() {
        this.setBackground(Color.yellow);
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
}

package scripts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.sources.ScheduledExecutorTimingSource;

public class GameUIElement {
    private Sprite cueStickSprite;
    private int posX;
    private int posY;
    private JPanel activePanel;
    private int animSpeed = 200;
    private Dimension hitBox;
    private Animator spriteAnimator;

    public GameUIElement(JPanel parent, String imagePath, int rows, int cols) {
        this(parent, imagePath, rows, cols, 10);
    }

    public GameUIElement(JPanel parent, String imagePath, int rows, int cols, int scaleFactor) {
        activePanel = parent;      
        TimingSource timingSource = new ScheduledExecutorTimingSource(15, TimeUnit.MILLISECONDS);
        Animator.setDefaultTimingSource(timingSource);
        timingSource.init();

        this.cueStickSprite = new Sprite(imagePath, cols, rows, scaleFactor);

        // Starts at origin?
        posX = 0; posY = 0;

        hitBox = new Dimension(cueStickSprite.getWidth(), cueStickSprite.getHeight());

        setupAnimator();
    }

    public GameUIElement(JPanel parent, String imagePath) {
        this(parent, imagePath, 1, 1);
    }

    public void setupAnimator() {
        spriteAnimator = new Animator.Builder()
            .setDuration(animSpeed * cueStickSprite.getCount(), TimeUnit.MILLISECONDS)
            .setRepeatCount(Animator.INFINITE)
            .setRepeatBehavior(Animator.RepeatBehavior.LOOP)
            .addTarget(PropertySetter.getTarget(this, "imageSpriteIndex", 0, cueStickSprite.getCount()))
            .build();

        spriteAnimator.start();
    }

    public void setPosX(int px) {
        posX = px;
    }

    public void setPosY(int py) {
        posY = py;
    } 

    public Sprite getSprite() {
        return cueStickSprite;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return hitBox.width;
    }

    public int getHeight() {
        return hitBox.height;
    }

    public void setImageSpriteIndex(int index) {
        cueStickSprite.setSpriteIndex(index);
        activePanel.repaint();
    }

    public void setFPS(int fps) {
        animSpeed = fps;
    }

    public void setSprite(Sprite s) {
        cueStickSprite = s;
    }

    public void drawSprite(Graphics g) {
        cueStickSprite.drawSprite(g, cueStickSprite.getSpriteIndex(), posX, posY);

        if (MainFrame.debugModeOn) {
            g.setColor(Color.red);
            
            g.drawRect(posX, posY, hitBox.width, hitBox.height);
        }
    }
    
}
package scripts.gui;

import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.sources.ScheduledExecutorTimingSource;

public class Ball {
    private final Sprite ballSprite;
    private boolean isActive = true;
    private int posX;
    private int posY;
    private JPanel activePanel;
    private int animSpeed = 200;

    public Ball(JPanel parent, String imagePath) {
        activePanel = parent;      
        TimingSource timingSource = new ScheduledExecutorTimingSource(15, TimeUnit.MILLISECONDS);
        Animator.setDefaultTimingSource(timingSource);
        timingSource.init();

        this.ballSprite = new Sprite(imagePath, 1, 4);

        // Starts at origin?
        posX = 0; posY = 0;

        Animator animatorSprite = new Animator.Builder()
            .setDuration(animSpeed * ballSprite.getCount(), TimeUnit.MILLISECONDS)
            .setRepeatCount(Animator.INFINITE)
            .setRepeatBehavior(Animator.RepeatBehavior.LOOP)
            .addTarget(PropertySetter.getTarget(this, "imageSpriteIndex", 0, ballSprite.getCount()))
            .build();

        animatorSprite.start();
    }

    public Sprite getSprite() {
        return ballSprite;
    }

    public void setActiveStatus(boolean status) {
        isActive = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setImageSpriteIndex(int index) {
        ballSprite.setSpriteIndex(index);
        activePanel.repaint();
    }

    public void setFPS(int fps) {
        animSpeed = fps;
    }

    public void drawSprite(Graphics g) {
        ballSprite.drawSprite(g, ballSprite.getSpriteIndex(), posX, posY);
    }
    
}

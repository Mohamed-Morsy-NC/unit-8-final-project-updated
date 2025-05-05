package scripts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.sources.ScheduledExecutorTimingSource;

public class Ball {
    private Sprite ballSprite;
    private boolean isActive = true;
    private int posX;
    private int posY;
    private JPanel activePanel;
    private int animSpeed = 200;
    private Dimension hitBox;
    private Animator spriteAnimator;

    public Ball(JPanel parent, String imagePath, int rows, int cols) {
        activePanel = parent;      
        TimingSource timingSource = new ScheduledExecutorTimingSource(15, TimeUnit.MILLISECONDS);
        Animator.setDefaultTimingSource(timingSource);
        timingSource.init();

        this.ballSprite = new Sprite(imagePath, cols, rows);

        // Starts at origin?
        posX = 0; posY = 0;

        hitBox = new Dimension(ballSprite.getWidth(), ballSprite.getHeight());

        setupAnimator();
    }

    public void setupAnimator() {
        spriteAnimator = new Animator.Builder()
            .setDuration(animSpeed * ballSprite.getCount(), TimeUnit.MILLISECONDS)
            .setRepeatCount(Animator.INFINITE)
            .setRepeatBehavior(Animator.RepeatBehavior.LOOP)
            .addTarget(PropertySetter.getTarget(this, "imageSpriteIndex", 0, ballSprite.getCount()))
            .build();

        spriteAnimator.start();
    }

    public Ball(JPanel parent, String imagePath) {
        this(parent, imagePath, 4, 1);
    }

    public boolean containsPoint(Point coordinate) {
        return (posX <= coordinate.getX() && posX + hitBox.width >= coordinate.getX()
        && posY <= coordinate.getY() && posY + hitBox.height >= coordinate.getY());
    }

    public boolean isCollidingWith(Ball b2) {
        // For each corner, check if within this ball's bounds
        // Top-left
        int x2 = b2.getPosX();
        int y2 = b2.getPosY();
        boolean tL = ((x2 <= (posX + hitBox.width) && x2 >= (posX) && y2 <= (posY + hitBox.height) && y2 >= (posY)));
        x2 += b2.getWidth();
        boolean tR = ((x2 <= (posX + hitBox.width) && x2 >= (posX) && y2 <= (posY + hitBox.height) && y2 >= (posY)));
        y2 += b2.getHeight();
        boolean bR = ((x2 <= (posX + hitBox.width) && x2 >= (posX) && y2 <= (posY + hitBox.height) && y2 >= (posY)));
        x2 -= b2.getWidth();
        boolean bL = ((x2 <= (posX + hitBox.width) && x2 >= (posX) && y2 <= (posY + hitBox.height) && y2 >= (posY)));

        return (bL || bR || tR || tL);
    }

    public Sprite getSprite() {
        return ballSprite;
    }

    public void setActiveStatus(boolean status) {
        isActive = status;
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

    public void setSprite(Sprite s) {
        ballSprite = s;
    }

    public void setPosX(int px) {
        posX = px;
    }

    public void setPosY(int py) {
        posY = py;
    } 

    public void drawSprite(Graphics g) {
        ballSprite.drawSprite(g, ballSprite.getSpriteIndex(), posX, posY);

        if (MainFrame.debugModeOn) {
            g.setColor(Color.red);
            
            g.drawRect(posX, posY, hitBox.width, hitBox.height);
        }
    }
    
}

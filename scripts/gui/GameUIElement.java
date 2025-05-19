package scripts.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.concurrent.TimeUnit;
import java.awt.BasicStroke;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import javax.swing.JPanel;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.sources.ScheduledExecutorTimingSource;

public class GameUIElement extends JComponent {

    private Sprite cueStickSprite;
    private int posX;
    private int posY;
    private JPanel activePanel;
    private int animSpeed = 200;
    private Dimension hitBox;
    private Animator spriteAnimator;
    private double drawAngle = 0;
    private boolean isHolding = false;
    private boolean isVisible = true;
    private int pullBackDist = 5;

    public GameUIElement(JPanel parent, String imagePath, int rows, int cols) {
        this(parent, imagePath, rows, cols, 10);
    }

    public void setVisibility(boolean state) {
        isVisible = state;
    }

    public GameUIElement(JPanel parent, String imagePath, int rows, int cols, int scaleFactor) {
        activePanel = parent;
        TimingSource timingSource = new ScheduledExecutorTimingSource(15, TimeUnit.MILLISECONDS);
        Animator.setDefaultTimingSource(timingSource);
        timingSource.init();

        this.cueStickSprite = new Sprite(imagePath, cols, rows, scaleFactor);

        // Starts at origin?
        posX = 0;
        posY = 0;

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

    public double getDrawAngle() {
        return drawAngle;
    }

    public void setDrawAngle(double angle) {
        drawAngle = angle;
    }

    public void rotateStick(Graphics g, boolean continueRotating) {
        Graphics2D g2d = (Graphics2D) g;
        Ball cueBall = GamePanel.gameBalls.get(0);
        double ballX = cueBall.getPosX() + cueBall.getWidth() / 2;
        double ballY = cueBall.getPosY() + cueBall.getHeight() / 2;
        double diffX = ballX - posX;
        double diffY = ballY - posY;
        double angle = Math.atan2(diffY, diffX);
        int lineX = (int) (ballX + GamePanel.shotStrengthSlider.getValue()/2 * Math.cos(angle));
        int lineY = (int) (ballY + GamePanel.shotStrengthSlider.getValue()/2 * Math.sin(angle));

        double drawDist = cueStickSprite.getWidth();

        if (isHolding) {
            drawDist += pullBackDist;
        }

        double drawX = ballX - Math.cos(angle) * drawDist;
        double drawY = ballY - Math.sin(angle) * drawDist;

        AffineTransform old = g2d.getTransform();

        g2d.translate(drawX, drawY);
        g2d.rotate(angle);
        drawAngle = angle;
        drawSprite(g);


        g2d.setTransform(old);

        float[] dashPattern = {10, 5};
        g2d.setStroke(new BasicStroke(
            2f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10f,
            dashPattern,
            0f
        ));
        g2d.drawLine((int)ballX, (int)ballY, lineX, lineY);
    }

    private void drawSprite(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        cueStickSprite.drawSprite2D(g2D, cueStickSprite.getSpriteIndex(), 0, -cueStickSprite.getHeight()/2);
    }

    public void setIsHolding(boolean h) {
        isHolding = h;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isVisible && !GamePanel.justHit)
            rotateStick(g, !isHolding);
    }

}

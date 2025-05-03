package scripts.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Sprite {
    private Image[] images;
    private int count;
    private int spriteIndex = 0;
    private int spriteWidth;
    private int spriteHeight;
    private int scaleFactor = 10;
    
    public Sprite(String path, int cols, int rows) {
        setupAnimation(path, cols, rows);
    }

    private void setupAnimation(String path, int cols, int rows) {
        spriteIndex=0;
        BufferedImage spriteSheet = null;

        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        spriteWidth = spriteSheet.getWidth() / cols;
        spriteHeight = spriteSheet.getHeight() / rows;
        images = new Image[rows * cols];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                images[i + cols * j] = spriteSheet.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight).getScaledInstance(spriteWidth/scaleFactor, spriteHeight/scaleFactor, Image.SCALE_SMOOTH);
            }   
        }

        spriteWidth/=scaleFactor;
        spriteHeight/=scaleFactor;

        count = cols * rows;
    }

    public int getWidth() {
        return spriteWidth;
    }

    public int getHeight() {
        return spriteHeight;
    }

    public Sprite setAnimState(String imagePath, int cols, int rows) {
        setupAnimation(imagePath, cols, rows);
        return this;
    }

    public void drawSprite(Graphics g, int index, int x, int y) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Invalid sprite index: " + index);
        }
        g.drawImage(images[index], x, y, null);
    }

    public int getCount() {
        return count;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }
}

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
    
    public Sprite(String path, int cols, int rows) {
        BufferedImage spriteSheet = null;

        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int spriteWidth = spriteSheet.getWidth() / cols;
        int spriteHeight = spriteSheet.getHeight() / rows;
        images = new Image[rows * cols];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                images[i + cols * j] = spriteSheet.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        count = cols * rows;
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

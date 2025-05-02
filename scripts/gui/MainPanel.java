package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainPanel extends JPanel {
    private BufferedImage bgImg;
    private BufferedImage cueStickImg;

    public void createMainComponents() {
        this.setLayout(null);
        this.setBackground(Color.black);

        // JLabel bgLabel = new JLabel(new ImageIcon(bgImg));
        // bgLabel.setVisible(true);
        // this.add(bgLabel);

        JButton b = new JButton("Start Game");

        
        // b.addActionListener(() -> new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         MainFrame.setVisiblePanel(3);
        //     }
        // });

        b.setBounds(0, 0, 100, 100);
        b.setBackground(Color.white);
        b.setVisible(true);
        
        this.add(b); 

    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            bgImg = ImageIO.read(new File("assets/game_assets/Background Board.png"));   
            cueStickImg = ImageIO.read(new File("assets/game_assets/Cue Stick.png"));  
        } catch (Exception e) {
        }
        
        g.drawImage(bgImg, 0, 0, this);
        g.drawImage(cueStickImg, 50, 50, this);
    }
    
}

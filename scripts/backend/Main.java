package scripts.backend;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import scripts.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            System.out.println(lafInfo.getClassName());
        }

        MetalLookAndFeel.setCurrentTheme(new MyDefaultMetalTheme());
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //https://github.com/vincenzopalazzo/material-ui-swing/wiki/Introduction
        

        // try {
        //     UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        // } catch (Exception e) {
        // }

        MainFrame mainFrame = new MainFrame();
        mainFrame.init(mainFrame);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }
}

class MyDefaultMetalTheme extends DefaultMetalTheme {
    public ColorUIResource getWindowTitleInactiveBackground() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getWindowTitleBackground() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getPrimaryControlHighlight() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getPrimaryControlDarkShadow() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getPrimaryControl() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getControlHighlight() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getControlDarkShadow() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  
    public ColorUIResource getControl() {
      return new ColorUIResource(java.awt.Color.orange);
    }
  }
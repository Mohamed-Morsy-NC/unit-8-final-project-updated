package scripts.backend;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;

import scripts.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {

            });   
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.init(mainFrame);
    }
}

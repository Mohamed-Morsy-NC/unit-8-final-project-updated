package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpPanel extends JPanel {

    private static final String INSTRUCTIONS_STRING = "Test 1 2 3";
    private JTextArea instructionsTextArea;
    private JScrollPane scrollPane;
    private JButton returnBtn;

    public HelpPanel() {
        System.out.println("Help Panel initialized.");
        initProperties();
        createComponents();
    }

    private void createComponents() {
        this.add(Box.createVerticalGlue());
        instructionsTextArea = new JTextArea(50, 35);
        instructionsTextArea.setBackground(Color.white);
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setFont(MainFrame.UNIVERSAL_FONT);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setText(INSTRUCTIONS_STRING);

        scrollPane = new JScrollPane(instructionsTextArea);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane);

        returnBtn = new JButton("GO BACK");
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
    }

    private void initProperties() {
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

}

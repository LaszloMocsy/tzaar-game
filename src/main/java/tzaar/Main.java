package tzaar;

import tzaar.component.Board;
import tzaar.gui.GamePanel;
import tzaar.gui.SidePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the frame
        JFrame frame = new JFrame("Tzaar game");

        // Initialize the SidePanel
        SidePanel sidePanel = new SidePanel();
        frame.add(sidePanel, BorderLayout.EAST);

        // Initialize the GamePanel
        GamePanel gamePanel = new GamePanel(new Board());
        frame.add(gamePanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setSize(740, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
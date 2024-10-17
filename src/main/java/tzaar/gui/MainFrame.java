package tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        // Initialize the game frame
        super("Tzaar game");

        // Initialize and add StatusPanel
        SidePanel sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

        // Initialize and add GamePanel to the GameFrame
        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Make the game frame visible
        setSize(740, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void startGame() {
        setVisible(true);
    }
}

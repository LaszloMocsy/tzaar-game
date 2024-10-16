package tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        // Initialize the game frame
        super("Tzaar game");

        // Initialize and add StatusPanel
        StatusPanel statusPanel = new StatusPanel("Initial text");
        add(statusPanel, BorderLayout.NORTH);

        // Initialize and add GamePanel to the GameFrame
        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Make the game frame visible
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void startGame() {
        setVisible(true);
    }
}

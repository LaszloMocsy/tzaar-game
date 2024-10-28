package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends JPanel {
    public final JButton newGameButton;
    public final JButton loadGameButton;
    
    public MainMenuUI() {
        // Initialize the buttons
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");

        // Set the layout to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        // Add the newGameButton
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(newGameButton, gbc);

        // Add the loadGameButton
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loadGameButton, gbc);
    }
}

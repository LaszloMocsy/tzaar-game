package dev.laszlomocsy.tzaar.gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final MenuPanel menuPanel = new MenuPanel();
    private static final GamePanel gamePanel = new GamePanel();

    public GameWindow() {
        setTitle("Tzaar Game");
        setSize(600, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        add(menuPanel, menuPanel.getName());
        add(gamePanel, gamePanel.getName());
        
        gamePanel.setMenuPanel(menuPanel);

        // Set up event listeners
        menuPanel.addStartGameListener(e -> {
            gamePanel.startNewGame();
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
            System.out.println("Game started!");
        });
        gamePanel.addReturnToMenuListener(e -> {
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
            System.out.println("Returned to menu!");
        });
        menuPanel.addLoadGameListener(e -> System.out.println("Load game clicked!"));
        gamePanel.addSaveGameListener(e -> System.out.println("Save game clicked!"));

        // Show the menu panel
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }
}

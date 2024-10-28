package dev.laszlomocsy.tzaar;

import dev.laszlomocsy.tzaar.game.Board;
import dev.laszlomocsy.tzaar.gui.MainMenuUI;

import javax.swing.*;
import java.awt.*;

public class TzaarGame {
    private static final MainMenuUI mainMenuUI = new MainMenuUI();
    private static Board board = null;

    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Tzaar Game");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setSize(740, 740);
        gameWindow.setResizable(true);

        // Set up the main menu UI
        mainMenuUI.newGameButton.addActionListener(e -> {
            System.out.println("New Game button clicked");
            board = Board.initDefault();
            System.out.println("Board: " + board);
        });
        mainMenuUI.loadGameButton.addActionListener(e -> JOptionPane.showMessageDialog(gameWindow, "Not implemented yet", "Error!", JOptionPane.ERROR_MESSAGE));
        gameWindow.add(mainMenuUI, BorderLayout.CENTER);

        gameWindow.setVisible(true);
    }
}
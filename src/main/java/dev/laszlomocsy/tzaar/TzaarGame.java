package dev.laszlomocsy.tzaar;

import dev.laszlomocsy.tzaar.game.Board;
import dev.laszlomocsy.tzaar.gui.BoardUI;
import dev.laszlomocsy.tzaar.gui.MainMenuUI;
import dev.laszlomocsy.tzaar.gui.GameStatusUI;

import javax.swing.*;
import java.awt.*;

public class TzaarGame {
    private static final MainMenuUI mainMenuUI = new MainMenuUI();
    private static final GameStatusUI gameStatusUI = new GameStatusUI();
    private static final BoardUI boardUI = new BoardUI(gameStatusUI);
    
    private static Board board = null;

    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Tzaar Game");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setSize(740, 740);
        gameWindow.setResizable(true);

        // Set up the main menu UI
        mainMenuUI.newGameButton.addActionListener(e -> {
            // Set up a new board
            board = Board.initDefault();

            // Remove the main menu UI from the game window
            mainMenuUI.setVisible(false);
            gameStatusUI.setVisible(true);

            // Add the board UI to the game window
            boardUI.setupBoard(board);
            gameWindow.add(boardUI, BorderLayout.CENTER);
        });
        mainMenuUI.loadGameButton.addActionListener(e -> JOptionPane.showMessageDialog(gameWindow, "Not implemented yet", "Error!", JOptionPane.ERROR_MESSAGE));
        gameWindow.add(mainMenuUI, BorderLayout.CENTER);
        
        // Set up the status panel
        gameWindow.add(gameStatusUI, BorderLayout.NORTH);
        gameStatusUI.setVisible(false);

        gameWindow.setVisible(true);
    }
}
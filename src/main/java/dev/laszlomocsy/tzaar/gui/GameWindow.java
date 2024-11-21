package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.utils.GameState;
import dev.laszlomocsy.tzaar.utils.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

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
            gamePanel.startNewGame(null);
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
        });
        gamePanel.addReturnToMenuListener(e -> {
            int returnValue = JOptionPane.showOptionDialog(this, "Do you want to save the game?", "Save game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");
            if (returnValue == 0) {
                // Save the game
                Board board = gamePanel.getBoard();
                String player1Name = menuPanel.getWhitePlayerName();
                String player2Name = menuPanel.getBlackPlayerName();
                GameState gameState = GameStateManager.generateGameState(board, player1Name, player2Name);
                GameStateManager.saveGame(gameState, this);
            }

            // Return to the menu
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
        });
        menuPanel.addLoadGameListener(e -> {
            Optional<GameState> gameState = GameStateManager.loadGame(this);
            if (gameState.isPresent()) {
                menuPanel.setWhitePlayerName(gameState.get().player1);
                menuPanel.setBlackPlayerName(gameState.get().player2);

                Board starterBoard = GameStateManager.generateBoard(gameState.get());
                gamePanel.startNewGame(starterBoard);
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
            }
        });
        gamePanel.addSaveGameListener(e -> {
            Board board = gamePanel.getBoard();
            String player1Name = menuPanel.getWhitePlayerName();
            String player2Name = menuPanel.getBlackPlayerName();
            GameState gameState = GameStateManager.generateGameState(board, player1Name, player2Name);
            GameStateManager.saveGame(gameState, this);
        });

        // Show the menu panel
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }
}

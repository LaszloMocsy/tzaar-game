package dev.laszlomocsy.tzaar.gui;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.utils.GameState;
import dev.laszlomocsy.tzaar.utils.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * The main window of the game.
 */
public class GameWindow extends JFrame {
    private static final MenuPanel menuPanel = new MenuPanel();
    private static final GamePanel gamePanel = new GamePanel();

    /**
     * Creates a new game window.
     */
    public GameWindow() {
        setTitle("Tzaar Game");
        setSize(850, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        add(menuPanel, menuPanel.getName());
        add(gamePanel, gamePanel.getName());

        gamePanel.setMenuPanel(menuPanel);

        // Set up event listeners
        menuPanel.addStartGameListener(e -> startNewGame());
        gamePanel.addReturnToMenuListener(e -> returnToMenu());
        menuPanel.addLoadGameListener(e -> loadGame());
        gamePanel.addSaveGameListener(e -> saveGame());

        // Show the menu panel
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }

    /**
     * Starts a new game.
     */
    private void startNewGame() {
        gamePanel.startNewGame(null);
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
    }

    /**
     * Returns to the menu panel.
     */
    private void returnToMenu() {
        int returnValue = JOptionPane.showOptionDialog(this, "Are you sure you want to go back to the main menu?\nWould you like to save the game?", "Return to main menu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Return and save", "Return without saving", "Stay"}, "Return and save");

        // Return and save the game
        if (returnValue == 0) {
            Board board = gamePanel.getBoard();
            String player1Name = menuPanel.getWhitePlayerName();
            String player2Name = menuPanel.getBlackPlayerName();
            GameState gameState = GameStateManager.generateGameState(board, player1Name, player2Name);
            GameStateManager.saveGame(gameState, this);
        }

        // Return to the menu
        if (returnValue == 0 || returnValue == 1)
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }

    /**
     * Saves the current game.
     */
    private void saveGame() {
        Board board = gamePanel.getBoard();
        String player1Name = menuPanel.getWhitePlayerName();
        String player2Name = menuPanel.getBlackPlayerName();
        GameState gameState = GameStateManager.generateGameState(board, player1Name, player2Name);
        GameStateManager.saveGame(gameState, this);
    }

    /**
     * Loads a saved game.
     */
    private void loadGame() {
        Optional<GameState> gameState = GameStateManager.loadGame(this);
        if (gameState.isPresent()) {
            menuPanel.setWhitePlayerName(gameState.get().player1);
            menuPanel.setBlackPlayerName(gameState.get().player2);

            Board starterBoard = GameStateManager.generateBoard(gameState.get());
            gamePanel.startNewGame(starterBoard);
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
        }
    }
}

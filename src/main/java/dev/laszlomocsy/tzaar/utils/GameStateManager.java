package dev.laszlomocsy.tzaar.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.figure.Figure;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is responsible for saving and loading the game state.
 */
public class GameStateManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Prevent initialization of this utility class.
     */
    private GameStateManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Saves the given game state to a file.
     */
    public static void saveGame(GameState gameState, Component dialogParent) {
        // Create a JFileChooser instance
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save game");

        // Set JSON file filter
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
        fileChooser.setFileFilter(jsonFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Show Save Dialog
        int userSelection = fileChooser.showSaveDialog(dialogParent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure the file has a .json extension
            if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".json");
            }

            boolean saveResult = GameStateManager.saveToFile(gameState, fileToSave);
            if (saveResult) {
                JOptionPane.showMessageDialog(dialogParent, "Game state saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogParent, "Failed to save game state.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads a game state from a file.
     */
    public static Optional<GameState> loadGame(Component dialogParent) {
        // Create a JFileChooser instance
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Game File");

        // Set JSON file filter
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
        fileChooser.setFileFilter(jsonFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Show Open Dialog
        int userSelection = fileChooser.showOpenDialog(dialogParent);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            Optional<GameState> gameState = GameStateManager.loadFromFile(fileToLoad);

            if (gameState.isPresent()) {
                JOptionPane.showMessageDialog(dialogParent, "Game state loaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogParent, "Failed to load game state.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            return gameState;
        }

        return Optional.empty();
    }

    /**
     * Saves the given game state to a file.
     *
     * @param gameState The game state to save.
     * @param file      The file to save the game state to.
     */
    public static boolean saveToFile(GameState gameState, File file) {
        try {
            // Serialize the object to JSON and write to a file
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, gameState);
        } catch (IOException e2) {
            return false;
        }

        return true;
    }

    /**
     * Loads a game state from a file.
     *
     * @param file The file to load the game state from.
     * @return The loaded game state.
     */
    public static Optional<GameState> loadFromFile(File file) {
        try {
            // Deserialize JSON file
            GameState gameState = objectMapper.readValue(file, GameState.class);
            return Optional.of(gameState);
        } catch (IOException e2) {
            return Optional.empty();
        }
    }

    /**
     * Converts a board to a game state.
     *
     * @param board       The board to convert.
     * @param player1Name The name of player 1.
     * @param player2Name The name of player 2.
     * @return The game state generated from the board.
     */
    public static GameState generateGameState(Board board, String player1Name, String player2Name) {
        GameState gameState = new GameState();
        gameState.player1 = player1Name;
        gameState.player2 = player2Name;
        gameState.figures = board.getFigures();
        return gameState;
    }

    /**
     * Converts a game state to a board.
     *
     * @param gameState The game state to convert.
     * @return The board generated from the game state.
     */
    public static Board generateBoard(GameState gameState) {
        Board board = Board.initEmpty();
        for (Figure figure : gameState.figures) {
            board.placeFigure(figure);
        }
        board.startGame();
        return board;
    }
}

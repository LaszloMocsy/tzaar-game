package dev.laszlomocsy.tzaar.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.utils.BoardData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static final MenuPanel menuPanel = new MenuPanel();
    private static final GamePanel gamePanel = new GamePanel();
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            System.out.println("Game started!");
        });
        gamePanel.addReturnToMenuListener(e -> {
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
            System.out.println("Returned to menu!");
        });
        menuPanel.addLoadGameListener(e -> {
            System.out.println("Load game clicked!");

            // Create a JFileChooser instance
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Game File");

            // Set JSON file filter
            FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
            fileChooser.setFileFilter(jsonFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Show Open Dialog
            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();

                try {
                    // Deserialize JSON file
                    BoardData starterBoardData = objectMapper.readValue(fileToLoad, BoardData.class);
                    menuPanel.setWhitePlayerName(starterBoardData.players.whitePlayer);
                    menuPanel.setBlackPlayerName(starterBoardData.players.blackPlayer);

                    Board starterBoard = BoardData.toBoard(starterBoardData);
                    gamePanel.startNewGame(starterBoard);
                    ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
                    JOptionPane.showMessageDialog(this, "Game state loaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        gamePanel.addSaveGameListener(e -> {
            System.out.println("Save game clicked!");

            // Create a JFileChooser instance
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save game");

            // Set JSON file filter
            FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
            fileChooser.setFileFilter(jsonFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Show Save Dialog
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // Ensure the file has a .json extension
                if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".json");
                }

                BoardData data = BoardData.fromBoard(gamePanel.getBoard(), menuPanel.getWhitePlayerName(), menuPanel.getBlackPlayerName());
                try {
                    // Serialize the object to JSON and write to a file
                    ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
                    writer.writeValue(fileToSave, data);
                    JOptionPane.showMessageDialog(this, "Game state saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });

        // Show the menu panel
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }
}

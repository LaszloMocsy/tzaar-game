package dev.laszlomocsy.tzaar.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.utils.BoardData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
            System.out.println("Game started!");
        });
        gamePanel.addReturnToMenuListener(e -> {
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
            System.out.println("Returned to menu!");
        });
        menuPanel.addLoadGameListener(e -> {
            System.out.println("Load game clicked!");

            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            
            try {
                // Deserialize JSON file
                BoardData starterBoardData = objectMapper.readValue(new File("tzaar-savegame.json"), BoardData.class);
                menuPanel.setWhitePlayerName(starterBoardData.players.whitePlayer);
                menuPanel.setBlackPlayerName(starterBoardData.players.blackPlayer);

                Board starterBoard = BoardData.toBoard(starterBoardData);
                gamePanel.startNewGame(starterBoard);
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), gamePanel.getName());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        gamePanel.addSaveGameListener(e -> {
            System.out.println("Save game clicked!");

            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            BoardData data = BoardData.fromBoard(gamePanel.getBoard(), menuPanel.getWhitePlayerName(), menuPanel.getBlackPlayerName());
            try {
                // Serialize the object to JSON and write to a file
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("tzaar-savegame.json"), data);
                System.out.println("JSON file created successfully.");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });

        // Show the menu panel
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), menuPanel.getName());
    }
}

package dev.laszlomocsy.tzaar.tests.utils;

import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.utils.GameState;
import dev.laszlomocsy.tzaar.utils.GameStateManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameStateTests {
    @Test
    void testGameStateSave() {
        // Set up the game state
        String player1 = "John";
        String player2 = "Jane";
        Board board = Board.InitDefault();

        // Save the game state
        GameState state = GameStateManager.generateGameState(board, player1, player2);

        // Check the saved game state
        assertEquals(player1, state.player1);
        assertEquals(player2, state.player2);
        assertEquals(board.getFigures().size(), state.figures.size());
    }
    
    @Test
    void testGameStateLoad() {
        // Load game state
        File fileToLoad = new File("src/test/resources/default-state.json");
        Optional<GameState> state = GameStateManager.loadFromFile(fileToLoad);
        
        assertTrue(state.isPresent(), "Failed to load game state.");
        assertEquals("John", state.get().player1);
        assertEquals("Jane", state.get().player2);
        assertEquals(60, state.get().figures.size());
    }
}

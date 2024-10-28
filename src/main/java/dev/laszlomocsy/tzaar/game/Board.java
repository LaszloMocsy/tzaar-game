package dev.laszlomocsy.tzaar.game;

import java.util.ArrayList;
import java.util.List;

/// A standalone game board. It contains the spaces and manages the game logic.
public class Board {
    /// All figures on the board.
    private final List<Figure> figures;

    //-- CONSTRUCTORS --//

    /// Initializes a new Board.
    public Board() {
        this.figures = new ArrayList<>();
    }
    
    //-- GETTERS --//
    
    /// Returns all figures on the board.
    public List<Figure> getFigures() {
        return figures;
    }
}

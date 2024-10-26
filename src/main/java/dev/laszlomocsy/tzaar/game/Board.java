package dev.laszlomocsy.tzaar.game;

import java.util.ArrayList;
import java.util.List;

/// A standalone game board. It contains the spaces and manages the game logic.
public class Board {
    /// The spaces on the board.
    private List<Space> spaces;

    //-- CONSTRUCTORS --//

    /// Initializes a new Board with the default layout.
    public Board() {
        this.spaces = new ArrayList<>();

        int[] numOfSpaces = {5, 6, 7, 8, 8, 8, 7, 6, 5};
        for (int coordX = 1; coordX <= 9; coordX++) {
            int coordYBase = coordX <= 5 ? 1 : coordX - 5;
            if (coordX > 5) {
                coordYBase += 1;
            }

            for (int i = 0; i < numOfSpaces[coordX - 1]; i++) {
                int coordY = coordYBase + i;
                if (coordX == 5 && i > 3) {
                    coordY += 1;
                }
                
                spaces.add(new Space(new SpaceCoordinate(coordX, coordY)));
            }
        }
    }
    
    //-- GETTERS --//
    
    /// Returns the spaces on the board.
    public List<Space> getSpaces() {
        return spaces;
    }
}

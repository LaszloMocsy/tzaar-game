package dev.laszlomocsy.tzaar.game;

/// Location coordinate of a `Space`. Represents the location of a space on the board.
public record SpaceCoordinate(int x, int y) {
    //-- METHODS (public) --//

    /// Returns the `String` representation of the coordinate.
    public String asString() {
        final Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        return letters[x - 1] + Integer.toString(y);
    }

    //-- OVERRIDES --//

    /// Returns a string representation of the coordinate.
    @Override
    public String toString() {
        return asString();
    }
}

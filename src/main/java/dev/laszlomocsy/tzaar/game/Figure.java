package dev.laszlomocsy.tzaar.game;

/// Represents a figure in the game.
public record Figure(FigureColor color, FigureType type, int height) {
    //-- METHODS --//

    /// Increases the height of the figure by 1.
    public Figure increaseHeight(FigureType newType) {
        return new Figure(color, newType, height + 1);
    }

    /// Checks if the figure is a stack.
    public boolean isStack() {
        return height > 1;
    }

    //-- OVERRIDES --//

    /// Returns a string representation of the figure.
    @Override
    public String toString() {
        return "Figure{color=%s, type=%s, height=%d}".formatted(color, type, height);
    }
}

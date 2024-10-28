package dev.laszlomocsy.tzaar.game;

/// Represents a figure in the game.
public class Figure {
    private Coordinate location;
    private final FigureColor color;
    private FigureType type;
    private int height;

    //-- CONSTRUCTORS --//

    /// Initializes a new Figure.
    public Figure(Coordinate location, FigureColor color, FigureType type, int height) {
        this.location = location;
        this.color = color;
        this.type = type;
        this.height = height;
    }

    //-- GETTERS --//

    /// Returns the location of the figure.
    public Coordinate getLocation() {
        return location;
    }

    /// Returns the color of the figure.
    public FigureColor getColor() {
        return color;
    }

    /// Returns the type of the figure.
    public FigureType getType() {
        return type;
    }

    /// Returns the height of the figure.
    public int getHeight() {
        return height;
    }

    //-- METHODS --//

    /// Moves the figure to a new location.
    public void moveTo(Coordinate newCoordinate) {
        this.location = newCoordinate;
    }

    /// Increases the height of the figure by 1.
    public void stack(Figure other) {
        this.type = other.getType();
        this.height += other.getHeight();
    }

    /// Checks if the figure is a stack.
    public boolean isStack() {
        return height > 1;
    }

    //-- OVERRIDES --//

    /// Returns a string representation of the figure.
    @Override
    public String toString() {
        return "Figure{location=%s, color=%s, type=%s, height=%d}".formatted(location, color, type, height);
    }
}

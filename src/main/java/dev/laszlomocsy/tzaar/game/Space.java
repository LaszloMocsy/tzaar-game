package dev.laszlomocsy.tzaar.game;

/// A space on the game board. Represents a single place where a figure can be placed.
public class Space {
    private final SpaceCoordinate location;
    private Figure figure;

    //-- CONSTRUCTORS --//

    /// Initializes a new Space.
    public Space(SpaceCoordinate location) {
        this.location = location;
        this.figure = null;
    }
    
    /// Initializes a new Space with a figure.
    public Space(SpaceCoordinate location, Figure figure) {
        this.location = location;
        this.figure = figure;
    }

    //-- GETTERS --//

    /// Returns the location of the space.
    public SpaceCoordinate getLocation() {
        return location;
    }

    /// Returns the figure on the space.
    public Figure getFigure() {
        return figure;
    }

    //-- METHODS --//

    /// Returns if the space has not figure.
    public boolean isEmpty() {
        return figure == null;
    }

    //-- OVERRIDES --//

    /// Returns a string representation of the space.
    @Override
    public String toString() {
        return "Space{loc=%s}".formatted(location);
    }
}

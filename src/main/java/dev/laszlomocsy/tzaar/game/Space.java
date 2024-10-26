package dev.laszlomocsy.tzaar.game;

/// A space on the game board. Represents a single place where a figure can be placed.
public class Space {
    /// The location of the space on the board.
    private final SpaceCoordinate location;

    //-- CONSTRUCTORS --//

    /// Initializes a new Space.
    public Space(SpaceCoordinate location) {
        this.location = location;
    }

    //-- GETTERS --//

    /// Returns the location of the space.
    public SpaceCoordinate getLocation() {
        return location;
    }

    //-- OVERRIDES --//

    /// Returns a string representation of the space.
    @Override
    public String toString() {
        return "Space{loc=%s}".formatted(location);
    }
}

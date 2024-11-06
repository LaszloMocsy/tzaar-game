package dev.laszlomocsy.tzaar.logic.figure;

/**
 * The location of a <code>Figure</code> on the <code>Board</code>.
 * <p>
 * The x is representing the letters of the board, while the y is representing the numbers of the board.
 * The available letters are from 'A' to 'I', while the available numbers are from 1 to 9.
 * There are only 60 valid locations on the board.
 *
 * @param x the x coordinate of the figure. Must be between one and 9!
 * @param y the y coordinate of the figure. Must be between one and 9!
 */
public record FigureLocation(int x, int y) {
    //-- Constructor --//

    public FigureLocation {
        if (x < 1 || x > 9) {
            throw new IllegalArgumentException("X must be between 1 and 9.");
        } else if (y < 1 || y > 9) {
            throw new IllegalArgumentException("Y must be between 1 and 9.");
        } else if (!isValid(x, y)) {
            throw new IllegalArgumentException("Location must be valid.");
        }
    }

    //-- Factory Methods --//

    /**
     * Creates a new <code>FigureLocation</code> from the given string.
     *
     * @param location the location of the figure. Must not be null and valid!
     * @return the new <code>FigureLocation</code>.
     */
    public static FigureLocation fromString(String location) {
        if (location == null) {
            throw new IllegalArgumentException("Location must not be null.");
        } else if (location.length() != 2) {
            throw new IllegalArgumentException("Location must be 2 characters long.");
        }

        int x = location.charAt(0) - 'A' + 1;
        int y = location.charAt(1) - '0';

        return new FigureLocation(x, y);
    }

    //-- Static Methods --//

    /**
     * Checks if the given location coordinates are valid.
     *
     * @param x the x coordinate of the figure. Must be between one and 9!
     * @param y the y coordinate of the figure. Must be between one and 9!
     * @return true if the location is valid, false otherwise.
     */
    public static boolean isValid(int x, int y) {
        // All valid locations are:
        //  A1, A2, A3, A4, A5,
        //  B1, B2, B3, B4, B5, B6,
        //  C1, C2, C3, C4, C5, C6, C7,
        //  D1, D2, D3, D4, D5, D6, D7, D8,
        //  E1, E2, E3, E4, E6, E7, E8, E9,  (E5 missing, because it is the middle space)
        //  F2, F3, F4, F5, F6, F7, F8, F9,
        //  G3, G4, G5, G6, G7, G8, G9,
        //  H4, H5, H6, H7, H8, H9,
        //  I5, I6, I7, I8, I9

        // calculate the start and end index of the y coordinate, based on the x coordinate
        final int yStart = x <= 5 ? 1 : x - 4;
        final int yEnd = x >= 5 ? 9 : x + 4;

        // check if the y coordinate is in the valid range
        // there is no middle space on the board, so the x=5, y=5 is invalid
        return y >= yStart && y <= yEnd && (x != 5 || y != 5);
    }

    //-- Overrides --//

    @Override
    public String toString() {
        return String.format("%c%d", 'A' + (x - 1), y);
    }
}

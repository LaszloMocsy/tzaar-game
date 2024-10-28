package dev.laszlomocsy.tzaar.game;

import java.util.ArrayList;
import java.util.List;

/// Location coordinate of a `Space`. Represents the location of a space on the board.
public record SpaceCoordinate(int x, int y) {
    //-- METHODS (private) --//

    /// Checks whether two coordinates are on the same X axis.
    private static boolean hasSameXAxis(SpaceCoordinate a, SpaceCoordinate b) {
        if (a.x == 5 && b.x == 5) {
            // They are on the middle X, so each Y must be less than 5 or greater than 5
            boolean aYLessThan5 = a.y < 5;
            boolean bYLessThan5 = b.y < 5;
            return aYLessThan5 == bYLessThan5;
        } else {
            // One of the three axes must be the same
            return a.x == b.x;
        }
    }

    /// Checks whether two coordinates are on the same Y axis.
    private static boolean hasSameYAxis(SpaceCoordinate a, SpaceCoordinate b) {
        if (a.y == 5 && b.y == 5) {
            // They are on the middle Y, so each X must be less than 5 or greater than 5
            boolean aXLessThan5 = a.x < 5;
            boolean bXLessThan5 = b.x < 5;
            return aXLessThan5 == bXLessThan5;
        } else {
            // One of the three axes must be the same
            return a.y == b.y;
        }
    }

    /// Checks whether two coordinates are on the same Z axis.
    private static boolean hasSameZAxis(SpaceCoordinate a, SpaceCoordinate b) {
        // Z = X - Y  (Z: 4, 3, 2, 1, 0, -1, -2, -3, -4)
        if (a.x - a.y == 0 && b.x - b.y == 0) {
            // They are on the middle Z, so each X must be less than 5 or greater than 5
            boolean aXLessThan5 = a.x < 5;
            boolean bXLessThan5 = b.x < 5;
            return aXLessThan5 == bXLessThan5;
        } else {
            // One of the three axes must be the same
            return a.x - a.y == b.x - b.y;
        }
    }

    //-- METHODS (public) --//

    /// Create a new `SpaceCoordinate` from a `String`.
    public static SpaceCoordinate fromString(String str) {
        final Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        int x = 0;
        for (int i = 0; i < letters.length; i++) {
            if (str.charAt(0) == letters[i]) {
                x = i + 1;
                break;
            }
        }
        int y = Integer.parseInt(str.substring(1));
        return new SpaceCoordinate(x, y);
    }

    /// Checks whether two coordinates are on the same axis.
    public static boolean isOnSameAxis(SpaceCoordinate a, SpaceCoordinate b) {
        return hasSameXAxis(a, b) || hasSameYAxis(a, b) || hasSameZAxis(a, b);
    }

    /// Returns all coordinates between two coordinates' same axis.
    public static List<SpaceCoordinate> coordinatesBetween(SpaceCoordinate a, SpaceCoordinate b) {
        // If the coordinates are not on the same axis, return an empty array
        if (!isOnSameAxis(a, b)) {
            return new ArrayList<>();
        }

        List<SpaceCoordinate> list = new ArrayList<>();
        if (hasSameXAxis(a, b)) {
            // X axis
            int direction = a.y < b.y ? 1 : -1;
            int diff = Math.abs(b.y - a.y) - 1;
            for (int i = 1; i <= diff; i++) {
                list.add(new SpaceCoordinate(a.x, a.y + direction * i));
            }
        } else {
            // Y and Z axis
            int direction = a.x < b.x ? 1 : -1;
            int diff = Math.abs(b.x - a.x) - 1;
            for (int i = 1; i <= diff; i++) {
                int x = a.x + direction * i;
                int y = hasSameYAxis(a, b) ? a.y : a.y + direction * i;
                list.add(new SpaceCoordinate(x, y));
            }
        }

        return list;
    }

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

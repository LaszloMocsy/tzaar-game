package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.Coordinate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class CoordinateTest {
    @Test
    void testCoordinateFromString() {
        String str = "H7";
        Coordinate coordinate = Coordinate.fromString(str);

        Assertions.assertEquals(8, coordinate.x(), "H7's X coordinate should be 8");
        Assertions.assertEquals(7, coordinate.y(), "H7's Y coordinate should be 7");
    }

    @Test
    void testCoordinateIsOnSameAxis() {
        Coordinate a1 = Coordinate.fromString("A1");
        Coordinate b2 = Coordinate.fromString("B2");
        Coordinate b5 = Coordinate.fromString("B5");
        Coordinate c2 = Coordinate.fromString("C2");
        Coordinate d5 = Coordinate.fromString("D5");
        Coordinate e1 = Coordinate.fromString("E1");
        Coordinate e6 = Coordinate.fromString("E6");
        Coordinate e8 = Coordinate.fromString("E8");
        Coordinate f6 = Coordinate.fromString("F6");
        Coordinate h5 = Coordinate.fromString("H5");

        // Same axis (not middle)
        Assertions.assertTrue(Coordinate.isOnSameAxis(b2, b5), "B2 and B5 are on the same x-axis");
        Assertions.assertTrue(Coordinate.isOnSameAxis(b2, c2), "B2 and C2 are on the same y-axis");
        Assertions.assertTrue(Coordinate.isOnSameAxis(b5, e8), "B5 and E8 are on the same z-axis");

        // Same axis (middle)
        Assertions.assertTrue(Coordinate.isOnSameAxis(e6, e8), "E6 and E8 are on the same x-axis");
        Assertions.assertTrue(Coordinate.isOnSameAxis(b5, d5), "B5 and D5 are on the same y-axis");
        Assertions.assertTrue(Coordinate.isOnSameAxis(a1, b2), "A1 and B2 are on the same z-axis");

        // Different axis (middle)
        Assertions.assertFalse(Coordinate.isOnSameAxis(e1, e6), "E1 and E6 are not on the same x-axis");
        Assertions.assertFalse(Coordinate.isOnSameAxis(b5, h5), "B5 and H5 are not on the same y-axis");
        Assertions.assertFalse(Coordinate.isOnSameAxis(a1, f6), "A1 and F6 are not on the same z-axis");
    }

    static Stream<Arguments> provideCoordinatesBetween() {
        return Stream.of(
                Arguments.of("A1", "A5", List.of("A2", "A3", "A4")),
                Arguments.of("F7", "C7", List.of("E7", "D7")),
                Arguments.of("D4", "A1", List.of("C3", "B2")),
                Arguments.of("E2", "E6", List.of())
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideCoordinatesBetween")
    void testCoordinatesBetween(String start, String end, List<String> expectedStrings) {
        Coordinate a = Coordinate.fromString(start);
        Coordinate b = Coordinate.fromString(end);

        List<Coordinate> between = Coordinate.coordinatesBetween(a, b);
        List<Coordinate> expected = expectedStrings.stream().map(Coordinate::fromString).toList();

        Assertions.assertEquals(expected.size(), between.size(), "There should be %d coordinates between %s and %s".formatted(expected.size(), start, end));
        for (Coordinate coordinate : expected) {
            Assertions.assertTrue(between.contains(coordinate), "The coordinates between %s and %s should contain %s".formatted(start, end, coordinate));
        }
    }
}

package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.SpaceCoordinate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class SpaceCoordinateTest {
    @Test
    void testSP_FromString() {
        String str = "H7";
        SpaceCoordinate coordinate = SpaceCoordinate.fromString(str);

        Assertions.assertEquals(8, coordinate.x(), "H7's X coordinate should be 8");
        Assertions.assertEquals(7, coordinate.y(), "H7's Y coordinate should be 7");
    }

    @Test
    void testSP_IsOnSameAxis() {
        SpaceCoordinate a1 = SpaceCoordinate.fromString("A1");
        SpaceCoordinate b2 = SpaceCoordinate.fromString("B2");
        SpaceCoordinate b5 = SpaceCoordinate.fromString("B5");
        SpaceCoordinate c2 = SpaceCoordinate.fromString("C2");
        SpaceCoordinate d5 = SpaceCoordinate.fromString("D5");
        SpaceCoordinate e1 = SpaceCoordinate.fromString("E1");
        SpaceCoordinate e6 = SpaceCoordinate.fromString("E6");
        SpaceCoordinate e8 = SpaceCoordinate.fromString("E8");
        SpaceCoordinate f6 = SpaceCoordinate.fromString("F6");
        SpaceCoordinate h5 = SpaceCoordinate.fromString("H5");

        // Same axis (not middle)
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(b2, b5), "B2 and B5 are on the same x-axis");
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(b2, c2), "B2 and C2 are on the same y-axis");
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(b5, e8), "B5 and E8 are on the same z-axis");

        // Same axis (middle)
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(e6, e8), "E6 and E8 are on the same x-axis");
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(b5, d5), "B5 and D5 are on the same y-axis");
        Assertions.assertTrue(SpaceCoordinate.isOnSameAxis(a1, b2), "A1 and B2 are on the same z-axis");

        // Different axis (middle)
        Assertions.assertFalse(SpaceCoordinate.isOnSameAxis(e1, e6), "E1 and E6 are not on the same x-axis");
        Assertions.assertFalse(SpaceCoordinate.isOnSameAxis(b5, h5), "B5 and H5 are not on the same y-axis");
        Assertions.assertFalse(SpaceCoordinate.isOnSameAxis(a1, f6), "A1 and F6 are not on the same z-axis");
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
    void testSP_CoordinatesBetween(String start, String end, List<String> expectedStrings) {
        SpaceCoordinate a = SpaceCoordinate.fromString(start);
        SpaceCoordinate b = SpaceCoordinate.fromString(end);

        List<SpaceCoordinate> between = SpaceCoordinate.coordinatesBetween(a, b);
        List<SpaceCoordinate> expected = expectedStrings.stream().map(SpaceCoordinate::fromString).toList();

        Assertions.assertEquals(expected.size(), between.size(), "There should be %d coordinates between %s and %s".formatted(expected.size(), start, end));
        for (SpaceCoordinate coordinate : expected) {
            Assertions.assertTrue(between.contains(coordinate), "The coordinates between %s and %s should contain %s".formatted(start, end, coordinate));
        }
    }
}

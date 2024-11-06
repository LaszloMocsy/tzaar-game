package dev.laszlomocsy.tzaar.tests.logic.figure;

import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FigureLocationTests {
    static Stream<Arguments> provideValidLocations() {
        return Stream.of(
                Arguments.of(1, 1, "A1"),
                Arguments.of(9, 9, "I9"),
                Arguments.of(5, 6, "E6")
        );
    }
    
    static Stream<Arguments> provideInvalidLocations() {
        return Stream.of(
                Arguments.of(0, 1, "X must be between 1 and 9."),
                Arguments.of(10, 2, "X must be between 1 and 9."),
                Arguments.of(1, 0, "Y must be between 1 and 9."),
                Arguments.of(2, 10, "Y must be between 1 and 9."),
                Arguments.of(5, 5, "Location must be valid.")
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideValidLocations")
    void testConstructorValid(int x, int y, String location) {
        FigureLocation figureLocation = new FigureLocation(x, y);
        assertEquals(x, figureLocation.x());
        assertEquals(y, figureLocation.y());
        assertEquals(location, figureLocation.toString());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLocations")
    void testConstructorInvalid(int x, int y, String message) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new FigureLocation(x, y));
        assertEquals(message, exception.getMessage());
    }
    
    @ParameterizedTest
    @MethodSource("provideValidLocations")
    void testFromStringValid(int x, int y, String expected) {
        FigureLocation location = FigureLocation.fromString(expected);
        assertEquals(x, location.x());
        assertEquals(y, location.y());
    }
    
    @Test
    void testFromStringNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FigureLocation.fromString(null));
        assertEquals("Location must not be null.", exception.getMessage());
    }
    
    @Test
    void testFromStringInvalidLength() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FigureLocation.fromString("A"));
        assertEquals("Location must be 2 characters long.", exception.getMessage());
    }
}
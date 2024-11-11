package dev.laszlomocsy.tzaar.tests.logic.figure;

import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigureLocationTests {
    @Test
    void testValidFigureLocationCreation() {
        FigureLocation location = new FigureLocation(1, 1);
        assertEquals(1, location.x());
        assertEquals(1, location.y());
    }

    @Test
    void testInvalidXCoordinate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new FigureLocation(0, 1);
        });
        assertEquals("X must be between 1 and 9.", exception.getMessage());
    }

    @Test
    void testInvalidYCoordinate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new FigureLocation(1, 10);
        });
        assertEquals("Y must be between 1 and 9.", exception.getMessage());
    }

    @Test
    void testInvalidLocation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new FigureLocation(5, 5);
        });
        assertEquals("Location must be valid.", exception.getMessage());
    }

    @Test
    void testFromStringValidLocation() {
        FigureLocation location = FigureLocation.fromString("A1");
        assertEquals(1, location.x());
        assertEquals(1, location.y());
    }

    @Test
    void testFromStringInvalidLocation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FigureLocation.fromString("E5");
        });
        assertEquals("Location must be valid.", exception.getMessage());
    }

    @Test
    void testHasSameAxis() {
        FigureLocation locA = new FigureLocation(1, 1);
        FigureLocation locB = new FigureLocation(1, 5);
        assertTrue(FigureLocation.hasSameAxis(locA, locB));
    }

    @Test
    void testHasDifferentAxis() {
        FigureLocation locA = new FigureLocation(1, 1);
        FigureLocation locB = new FigureLocation(2, 3);
        assertFalse(FigureLocation.hasSameAxis(locA, locB));
    }

    @Test
    void testToString() {
        FigureLocation location = new FigureLocation(1, 1);
        assertEquals("A1", location.toString());
    }
}
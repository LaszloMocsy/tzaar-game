package dev.laszlomocsy.tzaar.tests.logic.figure;

import dev.laszlomocsy.tzaar.logic.figure.Figure;
import dev.laszlomocsy.tzaar.logic.figure.FigureColor;
import dev.laszlomocsy.tzaar.logic.figure.FigureLocation;
import dev.laszlomocsy.tzaar.logic.figure.FigureType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureTests {
    @Test
    void testConstructorWithHeightOne() {
        FigureLocation location = new FigureLocation(1, 1);
        Figure figure = new Figure(location, FigureColor.BLACK, FigureType.TZAAR);

        assertEquals(location, figure.getLocation());
        assertEquals(FigureColor.BLACK, figure.getColor());
        assertEquals(FigureType.TZAAR, figure.getType());
        assertEquals(1, figure.getHeight());
    }

    @Test
    void testConstructorWithSpecifiedHeight() {
        FigureLocation location = new FigureLocation(2, 2);
        Figure figure = new Figure(location, FigureColor.WHITE, FigureType.TZARRA, 3);

        assertEquals(location, figure.getLocation());
        assertEquals(FigureColor.WHITE, figure.getColor());
        assertEquals(FigureType.TZARRA, figure.getType());
        assertEquals(3, figure.getHeight());
    }
}

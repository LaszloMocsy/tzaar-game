package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.Figure;
import dev.laszlomocsy.tzaar.game.FigureColor;
import dev.laszlomocsy.tzaar.game.FigureType;
import dev.laszlomocsy.tzaar.game.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FigureTest {
    @Test
    void testFigureMove() {
        Figure figure = new Figure(Coordinate.fromString("A1"), FigureColor.WHITE, FigureType.TZAAR, 1);
        Coordinate newCoordinate = Coordinate.fromString("E1");
        figure.moveTo(newCoordinate);
        Assertions.assertEquals(newCoordinate, figure.getLocation(), "The figure should be at the new location.");
        Assertions.assertFalse(figure.isStack(), "The figure should not be a stack.");
        Assertions.assertEquals("Figure{location=E1, color=WHITE, type=TZAAR, height=1}", figure.toString(), "The string representation of the figure does not match");
    }

    @Test
    void testFigureStack() {
        Figure figure1 = new Figure(Coordinate.fromString("D4"), FigureColor.BLACK, FigureType.TOTT, 3);
        Figure figure2 = new Figure(Coordinate.fromString("G4"), FigureColor.BLACK, FigureType.TZARRA, 2);
        figure1.stack(figure2);
        Assertions.assertEquals(5, figure1.getHeight(), "The height of the figure should be 5.");
        Assertions.assertEquals(FigureType.TZARRA, figure1.getType(), "The type of the figure should be TZARRA.");
        Assertions.assertTrue(figure1.isStack(), "The figure should be a stack.");
    }
}

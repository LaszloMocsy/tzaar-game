package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.Board;
import dev.laszlomocsy.tzaar.game.FigureColor;
import dev.laszlomocsy.tzaar.game.FigureType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BoardTest {
    @Test
    void testBoardEmptyInit() {
        Board board = Board.initEmpty();
        Assertions.assertEquals(0, board.getFigures().size(), "Empty board should have no figures.");
    }

    @Test
    void testBoardDefaultInit() {
        record FigureExpectation(String location, FigureColor color, FigureType type, int height) {
        }

        final List<FigureExpectation> expectations = Arrays.asList(
                new FigureExpectation("A1", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("A2", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("A3", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("A4", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("A5", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("B1", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("B2", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("B3", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("B4", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("B5", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("B6", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("C1", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("C2", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("C3", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("C4", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("C5", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("C6", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("C7", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("D1", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("D2", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("D3", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("D4", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("D5", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("D6", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("D7", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("D8", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("E1", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("E2", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("E3", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("E4", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("E6", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("E7", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("E8", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("E9", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("F2", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("F3", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("F4", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("F5", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("F6", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("F7", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("F8", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("F9", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("G3", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("G4", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("G5", FigureColor.BLACK, FigureType.TZAAR, 1),
                new FigureExpectation("G6", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("G7", FigureColor.WHITE, FigureType.TZAAR, 1),
                new FigureExpectation("G8", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("G9", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("H4", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("H5", FigureColor.BLACK, FigureType.TZARRA, 1),
                new FigureExpectation("H6", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("H7", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("H8", FigureColor.WHITE, FigureType.TZARRA, 1),
                new FigureExpectation("H9", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("I5", FigureColor.BLACK, FigureType.TOTT, 1),
                new FigureExpectation("I6", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("I7", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("I8", FigureColor.WHITE, FigureType.TOTT, 1),
                new FigureExpectation("I9", FigureColor.WHITE, FigureType.TOTT, 1)
        );

        Board board = Board.initDefault();
        Assertions.assertEquals(60, board.getFigures().size(), "Default board should have 60 figures.");
        for (FigureExpectation expectation : expectations) {
            Assertions.assertTrue(board.getFigures().stream().anyMatch(
                    figure -> figure.getLocation().asString().equals(expectation.location) &&
                            figure.getColor() == expectation.color &&
                            figure.getType() == expectation.type &&
                            figure.getHeight() == expectation.height
            ), "Figure not found: " + expectation);
        }
    }
}

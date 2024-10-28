package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.Figure;
import dev.laszlomocsy.tzaar.game.FigureColor;
import dev.laszlomocsy.tzaar.game.FigureType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class FigureTest {
    static Stream<Arguments> produceFigureCases() {
        return Stream.of(
                Arguments.of(FigureColor.WHITE, FigureType.TZAAR, 1, false, FigureType.TZAAR),
                Arguments.of(FigureColor.BLACK, FigureType.TZARRA, 2, true, FigureType.TOTT),
                Arguments.of(FigureColor.WHITE, FigureType.TOTT, 3, true, FigureType.TZARRA)
        );
    }

    @ParameterizedTest
    @MethodSource("produceFigureCases")
    void testFigureRecord(FigureColor color, FigureType type, int height, boolean isStack, FigureType newType) {
        Figure figure = new Figure(color, type, height);
        Assertions.assertSame(color, figure.color(), "Figure's color should be " + color);
        Assertions.assertSame(type, figure.type(), "Figure's type should be " + type);
        Assertions.assertEquals(height, figure.height(), "Figure's height should be " + height);
        Assertions.assertEquals(isStack, figure.isStack(), isStack ? "Figure should be a stack" : "Figure should not be a stack");

        figure = figure.increaseHeight(newType);
        Assertions.assertSame(color, figure.color(), "NewFigure's color should be " + color);
        Assertions.assertSame(newType, figure.type(), "NewFigure's type should be " + newType);
        Assertions.assertEquals(height + 1, figure.height(), "NewFigure's height should be " + (height + 1));
        Assertions.assertTrue(figure.isStack(), "NewFigure should be a stack");
    }
}

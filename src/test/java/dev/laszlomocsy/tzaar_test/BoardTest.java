package dev.laszlomocsy.tzaar_test;

import dev.laszlomocsy.tzaar.game.*;
import org.junit.jupiter.api.*;

import java.util.List;

class BoardTest {
    @Test
    void testDefaultBoardInitialization() {
        Board board = new Board();
        List<Space> spaces = board.getSpaces();

        Assertions.assertEquals(60, spaces.size()); // 2 * (6 + 9 + 15) = 60
        Assertions.assertEquals("A1", spaces.getFirst().getLocation().toString());
        Assertions.assertEquals("I9", spaces.getLast().getLocation().toString());
    }
}

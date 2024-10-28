package dev.laszlomocsy.tzaar.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// A standalone game board. It contains the spaces and manages the game logic.
public class Board {
    private final List<Figure> figures;
    private BoardState state;

    //-- CONSTRUCTORS --//

    /// Creates an empty board.
    private Board() {
        this.state = BoardState.SETUP;
        this.figures = new ArrayList<>();
    }

    /// Initializes an empty board.
    public static Board initEmpty() {
        return new Board();
    }

    /// Initializes a board with all figures in their default starting positions.
    public static Board initDefault() {
        Board board = new Board();

        int[] spacesInX = {5, 6, 7, 8, 8, 8, 7, 6, 5};
        for (int x = 1; x <= 9; x++) {
            int startY = Math.max(1, x - 4);
            for (int yCount = 0; yCount < spacesInX[x - 1]; yCount++) {
                int y = startY + yCount;
                if (x == 5 && yCount >= 4) y += 1;

                final List<String> whiteFigures = Arrays.asList("A5", "B1", "B5", "B6", "C1", "C2", "C5", "C6", "C7",
                        "D1", "D2", "D3", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "F6", "G6", "G7", "H6", "H7",
                        "H8", "I6", "I7", "I8", "I9");
                final List<String> tzaarFigures = Arrays.asList("C3", "C4", "C5", "D3", "D6", "E3", "E7", "F4", "F7",
                        "G5", "G6", "G7");
                final List<String> tzarraFigures = Arrays.asList("B2", "B3", "B4", "B5", "C2", "C6", "D2", "D7", "E2",
                        "E8", "F3", "F8", "G4", "G8", "H5", "H6", "H7", "H8");

                Coordinate coordinate = new Coordinate(x, y);
                FigureColor color = whiteFigures.contains(coordinate.asString()) ? FigureColor.WHITE : FigureColor.BLACK;
                FigureType type;
                if (tzaarFigures.contains(coordinate.asString())) type = FigureType.TZAAR;
                else if (tzarraFigures.contains(coordinate.asString())) type = FigureType.TZARRA;
                else type = FigureType.TOTT;
                
                board.addFigure(new Figure(coordinate, color, type, 1));
            }
        }

        board.startGame();
        return board;
    }

    //-- GETTERS --//

    /// Returns the current status of the board.
    public BoardState getState() {
        return state;
    }

    /// Returns all figures on the board.
    public List<Figure> getFigures() {
        return figures;
    }

    /// Returns the figure that is at the given coordinate.
    public Figure getFigureFrom(Coordinate coordinate) {
        return figures.stream()
                .filter(figure -> figure.getLocation().equals(coordinate))
                .findFirst()
                .orElse(null);
    }

    //-- METHODS --//

    /// Adds a figure to the board, only when the board is in 'SETUP' state.
    public void addFigure(Figure figure) {
        if (this.state != BoardState.SETUP) return;
        else if (figures.stream().anyMatch(f -> f.getLocation().equals(figure.getLocation()))) {
            throw new IllegalArgumentException("A figure is already at the given location.");
        }
        figures.add(figure);
    }

    /// Starts the game, only when the board is in 'SETUP' state.
    public void startGame() {
        if (this.state != BoardState.SETUP) return;
        this.state = BoardState.IN_PROGRESS;
    }

    /// Moves a figure from the start coordinate to the end coordinate.
    public MoveResult moveFigure(Coordinate startCoord, Coordinate endCoord) {
        if (state != BoardState.IN_PROGRESS) return MoveResult.GAME_NOT_IN_PROGRESS;

        Figure figureA = getFigureFrom(startCoord);
        Figure figureB = getFigureFrom(endCoord);

        if (figureA == null) {
            return MoveResult.NO_FIGURE_TO_MOVE;
        } else if (figureB == null) {
            return MoveResult.NO_FIGURE_TO_CAPTURE_OR_STACK;
        } else if (Coordinate.isOnSameAxis(startCoord, endCoord)) {
            return MoveResult.NO_SAME_AXIS;
        }

        // Check if there are any figures between the two coordinates
        List<Coordinate> coordinatesBetween = Coordinate.coordinatesBetween(startCoord, endCoord);
        for (Coordinate coord : coordinatesBetween) {
            if (getFigureFrom(coord) != null) {
                return MoveResult.NOT_THE_CLOSEST_FIGURE;
            }
        }

        // Every rule has been checked, so the figure can be moved
        if (figureA.getColor() == figureB.getColor()) {
            // Stack 'figureA' on top of 'figureB'
            figureB.stack(figureA);

            // Remove 'figureA' from the board
            figures.remove(figureA);
        } else {
            // 'figureA' captures 'figureB'
            figures.remove(figureB);
            figureA.moveTo(endCoord);
        }

        return MoveResult.SUCCESS;
    }
}
